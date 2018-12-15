package ua.nure.kn.bondarchuk.usermanagement2.gui;

import java.awt.Component;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.mockobjects.dynamic.Mock;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import ua.nure.kn.bondarchuk.usermanagement2.User;
import ua.nure.kn.bondarchuk.usermanagement2.db.DaoFactory;
import ua.nure.kn.bondarchuk.usermanagement2.db.MockDaoFactory;
import ua.nure.kn.bondarchuk.usermanagement2.util.Messages;

public class MainFrameTest extends JFCTestCase {
	
	private MainFrame mainFrame;
	private Mock mockUserDao;

	protected void setUp() throws Exception {
		super.setUp();
		
		
		try {
			Properties properties = new Properties();
			
			properties.setProperty("dao.factory", MockDaoFactory.class.getName());
			DaoFactory.init(properties);
			mockUserDao = ((MockDaoFactory) DaoFactory.getInstance()).getMockUserDao();
			
			mockUserDao.expectAndReturn("findAll", new ArrayList());
			
			setHelper(new JFCTestHelper());
			mainFrame = new MainFrame();
			} catch (Exception e) {
				e.printStackTrace();
			}
		mainFrame.setVisible(true);
		
	}
	
	protected void tearDown() throws Exception {
		try {
			mockUserDao.verify();
			mainFrame.setVisible(false);
			getHelper().cleanUp(this);
			super.tearDown();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		String firstName = "John";
		String lastName = "Doe";
		Date now = new Date();
		////
		User user = new User(firstName, lastName, now);
		User expectedUser = new User(new Long(1), firstName, lastName, now);
		mockUserDao.expectAndReturn("create", user, expectedUser);
		
		ArrayList users = new ArrayList();
		users.add(expectedUser);
		mockUserDao.expectAndReturn("findAll", users);
		JTable table = (JTable) find(JTable.class, "userTable");
		///
		mockUserDao.expectAndReturn("findAll", users);
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
	    String date=dateFormat.format(now);
			
		
		getHelper().sendString(new StringEventData(this, firstNameField, firstName));
		
		getHelper().sendString(new StringEventData(this, lastNameField, lastName));
		
		
		getHelper().sendString(new StringEventData(this, dateOfBirthField, date));
	
		getHelper().enterClickAndLeave(new MouseEventData(this, okButton));
		
		find(JPanel.class, "browsePanel");
		assertEquals(1, table.getRowCount());
	}
	
	public void testCancelAddUser() {
		String firstName = "John";
		String lastName = "Doe";
		Date now = new Date();
		
		ArrayList users = new ArrayList();
		mockUserDao.expectAndReturn("findAll", users);
		JTable table = (JTable) find(JTable.class, "userTable");
		///
		mockUserDao.expectAndReturn("findAll", users);
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
	    
		String date=dateFormat.format(now);
			
		
		getHelper().sendString(new StringEventData(this, firstNameField, firstName));
		
		getHelper().sendString(new StringEventData(this, lastNameField, lastName));
		
		
		getHelper().sendString(new StringEventData(this, dateOfBirthField, date));
	
		getHelper().enterClickAndLeave(new MouseEventData(this, cancelButton));
		
		find(JPanel.class, "browsePanel");
		assertEquals(0, table.getRowCount());
	}
	
	public void testEditUser () {
		String firstName = "John";
		String lastName = "Doe";
		Date now = new Date();
		
		String firstNameEdited = "Josh";
		String lastNameEdited = "Mcdc";
		Date nowEdited = new Date();
		
		//User user = new User(firstName, lastName, now);
		User expectedUser = new User(new Long(1), firstNameEdited, lastNameEdited, nowEdited);
		//mockUserDao.expectAndReturn("create", user);
		mockUserDao.expectAndReturn("update", expectedUser);
		
		ArrayList users = new ArrayList();
		users.add(expectedUser);
		mockUserDao.expectAndReturn("findAll", users);
		JTable table = (JTable) find(JTable.class, "userTable");
		///
		mockUserDao.expectAndReturn("findAll", users);
		
		JButton addButton = (JButton) find(JButton.class, "addButton");
		getHelper().enterClickAndLeave(new MouseEventData(this, addButton));
		find(JPanel.class,"addPanel");
		JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
		JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
		JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");
		JButton okButton = (JButton) find(JButton.class, "okButton");
	
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    String date=dateFormat.format(now);		
		getHelper().sendString(new StringEventData(this, firstNameField, firstName));
		getHelper().sendString(new StringEventData(this, lastNameField, lastName));		
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
	    String dateEdit=dateFormatEdit.format(nowEdited);		
		
		getHelper().sendString(new StringEventData(this, firstNameFieldEdit, firstNameEdited));
		
		getHelper().sendString(new StringEventData(this, lastNameFieldEdit, lastNameEdited));		
		getHelper().sendString(new StringEventData(this, dateOfBirthFieldEdit, dateEdit));
		getHelper().enterClickAndLeave(new MouseEventData(this, okButtonEdit));
		find(JPanel.class, "browsePanel");
		assertEquals(1, table.getRowCount());
		
	}
	
	public void testCancelEditUser() {
		String firstName = "John";
		String lastName = "Doe";
		Date now = new Date();
		
		String firstNameEdited = "Josh";
		String lastNameEdited = "Mcdc";
		Date nowEdited = new Date();
		
		User expectedUser = new User(new Long(1), firstNameEdited, lastNameEdited, nowEdited);
				
		ArrayList users = new ArrayList();
		users.add(expectedUser);
		mockUserDao.expectAndReturn("findAll", users);
		JTable table = (JTable) find(JTable.class, "userTable");
		///
		mockUserDao.expectAndReturn("findAll", users);
		JButton addButton = (JButton) find(JButton.class, "addButton");
		getHelper().enterClickAndLeave(new MouseEventData(this, addButton));
		find(JPanel.class,"addPanel");
		JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
		JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
		JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");
		JButton okButton = (JButton) find(JButton.class, "okButton");
	
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    String date=dateFormat.format(now);		
		getHelper().sendString(new StringEventData(this, firstNameField, firstName));
		getHelper().sendString(new StringEventData(this, lastNameField, lastName));		
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
	    String dateEdit=dateFormatEdit.format(nowEdited);		
		getHelper().sendString(new StringEventData(this, firstNameFieldEdit, firstNameEdited));
		getHelper().sendString(new StringEventData(this, lastNameFieldEdit, lastNameEdited));		
		getHelper().sendString(new StringEventData(this, dateOfBirthFieldEdit, dateEdit));
		getHelper().enterClickAndLeave(new MouseEventData(this, cancelButtonCancelEdit));	
			
		assertEquals(table.getModel().getValueAt(0, 1).toString(),firstName);
		assertEquals(table.getModel().getValueAt(0, 2).toString(),lastName);
		
		find(JPanel.class, "browsePanel");
		assertEquals(1, table.getRowCount());
	}
	
	public void testDeleteUser() {

		String firstName = "John";
		String lastName = "Doe";
		Date now = new Date();
		
		///////////////////
		
		
		User expectedUser = new User(new Long(1), firstName, lastName, now);
		
		mockUserDao.expectAndReturn("delete", expectedUser);
		
		ArrayList users = new ArrayList();
		mockUserDao.expectAndReturn("findAll", users);
		JTable table = (JTable) find(JTable.class, "userTable");
		///
		mockUserDao.expectAndReturn("findAll", users);
		JButton addButton = (JButton) find(JButton.class, "addButton");
		getHelper().enterClickAndLeave(new MouseEventData(this, addButton));
		find(JPanel.class,"addPanel");
		JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
		JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
		JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");
		JButton okButton = (JButton) find(JButton.class, "okButton");
	
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    String date=dateFormat.format(now);		
		getHelper().sendString(new StringEventData(this, firstNameField, firstName));
		getHelper().sendString(new StringEventData(this, lastNameField, lastName));		
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

		String firstName = "John";
		String lastName = "Doe";
		Date now = new Date();
		
		
		
		ArrayList users = new ArrayList();
		mockUserDao.expectAndReturn("findAll", users);
		JTable table = (JTable) find(JTable.class, "userTable");
		///
		mockUserDao.expectAndReturn("findAll", users);
		JButton addButton = (JButton) find(JButton.class, "addButton");
		getHelper().enterClickAndLeave(new MouseEventData(this, addButton));
		find(JPanel.class,"addPanel");
		JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
		JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
		JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");
		JButton okButton = (JButton) find(JButton.class, "okButton");
	
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    String date=dateFormat.format(now);		
		getHelper().sendString(new StringEventData(this, firstNameField, firstName));
		getHelper().sendString(new StringEventData(this, lastNameField, lastName));		
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

		String firstName = "John";
		String lastName = "Doe";
		Date now = new Date();
		
		User user = new User(firstName, lastName, now);
		
		ArrayList users = new ArrayList();
		users.add(user);
		mockUserDao.expectAndReturn("findAll", users);
		JTable table = (JTable) find(JTable.class, "userTable");
		///
		mockUserDao.expectAndReturn("findAll", users);
		JButton addButton = (JButton) find(JButton.class, "addButton");
		getHelper().enterClickAndLeave(new MouseEventData(this, addButton));
		find(JPanel.class,"addPanel");
		JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
		JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
		JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");
		JButton okButton = (JButton) find(JButton.class, "okButton");
	
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    String date=dateFormat.format(now);		
		getHelper().sendString(new StringEventData(this, firstNameField, firstName));
		getHelper().sendString(new StringEventData(this, lastNameField, lastName));		
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
