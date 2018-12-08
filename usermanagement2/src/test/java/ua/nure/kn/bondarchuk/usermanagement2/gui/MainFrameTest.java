package ua.nure.kn.bondarchuk.usermanagement2.gui;

import java.awt.Component;
import java.text.DateFormat;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import ua.nure.kn.bondarchuk.usermanagement2.db.DaoFactory;
import ua.nure.kn.bondarchuk.usermanagement2.db.DaoFactoryImpl;
import ua.nure.kn.bondarchuk.usermanagement2.db.MockUserDao;
import ua.nure.kn.bondarchuk.usermanagement2.util.Messages;

public class MainFrameTest extends JFCTestCase {
	
	private MainFrame mainFrame;

	protected void setUp() throws Exception {
		super.setUp();
		
		
		try {
			Properties properties = new Properties();
			properties.setProperty(
					"ua.nure.kn.bondarchuk.usermanagement2.db.UserDao", 
					MockUserDao.class.getName());
			properties.setProperty("dao.factory", DaoFactoryImpl.class.getName());
			DaoFactory.getInstance().init(properties);
			
			setHelper(new JFCTestHelper());
			mainFrame = new MainFrame();
			} catch (Exception e) {
				e.printStackTrace();
			}
		mainFrame.setVisible(true);
		
	}
	
	protected void tearDown() throws Exception {
		
		mainFrame.setVisible(false);
		getHelper().cleanUp(this);
		super.tearDown();
	}
	
	private Component find(Class<?> componentClass, String name) {
		Component component;
		NamedComponentFinder finder = new NamedComponentFinder(componentClass, name);
		finder.setWait(0);
		component = finder.find(mainFrame, 0);
		assertNotNull("Could not find component '" + name + "'", component);
		return component;
	}
	
	public void testBrowseControls() {
		find(JPanel.class, "browsePanel");
		JTable table = (JTable) find(JTable.class, "userTable");
		assertEquals(3, table.getColumnCount());
		assertEquals(Messages.getString("UserTableModel.id"), table.getColumnName(0));
		assertEquals(Messages.getString("UserTableModel.first_name"), table.getColumnName(1));
		assertEquals(Messages.getString("UserTableModel.last_name"), table.getColumnName(2));
		
		find(JButton.class, "addButton");
		find(JButton.class, "deleteButton");
		find(JButton.class, "editButton");
		find(JButton.class, "detailsButton");
	}
	
	public void testAddUser() {
		JTable table = (JTable) find(JTable.class, "userTable");
		assertEquals(0, table.getRowCount());
		
		JButton addButton = (JButton) find(JButton.class, "addButton");
		getHelper().enterClickAndLeave(new MouseEventData(this, addButton));
		
		find(JPanel.class,"addPanel");
		
		JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
		JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
		JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");
		
		JButton okButton = (JButton) find(JButton.class, "okButton");
		find(JButton.class, "cancelButton");
		
			
		getHelper().sendString(new StringEventData(this, firstNameField, "John"));
		getHelper().sendString(new StringEventData(this, lastNameField, "Doe"));
		DateFormat formatter = DateFormat.getDateInstance();
		String date = formatter.format(new Date());
		getHelper().sendString(new StringEventData(this, dateOfBirthField, date));
	
		getHelper().enterClickAndLeave(new MouseEventData(this, okButton));
		
		find(JPanel.class, "browsePanel");
		table = (JTable) find(JTable.class, "userTable");
		assertEquals(1, table.getRowCount());
	}
}
