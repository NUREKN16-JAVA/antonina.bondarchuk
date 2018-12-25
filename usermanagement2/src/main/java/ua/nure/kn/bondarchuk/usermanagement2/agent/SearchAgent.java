package ua.nure.kn.bondarchuk.usermanagement2.agent;

import java.util.Collection;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import ua.nure.kn.bondarchuk.usermanagement2.db.DaoFactory;
import ua.nure.kn.bondarchuk.usermanagement2.db.DatabaseException;
import ua.nure.kn.bondarchuk.usermanagement2.gui.SearchGui;

public class SearchAgent extends Agent {
	
	private AID[] aids;
	private SearchGui gui = null;

	/* (non-Javadoc)
	 * @see jade.core.Agent#setup()
	 */
	@Override
	protected void setup() {
		super.setup();
		System.out.println(getAID().getName() + " started.");
		
		gui = new SearchGui(this);
		gui.setVisible(true);
		
		DFAgentDescription description = new DFAgentDescription();
		description.setName(getAID());
		ServiceDescription serviceDescription = new ServiceDescription();
		serviceDescription.setName("JADE-searching");
		serviceDescription.setType("searching");
		description.addServices(serviceDescription);
		try {
			DFService.register(this, description);
			
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		
		
		addBehaviour(new TickerBehaviour(this, 60000) {
			
			

			@Override
			protected void onTick() {
				DFAgentDescription agentDescription = new DFAgentDescription();
				ServiceDescription serviceDescription = new ServiceDescription();
				serviceDescription.setType("searching");
				agentDescription.addServices(serviceDescription);
				try {
					DFAgentDescription[] descriptions = DFService.search(myAgent, agentDescription);
					aids = new AID[descriptions.length];
					for (int i = 0; i < descriptions.length; i++) {
						DFAgentDescription d = descriptions[i];
						aids[i] = d.getName();
					}
				} catch (FIPAException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/* (non-Javadoc)
	 * @see jade.core.Agent#takeDown()
	 */
	@Override
	protected void takeDown() {
		System.out.println(getAID().getName() + " terminated.");
		try {
			DFService.deregister(this);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		gui.setVisible(false);
		gui.dispose();
		super.takeDown();
	}
	
	public void search(String firstName, String lastName) throws SearchException {
		try {
			Collection users = DaoFactory.getInstance().getUserDao().find(firstName, lastName);
			if (users.size() > 0) {
				showUsers(users);
				
			} else {
				addBehaviour(new SearchRequestBehaviour(aids, firstName, lastName));
			}
		} catch (DatabaseException e) {
			throw new SearchException (e);
		}
	}
	
	void showUsers(Collection user) {
		gui.addUsers(user);
	}

}
