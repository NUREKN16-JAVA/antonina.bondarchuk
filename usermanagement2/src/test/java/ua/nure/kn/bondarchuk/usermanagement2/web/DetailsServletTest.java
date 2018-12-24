package ua.nure.kn.bondarchuk.usermanagement2.web;

import ua.nure.kn.bondarchuk.usermanagement2.User;

public class DetailsServletTest extends MockServletTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		createServlet(DetailsServlet.class);
	}
	
	public void testOk() {
        addRequestParameter("OkButton", "Ok");
        User user = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
        assertNull("User must not exist in session scope", user);
    }

}
