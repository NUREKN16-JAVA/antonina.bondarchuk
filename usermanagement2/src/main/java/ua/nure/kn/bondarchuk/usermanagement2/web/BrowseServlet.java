package ua.nure.kn.bondarchuk.usermanagement2.web;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.kn.bondarchuk.usermanagement2.db.DaoFactory;
import ua.nure.kn.bondarchuk.usermanagement2.db.DatabaseException;

public class BrowseServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		browse(arg0, arg1);
	}

	private void browse(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		Collection<?> users;
		try {
			users = DaoFactory.getInstance().getUserDao().findAll();
			arg0.getSession().setAttribute("users", users);
			arg0.getRequestDispatcher("/browse.jsp").forward(arg0, arg1);
		} catch (DatabaseException e) {
			throw new ServletException(e);
		}
		
	}

}
