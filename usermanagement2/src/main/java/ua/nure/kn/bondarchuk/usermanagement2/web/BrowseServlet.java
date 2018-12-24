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
import ua.nure.kn.bondarchuk.usermanagement2.db.UserDao;

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
		 String idStrUser = arg0.getParameter("id");

	        if (idStrUser == null || idStrUser.trim().isEmpty()) {
	        	arg0.setAttribute("error", "You must select a user");
	        	arg0.getRequestDispatcher("/browse.jsp").forward(arg0, arg1);
	            return;
	        }

	        try {
	            User foundUser = DaoFactory.getInstance().getUserDao().find(Long.parseLong(idStrUser));
	            arg0.getSession(true).setAttribute("user", foundUser);
	        } catch (Exception e) {
	        	arg0.setAttribute("error", "ERROR:" + e.toString());
	        	arg0.getRequestDispatcher("/browse.jsp").forward(arg0, arg1);
	            return;
	        }
	        arg0.getRequestDispatcher("/details").forward(arg0, arg1);
		
	}

	private void delete(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException{
		 String idStrUser = arg0.getParameter("id");

	        if (idStrUser == null || idStrUser.trim().isEmpty()) {
	        	arg0.setAttribute("error", "You must select a user");
	        	arg0.getRequestDispatcher("/browse.jsp").forward(arg0, arg1);
	            return;
	        }

	        try {
	            UserDao userDao = DaoFactory.getInstance().getUserDao();
	            User deleteUser = userDao.find(Long.parseLong(idStrUser));
	            userDao.delete(deleteUser);
	        } catch (Exception e) {
	        	arg0.setAttribute("error", "ERROR:" + e.toString());
	        	arg0.getRequestDispatcher("/browse.jsp").forward(arg0, arg1);
	            return;
	        }
	        arg1.sendRedirect("/browse");
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
		arg0.getRequestDispatcher("/add").forward(arg0, arg1);
		
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
