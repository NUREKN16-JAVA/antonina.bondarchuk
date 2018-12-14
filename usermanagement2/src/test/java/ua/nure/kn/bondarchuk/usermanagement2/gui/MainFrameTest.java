package ua.nure.kn.bondarchuk.usermanagement2.gui;

import java.awt.Component;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
					"dao.ua.nure.kn.bondarchuk.usermanagement2.db.UserDao", 
					MockUserDao.class.getName());
			properties.setProperty("dao.factory", DaoFactoryImpl.class.getName());
			DaoFactory.init(properties);
			
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
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    String date=dateFormat.format(new Date());
			
		getHelper().sendString(new StringEventData(this, firstNameField, "John"));
		getHelper().sendString(new StringEventData(this, lastNameField, "Doe"));
		
		
		getHelper().sendString(new StringEventData(this, dateOfBirthField, date));
	
		getHelper().enterClickAndLeave(new MouseEventData(this, okButton));
		
		find(JPanel.class, "browsePanel");
		assertEquals(1, table.getRowCount());
	}
	
	public void testCancelAddUser() {
		JTable table = (JTable) find(JTable.class, "userTable");
		assertEquals(0, table.getRowCount());
		
		JButton addButton = (JButton) find(JButton.class, "addButton");
		getHelper().enterClickAndLeave(new MouseEventData(this, addButton));
		
		find(JPanel.class,"addPanel");
		
		JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
		JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
		JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");
		
		find(JButton.class, "okButton");
		JButton cancelButton = (JButton) find(JButton.class, "cancelButton");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    String date=dateFormat.format(new Date());
			
		getHelper().sendString(new StringEventData(this, firstNameField, "John"));
		getHelper().sendString(new StringEventData(this, lastNameField, "Doe"));
		
		
		getHelper().sendString(new StringEventData(this, dateOfBirthField, date));
	
		getHelper().enterClickAndLeave(new MouseEventData(this, cancelButton));
		
		find(JPanel.class, "browsePanel");
		assertEquals(0, table.getRowCount());
	}
	
	public void testEditUser () {
		JTable table = (JTable) find(JTable.class, "userTable");
		JButton addButton = (JButton) find(JButton.class, "addButton");
		getHelper().enterClickAndLeave(new MouseEventData(this, addButton));
		find(JPanel.class,"addPanel");
		JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
		JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
		JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");
		JButton okButton = (JButton) find(JButton.class, "okButton");
	
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    String date=dateFormat.format(new Date());		
		getHelper().sendString(new StringEventData(this, firstNameField, "John"));
		getHelper().sendString(new StringEventData(this, lastNameField, "Doe"));		
		getHelper().sendString(new StringEventData(this, dateOfBirthField, date));
		getHelper().enterClickAndLeave(new MouseEventData(this, okButton));
		find(JPanel.class, "browsePanel");
		//TO-DO: fix adding user in table
		table.setRowSelectionInterval(0, 0);
		JButton editButton = (JButton) find(JButton.class, "editButton");
		getHelper().enterClickAndLeave(new MouseEventData(this, editButton));
		find(JPanel.class,"editPanel");
		JTextField firstNameFieldEdit = (JTextField) find(JTextField.class, "firstNameField");
		JTextField lastNameFieldEdit = (JTextField) find(JTextField.class, "lastNameField");
		JTextField dateOfBirthFieldEdit = (JTextField) find(JTextField.class, "dateOfBirthField");
		firstNameFieldEdit.setText("");
		lastNameFieldEdit.setText("");
		dateOfBirthFieldEdit.setText("");
		JButton okButtonEdit = (JButton) find(JButton.class, "okButton");
		SimpleDateFormat dateFormatEdit = new SimpleDateFormat("yyyy-MM-dd");
	    String dateEdit=dateFormatEdit.format(new Date());		
		getHelper().sendString(new StringEventData(this, firstNameFieldEdit, "Josh"));
		getHelper().sendString(new StringEventData(this, lastNameFieldEdit, "Mcdc"));		
		getHelper().sendString(new StringEventData(this, dateOfBirthFieldEdit, dateEdit));
		getHelper().enterClickAndLeave(new MouseEventData(this, okButtonEdit));
		find(JPanel.class, "browsePanel");
		assertEquals(1, table.getRowCount());
		
	}
	
	public void testCancelEditUser() {
		JTable table = (JTable) find(JTable.class, "userTable");
		JButton addButton = (JButton) find(JButton.class, "addButton");
		getHelper().enterClickAndLeave(new MouseEventData(this, addButton));
		find(JPanel.class,"addPanel");
		JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
		JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
		JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");
		JButton okButton = (JButton) find(JButton.class, "okButton");
	
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    String date=dateFormat.format(new Date());		
		getHelper().sendString(new StringEventData(this, firstNameField, "John"));
		getHelper().sendString(new StringEventData(this, lastNameField, "Doe"));		
		getHelper().sendString(new StringEventData(this, dateOfBirthField, date));
		getHelper().enterClickAndLeave(new MouseEventData(this, okButton));
		find(JPanel.class, "browsePanel");
		
		table.setRowSelectionInterval(0, 0);
		JButton editButton = (JButton) find(JButton.class, "editButton");
		getHelper().enterClickAndLeave(new MouseEventData(this, editButton));
		find(JPanel.class,"editPanel");
		JTextField firstNameFieldEdit = (JTextField) find(JTextField.class, "firstNameField");
		JTextField lastNameFieldEdit = (JTextField) find(JTextField.class, "lastNameField");
		JTextField dateOfBirthFieldEdit = (JTextField) find(JTextField.class, "dateOfBirthField");
		firstNameFieldEdit.setText("");
		lastNameFieldEdit.setText("");
		dateOfBirthFieldEdit.setText("");
		JButton  cancelButtonCancelEdit = (JButton) find(JButton.class, "cancelButton");
		SimpleDateFormat dateFormatEdit = new SimpleDateFormat("yyyy-MM-dd");
	    String dateEdit=dateFormatEdit.format(new Date());		
		getHelper().sendString(new StringEventData(this, firstNameFieldEdit, "Josh"));
		getHelper().sendString(new StringEventData(this, lastNameFieldEdit, "Mcdc"));		
		getHelper().sendString(new StringEventData(this, dateOfBirthFieldEdit, dateEdit));
		getHelper().enterClickAndLeave(new MouseEventData(this, cancelButtonCancelEdit));	
			
		assertEquals(table.getModel().getValueAt(0, 1).toString(),"John");
		assertEquals(table.getModel().getValueAt(0, 2).toString(),"Doe");
		
		find(JPanel.class, "browsePanel");
		assertEquals(1, table.getRowCount());
	}
	
	public void testDeleteUser() {
		JTable table = (JTable) find(JTable.class, "userTable");
		JButton addButton = (JButton) find(JButton.class, "addButton");
		getHelper().enterClickAndLeave(new MouseEventData(this, addButton));
		find(JPanel.class,"addPanel");
		JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
		JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
		JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");
		JButton okButton = (JButton) find(JButton.class, "okButton");
	
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    String date=dateFormat.format(new Date());		
		getHelper().sendString(new StringEventData(this, firstNameField, "John"));
		getHelper().sendString(new StringEventData(this, lastNameField, "Doe"));		
		getHelper().sendString(new StringEventData(this, dateOfBirthField, date));
		getHelper().enterClickAndLeave(new MouseEventData(this, okButton));
		assertEquals(1, table.getRowCount());
		find(JPanel.class, "browsePanel");
		table.setRowSelectionInterval(0, 0);
		
		JButton deleteButton = (JButton) find(JButton.class, "deleteButton");
		getHelper().enterClickAndLeave(new MouseEventData(this, deleteButton));
		find(JPanel.class,"deletePanel");
		JButton okButtonDelete = (JButton) find(JButton.class, "okButton");
		getHelper().enterClickAndLeave(new MouseEventData(this, okButtonDelete));
		assertEquals(0, table.getRowCount());
		
		find(JPanel.class, "browsePanel");
		
	}
	
	public void testCancelDeleteUser() {
		JTable table = (JTable) find(JTable.class, "userTable");
		JButton addButton = (JButton) find(JButton.class, "addButton");
		getHelper().enterClickAndLeave(new MouseEventData(this, addButton));
		find(JPanel.class,"addPanel");
		JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
		JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
		JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");
		JButton okButton = (JButton) find(JButton.class, "okButton");
	
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    String date=dateFormat.format(new Date());		
		getHelper().sendString(new StringEventData(this, firstNameField, "John"));
		getHelper().sendString(new StringEventData(this, lastNameField, "Doe"));		
		getHelper().sendString(new StringEventData(this, dateOfBirthField, date));
		getHelper().enterClickAndLeave(new MouseEventData(this, okButton));
		assertEquals(1, table.getRowCount());
		find(JPanel.class, "browsePanel");
		table.setRowSelectionInterval(0, 0);
		
		JButton deleteButton = (JButton) find(JButton.class, "deleteButton");
		getHelper().enterClickAndLeave(new MouseEventData(this, deleteButton));
		find(JPanel.class,"deletePanel");
		JButton cancelButtonDelete = (JButton) find(JButton.class, "cancelButton");
		getHelper().enterClickAndLeave(new MouseEventData(this, cancelButtonDelete));
		assertEquals(1, table.getRowCount());	
		
		find(JPanel.class, "browsePanel");
	}
	
	
	public void testDetails() {
		JTable table = (JTable) find(JTable.class, "userTable");
		JButton addButton = (JButton) find(JButton.class, "addButton");
		getHelper().enterClickAndLeave(new MouseEventData(this, addButton));
		find(JPanel.class,"addPanel");
		JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
		JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
		JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");
		JButton okButton = (JButton) find(JButton.class, "okButton");
	
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    String date=dateFormat.format(new Date());		
		getHelper().sendString(new StringEventData(this, firstNameField, "John"));
		getHelper().sendString(new StringEventData(this, lastNameField, "Doe"));		
		getHelper().sendString(new StringEventData(this, dateOfBirthField, date));
		getHelper().enterClickAndLeave(new MouseEventData(this, okButton));
		find(JPanel.class, "browsePanel");
		
		table.setRowSelectionInterval(0, 0);
		
		JButton detailsButton = (JButton) find(JButton.class, "detailsButton");
		getHelper().enterClickAndLeave(new MouseEventData(this, detailsButton));
		find(JPanel.class,"detailsPanel");
		
		JTextField firstNameFieldDetails = (JTextField) find(JTextField.class, "firstNameField");
		JTextField lastNameFieldDetails = (JTextField) find(JTextField.class, "lastNameField");
		JTextField dateOfBirthFieldDetails = (JTextField) find(JTextField.class, "dateOfBirthField");
		assertNotNull(firstNameFieldDetails.getText());
		assertNotNull(lastNameFieldDetails.getText());
		assertNotNull(dateOfBirthFieldDetails.getText());
		JButton okButtonDetails = (JButton) find(JButton.class, "okButton");
		getHelper().enterClickAndLeave(new MouseEventData(this, okButtonDetails));
		find(JPanel.class,"browsePanel");
		
		
		
	}
}
