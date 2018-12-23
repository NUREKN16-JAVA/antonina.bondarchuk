package ua.nure.kn.bondarchuk.usermanagement2.web;

import java.io.IOException;
import java.text.DateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;

import java.text.ParseException;
import ua.nure.kn.bondarchuk.usermanagement2.User;
import ua.nure.kn.bondarchuk.usermanagement2.db.DaoFactory;
import ua.nure.kn.bondarchuk.usermanagement2.db.DatabaseException;

public class EditServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		if (arg0.getParameter("okButton") != null) {
			doOk(arg0, arg1);
		} else if (arg0.getParameter("cancelButon") != null) {
			doCancel(arg0, arg1);
		} else {
			showPage(arg0, arg1);
		}
	}

	protected void showPage(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		arg0.getRequestDispatcher("/edit.jsp").forward(arg0, arg1);

    }

    private void doCancel(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
    	arg0.getRequestDispatcher("/browse").forward(arg0, arg1);
    }

    private void doOk(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
        User user;
        try {
            user = getUser(arg0);
        } catch (ValidationException | ParseException e1) {
        	arg0.setAttribute("error", e1.getMessage());
            showPage(arg0, arg1);
            return;
        }
        try {
            processUser(user);
        } catch (DatabaseException e) {
            e.printStackTrace();
            new ServletException(e);
        }
        arg0.getRequestDispatcher("/browse").forward(arg0, arg1);

    }

    private User getUser(HttpServletRequest arg0) throws ValidationException, java.text.ParseException {
        User user = new User();
        String idStr = arg0.getParameter("id");
        String firstName = arg0.getParameter("firstName");
        String lastName = arg0.getParameter("lastName");
        String dateStr = arg0.getParameter("dateOfBirth");
        if (firstName == null) {
            throw new ValidationException("First name is empty");
        }
        if (lastName == null) {
            throw new ValidationException("Last name is empty");
        }
        if (dateStr == null) {
            throw new ValidationException("Date is empty");
        }

        if (idStr != null) {
            user.setId(new Long(idStr));
        }
        user.setFirstName(firstName);
        user.setLastName(lastName);
        try {
            user.setDateOfBirth(DateFormat.getDateInstance().parse(dateStr));
            } catch (ParseException e) {
    		throw new ValidationException("Date format is incorrect");
        }
        return user;
    }

    protected void processUser(User user) throws DatabaseException {
        DaoFactory.getInstance().getUserDao().update(user);
    }

}
