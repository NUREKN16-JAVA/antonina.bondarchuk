package ua.nure.kn.bondarchuk.usermanagement2.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ua.nure.kn.bondarchuk.usermanagement2.User;
import ua.nure.kn.bondarchuk.usermanagement2.db.DaoFactory;
import ua.nure.kn.bondarchuk.usermanagement2.db.UserDao;
import ua.nure.kn.bondarchuk.usermanagement2.util.Messages;

public class MainFrame extends JFrame {

	private static final int FRAME_HEIGHT = 600;
	private static final int FRAME_WIDTH = 800;
	private JPanel contentPanel;
	private BrowsePanel browsePanel;
	private AddPanel addPanel;
	private EditPanel editPanel;
	private UserDao dao;
	private DeletePanel deletePanel;
	private DetailsPanel detailsPanel;
	
	public UserDao getDao() {
		return dao;
	}



	public MainFrame() {
		super();
		dao = DaoFactory.getInstance().getUserDao();
		initialize();
	}
	
	
	
	private void initialize() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setTitle(Messages.getString("MainFrame.user_management")); //localize //$NON-NLS-1$
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

	private JPanel getBrowsePanel() {
		if (browsePanel == null) {
			browsePanel = new BrowsePanel(this);
		}
		((BrowsePanel) browsePanel).initTable();
		return browsePanel;
	}
	
	public void showBrowsePanel() {
		showPanel(getBrowsePanel());
	}

	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
		frame.setVisible(true);

	}

	public void showAddPanel() {
		showPanel(getAddPanel());
		
	}

	private void showPanel(JPanel panel) {
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setVisible(true);
		panel.repaint();
		
	}

	private AddPanel getAddPanel() {
		if (addPanel == null) {
			addPanel = new AddPanel(this);
			
		}
		return addPanel;
	}



	public void showEditPanel(User user) {
		 EditPanel editPanel = getEditPanel();
	        editPanel.showEditPanel(user);
	        this.showPanel(getEditPanel());
	}



	private EditPanel getEditPanel() {
		if (editPanel == null) {
			editPanel = new EditPanel(this);
			
		}
		return editPanel;
	}



	public DeletePanel getDeletePanel() {
		if (deletePanel == null) {
			deletePanel = new DeletePanel(this);
			
		}
		return deletePanel;
		
	}
	
	public void showDeletePanel(User find) {
		DeletePanel deletePanel = getDeletePanel();
        deletePanel.showDeletePanel(find);
        this.showPanel(getDeletePanel());
	}



	public void showDetailsPanel(User find) {
		DetailsPanel detailsPanel = getDetailsPanel();
		detailsPanel.showDetailsPanel(find);
        this.showPanel(getDetailsPanel());
}



	public DetailsPanel getDetailsPanel() {
		if (detailsPanel == null) {
			detailsPanel = new DetailsPanel(this);
			
		}
		return detailsPanel;
	}
		
	


}
