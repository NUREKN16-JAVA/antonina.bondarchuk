package ua.nure.kn.bondarchuk.usermanagement2.gui;


import java.text.DateFormat;

import ua.nure.kn.bondarchuk.usermanagement2.User;


public class EditPanel extends AddPanel {
	private User user;

    public EditPanel(MainFrame parent) {
        super(parent);
        setName("editPanel");
    }


    public void setUser(User user) {
        DateFormat format = DateFormat.getDateInstance();
        this.user = user;
        getFirstNameField().setText(user.getFirstName());
        getLastNameField().setText(user.getLastName());
        getDateOfBirthField().setText(format.format(user.getDateOfBirth()));
    }

}
