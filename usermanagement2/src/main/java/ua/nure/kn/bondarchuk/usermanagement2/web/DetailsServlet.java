package ua.nure.kn.bondarchuk.usermanagement2.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DetailsServlet extends HttpServlet {

	 protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
	        if (arg0.getParameter("backButton") != null) {
	        	arg0.getSession(true).removeAttribute("user");
	        	arg0.getRequestDispatcher("/browse").forward(arg0, arg1);
	        } else {
	        	arg0.getRequestDispatcher("/details.jsp").forward(arg0, arg1);
	        }
	    }
}
