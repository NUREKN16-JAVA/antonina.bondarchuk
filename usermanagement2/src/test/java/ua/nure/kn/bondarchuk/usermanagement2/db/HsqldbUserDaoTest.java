package ua.nure.kn.bondarchuk.usermanagement2.db;

import java.util.Collection;
import java.util.Date;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;

import ua.nure.kn.bondarchuk.usermanagement2.User;

public class HsqldbUserDaoTest extends DatabaseTestCase {
	
	private UserDao dao;

	private ConnectionFactoryImpl connectionFactory;

	private static final String LAST_NAME_ETALON = "Bondarchuk";
	private static final String FIRST_NAME_ETALON = "Tonya";
	private static final long ID_ETALON = 1001L;

	public HsqldbUserDaoTest(String name) {
		super(name);
	}

	public void setUp() throws Exception {
		super.setUp();
		dao = new HsqldbUserDao(new ConnectionFactoryImpl());
	}
	
	public void testCreateUser() throws DatabaseException{
		try {
			User user = new User();
			user.setFirstName(FIRST_NAME_ETALON);
			user.setLastName(LAST_NAME_ETALON);
			user.setDateOfBirth(new Date());
			assertNull(user.getId());
			User userExpected = dao.create(user);
			assertNotNull(userExpected);
			assertNotNull(userExpected.getId());
			assertEquals(userExpected.getFirstName(), user.getFirstName());
			assertEquals(userExpected.getLastName(), user.getLastName());
			assertEquals(userExpected.getDateOfBirth(), user.getDateOfBirth());
		} catch (DatabaseException e) {
			e.printStackTrace();
			fail(e.toString());
		}
		
	}
	
	public void testFindAll() throws DatabaseException{
		try {
			Collection<User> collection = dao.findAll();
			assertNotNull("Collection is null",collection);
			assertEquals("Collection size.",2, collection.size());
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.toString());
		}
	}
	
	public void testFindUser() throws DatabaseException{
		try {
			User user = dao.find(ID_ETALON);
			assertNotNull(user);
            long userId = user.getId();
            assertEquals(ID_ETALON, userId);
		} catch (DatabaseException e) {
			e.printStackTrace();
			fail(e.toString());
		}
		
	}

	public void testUpdateUser() throws DatabaseException {
		
        try {
        	User user = new User();
        	user.setId(ID_ETALON);
        	user.setFirstName(FIRST_NAME_ETALON);
			user.setLastName(LAST_NAME_ETALON);
			user.setDateOfBirth(new Date());
            dao.update(user);
            User updatedUser = dao.find(ID_ETALON);
            assertEquals(FIRST_NAME_ETALON, updatedUser.getFirstName());
            assertEquals(LAST_NAME_ETALON, updatedUser.getLastName());
        } catch (DatabaseException e) {
        	e.printStackTrace();
			fail(e.toString());
        }
		
	}
	
	public void testDeleteUser() throws DatabaseException {
		try {
        	int expectedBefore = 2;
            int expectedAfter = 1;
        	User user = new User();
        	user.setId(ID_ETALON);
        	user.setFirstName(FIRST_NAME_ETALON);
			user.setLastName(LAST_NAME_ETALON);
			user.setDateOfBirth(new Date());
            int actualBefore = dao.findAll().size();
            dao.delete(user);
            int actualAfter = dao.findAll().size();

            assertEquals(expectedBefore, actualBefore);
            assertEquals(expectedAfter, actualAfter);
        } catch (DatabaseException e) {
        	e.printStackTrace();
			fail(e.toString());
        }
	}
	
	
	
	@Override
	protected IDatabaseConnection getConnection() throws Exception {
		connectionFactory = new ConnectionFactoryImpl();
		return new DatabaseConnection(connectionFactory.createConnection());
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		IDataSet dataSet = new XmlDataSet(
				getClass().getClassLoader().getResourceAsStream("usersDataSet.xml"));
		return dataSet;
	}

}
