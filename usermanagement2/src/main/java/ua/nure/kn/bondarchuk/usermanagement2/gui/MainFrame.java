package ua.nure.kn.bondarchuk.usermanagement2.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {

	private static final int FRAME_HEIGHT = 600;
	private static final int FRAME_WIDTH = 800;
	private JPanel contentPanel;
	private BrowsePanel browsePanel;

	public MainFrame() {
		super();
		initialize();
	}
	
	private void initialize() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setTitle("User management"); //localize
		this.setContentPane(getContentPanel()); //singleton
	}
	
	private JPanel getContentPanel() {
		if (contentPanel == null) {
			contentPanel = new JPanel();
			contentPanel.setLayout(new BorderLayout());
			contentPanel.add(getBrowsePanel(), BorderLayout.CENTER);
			
		}
		return contentPanel;
	}

	private Component getBrowsePanel() {
		if (browsePanel == null) {
			browsePanel = new BrowsePanel(this);
		}
		return browsePanel;
	}

	public static void main(String[] args) {
		

	}

}
