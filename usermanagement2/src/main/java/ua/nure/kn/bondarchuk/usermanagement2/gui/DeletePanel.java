package ua.nure.kn.bondarchuk.usermanagement2.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ua.nure.kn.bondarchuk.usermanagement2.User;
import ua.nure.kn.bondarchuk.usermanagement2.db.DatabaseException;
import ua.nure.kn.bondarchuk.usermanagement2.util.Messages;

public class DeletePanel extends JPanel implements ActionListener{
	
	private MainFrame parent;
	User user;
	private JPanel buttonPanel;
	private JButton cancelButton;
	private JButton okButton;
	
	public DeletePanel(MainFrame parent) {
		this.parent = parent;
		parent.setSize(200,100);
		initialize();
	}

	
	private void initialize() {
		this.setName("deletePanel"); //$NON-NLS-1$
		this.setLayout(new BorderLayout());
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
	public void showDeletePanel(User find) {
		// TODO Auto-generated method stub
		 this.user = find;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if("ok".equalsIgnoreCase(e.getActionCommand())) {
			try {
				parent.getDao().delete(user);
			} catch (DatabaseException e1) {
				JOptionPane.showMessageDialog(this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		this.setVisible(false);
		parent.setSize(800,600);
		parent.showBrowsePanel();
	
	}
	

}
