package ua.nure.kn.bondarchuk.usermanagement2.gui;

import java.awt.Component;
import java.awt.List;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.mockobjects.dynamic.Mock;
import ua.nure.kn.bondarchuk.usermanagement2.User;
import ua.nure.kn.bondarchuk.usermanagement2.db.DaoFactory;
import ua.nure.kn.bondarchuk.usermanagement2.db.MockDaoFactory;
import ua.nure.kn.bondarchuk.usermanagement2.util.Messages;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.TestHelper;
import junit.extensions.jfcunit.eventdata.JTableMouseEventData;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.DialogFinder;
import junit.extensions.jfcunit.finder.NamedComponentFinder;

public class MainFrameTest extends JFCTestCase {
	
	private MainFrame mainFrame;
	private Mock mockUserDao;
	private final Date DATE = new Date();
	private java.util.List<User> users;

	protected void setUp() throws Exception {
		super.setUp();
		
		
		try {
			Properties properties = new Properties();
						
			properties.setProperty("dao.factory", MockDaoFactory.class.getName());
			DaoFactory.init(properties);
			mockUserDao = ((MockDaoFactory) DaoFactory.getInstance()).getMockUserDao();
			User expectedUser = new User(new Long(1000), "George", "Bush", new Date());
            users = Collections.singletonList(expectedUser);
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
        try {
            String firstName = "John";
            String lastName = "Doe";
            Date now = new Date();

            User user = new User(firstName, lastName, now);
            
            User expectedUser = new User(new Long(1), firstName, lastName, now);
            mockUserDao.expectAndReturn("create", user, expectedUser);
            
            ArrayList<User> users = new ArrayList<>(this.users);
            users.add(expectedUser);
            mockUserDao.expectAndReturn("findAll", users);
            
            JTable table = (JTable) find(JTable.class, "userTable");
            assertEquals(1, table.getRowCount());

            JButton addButton = (JButton) find(JButton.class, "addButton");
            getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

            find(JPanel.class, "addPanel");

            fillField(firstName, lastName, now);

            JButton okButton = (JButton) find(JButton.class, "okButton");
            getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

            find(JPanel.class, "browsePanel");
            table = (JTable) find(JTable.class, "userTable");
            assertEquals(2, table.getRowCount());
            
            mockUserDao.verify();
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    public void testCancelAddUser() {
        try {
            String firstName = "John";
            String lastName = "Doe";
            Date now = new Date();

            ArrayList<User> users = new ArrayList<>(this.users);
            mockUserDao.expectAndReturn("findAll", users);
            
            JTable table = (JTable) find(JTable.class, "userTable");
            assertEquals(1, table.getRowCount());

            JButton addButton = (JButton) find(JButton.class, "addButton");
            getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

            find(JPanel.class, "addPanel");

            fillField(firstName, lastName, now);

            JButton cancelButton = (JButton) find(JButton.class, "cancelButton");
            getHelper().enterClickAndLeave(new MouseEventData(this, cancelButton));

            find(JPanel.class, "browsePanel");
            table = (JTable) find(JTable.class, "userTable");
            assertEquals(1, table.getRowCount());
            
            mockUserDao.verify();
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    public void testCancelEditUser() {
        try {
            String firstName = "John";
            String lastName = "Doe";
            Date now = new Date();

            User expectedUser = new User(new Long(1), firstName, lastName, now);
            Collection<User> users = new ArrayList<>(this.users);
            users.add(expectedUser);
            
            mockUserDao.expectAndReturn("findAll", users);
            
            JTable table = (JTable) find(JTable.class, "userTable");
            assertEquals(1, table.getRowCount());

            JButton editButton = (JButton) find(JButton.class, "editButton");
            getHelper().enterClickAndLeave(new MouseEventData(this, editButton));

            String title = "Edit user";
            findDialog(title);
            
            getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
            getHelper().enterClickAndLeave(new MouseEventData(this, editButton));
            
            find(JPanel.class, "editPanel");
            
            

            JTextField firstNameField = (JTextField) find(JTextField.class,
                    "firstNameField");
            JTextField lastNameField = (JTextField) find(JTextField.class,
                    "lastNameField");
            JTextField dateOfBirthField = (JTextField) find(JTextField.class,
                    "dateOfBirthField");
            
            assertEquals("George", firstNameField.getText());
            assertEquals("Bush", lastNameField.getText());
            
            getHelper().sendString(
                    new StringEventData(this, firstNameField, firstName));
            getHelper().sendString(
                    new StringEventData(this, lastNameField, lastName));
            DateFormat formatter = DateFormat.getDateInstance();
            String date = formatter.format(now);
            getHelper().sendString(
                    new StringEventData(this, dateOfBirthField, date));

            JButton cancelButton = (JButton) find(JButton.class, "cancelButton");
            getHelper().enterClickAndLeave(new MouseEventData(this, cancelButton));

            find(JPanel.class, "browsePanel");
            table = (JTable) find(JTable.class, "userTable");
            assertEquals(2, table.getRowCount());
            mockUserDao.verify();
            
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    public void testEditUser() {
        try {           
            User expectedUser = new User(new Long(1000), "George", "Bush", new Date());
            System.out.println(expectedUser);
            mockUserDao.expect("update", expectedUser);

            Collection<User> users = new ArrayList<>(this.users);
            mockUserDao.expectAndReturn("findAll", users);
            
            JTable table = (JTable) find(JTable.class, "userTable");
            assertEquals(1, table.getRowCount());
            JButton editButton = (JButton) find(JButton.class, "editButton");
            getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
            getHelper().enterClickAndLeave(new MouseEventData(this, editButton));
            
            find(JPanel.class, "editPanel");

            JTextField firstNameField = (JTextField) find(JTextField.class,
                    "firstNameField");
            JTextField lastNameField = (JTextField) find(JTextField.class,
                    "lastNameField");
            
            getHelper().sendString(
                    new StringEventData(this, firstNameField, "1"));
            getHelper().sendString(
                    new StringEventData(this, lastNameField, "1"));

            JButton okButton = (JButton) find(JButton.class, "okButton");
            getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

            find(JPanel.class, "browsePanel");
            table = (JTable) find(JTable.class, "userTable");
            assertEquals(1, table.getRowCount());
            mockUserDao.verify();
            
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    private void findDialog(String title) {
        JDialog dialog;
        DialogFinder dFinder = new DialogFinder(title);
        dialog = (JDialog) dFinder.find();
        assertNotNull("Could not find dialog '" + title + "'", dialog);
        getHelper();
        TestHelper.disposeWindow(dialog, this);
    }

    private void fillField(String firstName, String lastName, Date now) {
        JTextField firstNameField = (JTextField) find(JTextField.class,
                "firstNameField");
        JTextField lastNameField = (JTextField) find(JTextField.class,
                "lastNameField");
        JTextField dateOfBirthField = (JTextField) find(JTextField.class,
                "dateOfBirthField");

        getHelper().sendString(
                new StringEventData(this, firstNameField, firstName));
        getHelper().sendString(
                new StringEventData(this, lastNameField, lastName));
        DateFormat formatter = DateFormat.getDateInstance();
        String date = formatter.format(now);
        getHelper().sendString(
                new StringEventData(this, dateOfBirthField, date));
    }

    public void testDeleteUser() {
        try {
            User expectedUser = new User(new Long(1000), "George", "Bush", new Date());
            mockUserDao.expect("delete", expectedUser);

            Collection<User> users = new ArrayList<>();
            mockUserDao.expectAndReturn("findAll", users);
            
            JTable table = (JTable) find(JTable.class, "userTable");
            assertEquals(1, table.getRowCount());
            JButton deleteButton = (JButton) find(JButton.class, "deleteButton");
            getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
            getHelper().enterClickAndLeave(new MouseEventData(this, deleteButton));
            
            find(JPanel.class, "browsePanel");
            table = (JTable) find(JTable.class, "userTable");
            assertEquals(0, table.getRowCount());
            mockUserDao.verify();
            
        } catch (Exception e) {
            fail(e.toString());
        }
    }
}
