package ua.nure.kn.bondarchuk.usermanagement2.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ua.nure.kn.bondarchuk.usermanagement2.User;
import ua.nure.kn.bondarchuk.usermanagement2.db.DatabaseException;
import ua.nure.kn.bondarchuk.usermanagement2.util.Messages;

public class EditPanel extends JPanel  implements ActionListener{

	private MainFrame parent;
	private JPanel buttonPanel;
	private JPanel fieldPanel;
	private JButton cancelButton;
	private JButton okButton;
	private JTextField dateOfBirthField;
	private JTextField lastNameField;
	private JTextField firstNameField;
	private Color bgColor;
	private User user;
	
	public EditPanel(MainFrame parent) {
		this.parent = parent;
		initialize();
	}

	private void initialize() {
		this.setName("editPanel"); //$NON-NLS-1$
		this.setLayout(new BorderLayout());
		this.add(getFieldPanel(), BorderLayout.NORTH);
		this.add(getButtonPanel(), BorderLayout.SOUTH);
		
	}
	
	private JPanel getButtonPanel() {
		if (buttonPanel == null) {
			buttonPanel = new JPanel();
			buttonPanel.add(getOkButton(), null);
			buttonPanel.add(getCancelButton(), null);
			}
		return buttonPanel;
	}

	private JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setText(Messages.getString("EditPanel.cancel")); //$NON-NLS-1$
			cancelButton.setName("cancelButton"); //$NON-NLS-1$
			cancelButton.setActionCommand("cancel"); //$NON-NLS-1$
			cancelButton.addActionListener(this);
		}
		return cancelButton;
	}

	private JButton getOkButton() {
		if (okButton == null) {
			okButton = new JButton();
			okButton.setText(Messages.getString("EditPanel.ok")); //$NON-NLS-1$
			okButton.setName("okButton"); //$NON-NLS-1$
			okButton.setActionCommand("ok"); //$NON-NLS-1$
			okButton.addActionListener(this);
		}
		return okButton;
	}

	private JPanel getFieldPanel() {
		if (fieldPanel == null) {
			fieldPanel = new JPanel();
			fieldPanel.setLayout(new GridLayout(3, 2));
			addLabeledField(fieldPanel, Messages.getString("EditPanel.first_name"), getFirstNameField()); //$NON-NLS-1$
			addLabeledField(fieldPanel, Messages.getString("EditPanel.last_name"), getLastNameField()); //$NON-NLS-1$
			addLabeledField(fieldPanel, Messages.getString("EditPanel.date_of_birth"), getDateOfBirthField()); //$NON-NLS-1$
		}
		return fieldPanel;
	}

	

	private void addLabeledField(JPanel panel, String labelText, JTextField textField) {
		JLabel label = new JLabel(labelText);
		label.setLabelFor(textField);
		panel.add(label);
		panel.add(textField);
		
	}

	private JTextField getFirstNameField() {
		if (firstNameField == null) {
			firstNameField = new JTextField();
			firstNameField.setName("firstNameField"); //$NON-NLS-1$
		}
		return firstNameField;
	}
	
	private JTextField getDateOfBirthField() {
		if (dateOfBirthField == null) {
			dateOfBirthField = new JTextField();
			dateOfBirthField.setName("dateOfBirthField"); //$NON-NLS-1$
		}
		return dateOfBirthField;
	}

	private JTextField getLastNameField() {
		if (lastNameField == null) {
			lastNameField = new JTextField();
			lastNameField.setName("lastNameField"); //$NON-NLS-1$
		}
		return lastNameField;
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if("ok".equalsIgnoreCase(e.getActionCommand())) {
			user.setFirstName(getFirstNameField().getText());
			user.setLastName(getLastNameField().getText());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				user.setDateOfBirth(dateFormat.parse(getDateOfBirthField().getText()));
			} catch (ParseException e1) {
				getDateOfBirthField().setBackground(Color.RED);
				return;
			}
			try {
				parent.getDao().update(user);
			} catch (DatabaseException e1) {
				JOptionPane.showMessageDialog(this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		clearFields();
		this.setVisible(false);
		parent.showBrowsePanel();
	        
		
	}

	private void clearFields() {
		getFirstNameField().setText("");
		getFirstNameField().setBackground(bgColor);
		
		getLastNameField().setText("");
		getLastNameField().setBackground(bgColor);
		
		getDateOfBirthField().setText("");
		getDateOfBirthField().setBackground(bgColor);
		
	}

	public void showEditPanel(User user) {
		 this.user = user;
	        getFirstNameField().setText(user.getFirstName());
	        getLastNameField().setText(user.getLastName());
	        DateFormat dateFormat = DateFormat.getDateInstance();
	        getDateOfBirthField().setText(dateFormat.format(user.getDateOfBirth()));
		
	}
	 
	

}
