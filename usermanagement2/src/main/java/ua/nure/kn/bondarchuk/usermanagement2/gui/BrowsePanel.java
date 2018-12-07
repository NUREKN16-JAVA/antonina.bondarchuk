package ua.nure.kn.bondarchuk.usermanagement2.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class BrowsePanel extends JPanel implements ActionListener{

	private MainFrame parent;
	private JScrollPane tablePanel;
	private JTable userTable;
	private JPanel buttonsPanel;
	private JButton addButton;
	private JButton editButton;
	private JButton deleteButton;
	private JButton detailsButton;

	public BrowsePanel(MainFrame mainFrame) {
		parent = mainFrame;
		initialize();
	}
	
	private void initialize() {
		this.setName("browsePanel");
		this.setLayout(new BorderLayout());
		this.add(getTablePanel(), BorderLayout.CENTER);
		this.add(getButtonsPanel(), BorderLayout.SOUTH);
	}
	
	private JPanel getButtonsPanel() {
		if (buttonsPanel == null) {
			buttonsPanel = new JPanel();
			buttonsPanel.add(getAddButton());
			buttonsPanel.add(getEditButton());
			buttonsPanel.add(getDeleteButton());
			buttonsPanel.add(getDetailsButton());
			
		}
		return buttonsPanel;
	}

	private JButton getAddButton() {
		if (addButton == null) {
			addButton = new JButton();
			addButton.setText("Add"); //localize
			addButton.setName("addButton");
			addButton.setActionCommand("add");
			addButton.addActionListener(this);
		}
		return addButton;
	}
	
	private JButton getEditButton() {
		if (editButton == null) {
			editButton = new JButton();
			editButton.setText("Edit"); //localize
			editButton.setName("editButton");
			editButton.setActionCommand("edit");
			editButton.addActionListener(this);
		}
		return editButton;
	}

	
	private JButton getDeleteButton() {
		if (deleteButton == null) {
			deleteButton = new JButton();
			deleteButton.setText("Delete"); //localize
			deleteButton.setName("deleteButton");
			deleteButton.setActionCommand("delete");
			deleteButton.addActionListener(this);
		}
		return deleteButton;
	}
	
	private JButton getDetailsButton() {
		if (detailsButton == null) {
			detailsButton = new JButton();
			detailsButton.setText("Details"); //localize
			detailsButton.setName("detailsButton");
			detailsButton.setActionCommand("details");
			detailsButton.addActionListener(this);
		}
		return detailsButton;
	}
	
	private JScrollPane getTablePanel() {
		if (tablePanel == null) {
			tablePanel = new JScrollPane(getUserTable());
			
		}
		return tablePanel;
	}

	private JTable getUserTable() {
		if (userTable == null) {
			userTable = new JTable();
			userTable.setName("userTable");
		}
		return userTable;
	}

	@Override
    public void actionPerformed(ActionEvent e) {
        
    }

}
