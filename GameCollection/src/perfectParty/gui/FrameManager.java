package perfectParty.gui;

import java.awt.Color;

import javax.swing.BoundedRangeModel;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

import ejcMain.util.EJC_GUI;
import ejcMain.util.EJC_GUI.EJC_MenuBar;
import perfectParty.EJC_PerfectParty;
import perfectParty.election.ElectionHandler;
import perfectParty.party.PolicyCollection;
import perfectParty.voters.Population;

public class FrameManager
{
	private EJC_PerfectParty frame;
	private PartyPanel partyPanel;
	private VoterPanel voterPanel;

	private ElectionHandler electionHandler;

	public FrameManager(EJC_PerfectParty frame)
	{
		this.frame = frame;
	}

	/**
	 * Builds the PerfectParty frame made up of the different sub panels with
	 * synchronized scroll bars. Uses the {@link EJC_PerfectParty} instance given at
	 * instantiation as the base frame.
	 */
	public void buildFrame()
	{
		frame.setResizable(false);
		frame.setBounds(0, 0, 1200, 400);

		this.partyPanel = new PartyPanel();
		this.voterPanel = new VoterPanel();

		// WARP PANELS IN SCROLL PANES
		JScrollPane scrollPaneParty = new JScrollPane(partyPanel);
		JScrollPane scrollPaneVoters = new JScrollPane(voterPanel);

		// HIDE LEFT SCROLLBAR AND STYLE RIGHT SCROLLBAR
		scrollPaneParty.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPaneVoters.getVerticalScrollBar().setUI(new EJC_GUI.EJCScrollBar());

		// SYNCHRONIZE SCROLLBARS
		BoundedRangeModel partyModel = scrollPaneParty.getVerticalScrollBar().getModel();
		BoundedRangeModel voterModel = scrollPaneVoters.getVerticalScrollBar().getModel();

		partyModel.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				voterModel.setValue(partyModel.getValue());
			}
		});

		voterModel.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				partyModel.setValue(voterModel.getValue());
			}
		});

		// DIVIDE FRAME USING SPLITPANE
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPaneParty, scrollPaneVoters);
		splitPane.setDividerLocation((int) (frame.getWidth() * 0.35));
		splitPane.setUI(new BasicSplitPaneUI());

		frame.add(splitPane);
		frame.setVisible(true);
		
		// MENUBAR
		EJC_MenuBar menuBar = new EJC_MenuBar(frame);
		menuBar.addEJCMenu("Menu", new JMenuItem[] {});
		menuBar.addEJCMenu("Info", new JMenuItem[] {});

		this.startRound();
		
	}
	
	private void startRound()
	{
		electionHandler.startRound();
		PolicyCollection policyCollection = electionHandler.getPolicyCollection();
		Population population = electionHandler.getPopulation();
		
		voterPanel.displayPreferences(policyCollection, population);
		partyPanel.displayPolicyPoints(policyCollection, electionHandler.getCPUParty());
	}

	/**
	 * Sets the {@link ElectionHandler} instance that is to be linked to the GUI.
	 */
	public void setElectionHandler(ElectionHandler electionHandler)
	{
		this.electionHandler = electionHandler;
	}
}