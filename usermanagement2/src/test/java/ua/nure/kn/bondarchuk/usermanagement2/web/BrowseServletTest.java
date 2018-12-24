package ua.nure.kn.bondarchuk.usermanagement2.web;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import ua.nure.kn.bondarchuk.usermanagement2.User;

public class BrowseServletTest extends MockServletTestCase {
	

	protected void setUp() throws Exception {
		super.setUp();
		createServlet(BrowseServlet.class);
	}
	
	public void testBrowse() {
		User user = new User(new Long(1000), "John", "Doe", new Date());
		List<?> list = Collections.singletonList(user);
		getMockUserDao().expectAndReturn("findAll", list);
		doGet();
		Collection<?> collection = (Collection<?>) getWebMockObjectFactory().getMockSession().getAttribute("user");
		assertNotNull("Could not find list of users in session", collection);
		assertSame(list, collection);
	}

	public void testEdit() {
		User user = new User(new Long(1000), "John", "Doe", new Date());
		getMockUserDao().expectAndReturn("find", new Long(1000), user);
		addRequestParameter("editButton", "Edit");
		addRequestParameter("id", "1000");
		doPost();
		User userInSession = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
		assertNotNull("Could not find user in session", user);
		assertSame(user, userInSession);
	}
	
	 public void testDetails() {
	        User user = new User(new Long(1000), "John", "Doe", new Date());
	        getMockUserDao().expectAndReturn("find", new Long(1000), user);
	        addRequestParameter("detailsButton", "Details");
	        addRequestParameter("id", "1000");
	        doPost();
	        User userInSession = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
	        assertNotNull("Could not find user in session", userInSession);
	        assertEquals(user, userInSession);
	    }
	
}
