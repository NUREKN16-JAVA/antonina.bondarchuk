package ua.nure.kn.bondarchuk.usermanagement2.web;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.kn.bondarchuk.usermanagement2.User;
import ua.nure.kn.bondarchuk.usermanagement2.db.DaoFactory;
import ua.nure.kn.bondarchuk.usermanagement2.db.DatabaseException;

public class BrowseServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		if (arg0.getParameter("addButton") != null) {
			add(arg0, arg1);
		} else if (arg0.getParameter("editButton") != null){
			edit(arg0, arg1);
		} else if (arg0.getParameter("deleteButton") != null){
			delete(arg0, arg1);
		} else if (arg0.getParameter("detailsButton") != null){
			details(arg0, arg1);
		} else {
		browse(arg0, arg1);
		}
	}

	private void details(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException{
		// TODO Auto-generated method stub
		
	}

	private void delete(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException{
		// TODO Auto-generated method stub
		
	}

	private void edit(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException{
		String idStr = arg0.getParameter("id");
		if (idStr == null || idStr.trim().length() == 0) {
			arg0.setAttribute("error", "You must select a user");
			arg0.getRequestDispatcher("/browse.jsp").forward(arg0,arg1);
			return;
		}
		try {
			User user = (User) DaoFactory.getInstance().getUserDao().find(new Long(idStr));
			arg0.getSession().setAttribute("user", user);
		} catch (Exception e) {
			arg0.setAttribute("error", "ERROR:" + e.toString());
			arg0.getRequestDispatcher("/browse.jsp").forward(arg0, arg1);
			return;
		}
		arg0.getRequestDispatcher("/edit").forward(arg0, arg1);
		
	}

	private void add(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException{
		// TODO Auto-generated method stub
		
	}

	private void browse(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		Collection<User> users;
		try {
			users = DaoFactory.getInstance().getUserDao().findAll();
			arg0.getSession().setAttribute("users", users);
			arg0.getRequestDispatcher("/browse.jsp").forward(arg0, arg1);
		} catch (DatabaseException e) {
			throw new ServletException(e);
		}
		
	}

}
