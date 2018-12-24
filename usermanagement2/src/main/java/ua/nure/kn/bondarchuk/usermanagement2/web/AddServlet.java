package ua.nure.kn.bondarchuk.usermanagement2.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.kn.bondarchuk.usermanagement2.User;
import ua.nure.kn.bondarchuk.usermanagement2.db.DaoFactory;
import ua.nure.kn.bondarchuk.usermanagement2.db.DatabaseException;

public class AddServlet extends EditServlet {

	/* (non-Javadoc)
	 * @see ua.nure.kn.bondarchuk.usermanagement2.web.EditServlet#showPage(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void showPage(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		arg0.getRequestDispatcher("/add.jsp").forward(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see ua.nure.kn.bondarchuk.usermanagement2.web.EditServlet#processUser(ua.nure.kn.bondarchuk.usermanagement2.User)
	 */
	@Override
	protected void processUser(User user) throws DatabaseException {
		DaoFactory.getInstance().getUserDao().create(user);
		
	}

	
}
