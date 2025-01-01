package perfectParty.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import ejcMain.util.EJC_GUI.EJC_MenuBar;
import perfectParty.EJC_PerfectParty;
import perfectParty.election.ElectionHandler;
import perfectParty.election.ElectionResult;
import perfectParty.party.PolicyCollection;
import perfectParty.voters.Population;

/**
 * Core class of the GUI for {@link EJC_PerfectParty}. 
 * Manages the EJC {@link JFrame} as well as all sub {@link JPanels} and communicates with the {@link ElectionHandler}.
 */
public class FrameManager implements ActionListener
{
	private EJC_PerfectParty frame;
	private ElectionHandler electionHandler;
	
	// GAME VIEW
	private PartyPanel partyPanel;
	private VoterPanel voterPanel;
	private JSplitPane gameSplitPane;
	
	// RESULT VIEW
	private PartyResultPanel playerResultPanel;
	private PartyResultPanel cpuResultPanel;
	private JSplitPane resultSplitPane;
	
	// JMENU
	private JMenuItem runElectionItem = new JMenuItem("Run election");
	private JMenuItem nextRoundItem = new JMenuItem("Next round");
	private JMenuItem restartItem = new JMenuItem("Restart");
	private JMenuItem[] actionItems = new JMenuItem[] {runElectionItem, nextRoundItem, restartItem};
	private JMenuItem rulesItem = new JMenuItem("Rules");
	private JMenuItem controlsItem = new JMenuItem("Controls");
	private JMenuItem[] infoItems = new JMenuItem[] {rulesItem, controlsItem};

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
		frame.setBounds(0, 0, 1000, 600);

		this.buildGameView();
		this.buildResultView();
		this.buildMenuBar();

		frame.setVisible(true);
	}
	
	private void buildGameView()
	{
		this.partyPanel = new PartyPanel(electionHandler.getPlayerParty());
		this.voterPanel = new VoterPanel(electionHandler.getPopulation());
		gameSplitPane = new JSplitPane();
		ElectionStyle.buildSplitScrollPane(gameSplitPane, partyPanel, voterPanel);
		gameSplitPane.setDividerLocation(frame.getWidth() / 4);

	}
	
	private void buildResultView()
	{
		PolicyCollection policyCollection = electionHandler.getPolicyCollection();
		Population population = electionHandler.getPopulation();
		
		this.playerResultPanel = new PartyResultPanel(electionHandler.getPlayerParty(), policyCollection, population);
		this.cpuResultPanel = new PartyResultPanel(electionHandler.getCPUParty(), policyCollection, population);
		
		resultSplitPane = new JSplitPane();
		ElectionStyle.buildSplitScrollPane(resultSplitPane, playerResultPanel, cpuResultPanel);
		resultSplitPane.setDividerLocation((int) (frame.getWidth() * 0.485));
	}
	
	private void buildMenuBar()
	{
		EJC_MenuBar menuBar = new EJC_MenuBar(frame);
		menuBar.addEJCMenu("Actions", actionItems);
		menuBar.addEJCMenu("Info", infoItems);
		
		for (JMenuItem item : actionItems)
		{item.addActionListener(this);}
		
		for (JMenuItem item : infoItems)
		{item.addActionListener(this);}
	}
	
	/**
	 * Updates the frame to display the result view using the given {@link ElectionResult}.
	 */
	public void displayResult(ElectionResult result, boolean hasPlayerWon)
	{
		frame.remove(gameSplitPane);
		frame.add(resultSplitPane);
		playerResultPanel.updateResult(result);
		cpuResultPanel.updateResult(result);
		
		//JMENU
		if (hasPlayerWon) {nextRoundItem.setEnabled(true);}
		else {nextRoundItem.setText("Next round (Disabled: Lost election)");}
		runElectionItem.setEnabled(false);
		
		frame.revalidate();
		frame.repaint();
	}
	
	/**
	 * Updates the frame to display the standard game view.
	 */
	public void displayGameView()
	{
		frame.remove(resultSplitPane);
		playerResultPanel.stopAnimation();
		cpuResultPanel.stopAnimation();
		
		frame.add(gameSplitPane);
		
		PolicyCollection policyCollection = electionHandler.getPolicyCollection();
		voterPanel.displayPreferences(policyCollection);
		voterPanel.updatePopulationLabel();
		partyPanel.displayPolicyPoints(policyCollection);
		partyPanel.updatePolicyPointsDisplay();
		
		// JMENU
		nextRoundItem.setEnabled(false);
		nextRoundItem.setText("Next round");
		runElectionItem.setEnabled(true);
		
		frame.revalidate();
		frame.repaint();
	}
	
	/**
	 * Sets the {@link ElectionHandler} instance that is to be linked to the GUI.
	 */
	public void setElectionHandler(ElectionHandler electionHandler)
	{
		this.electionHandler = electionHandler;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		
		if (source == runElectionItem)
		{
			this.electionHandler.runElection();
		}
		
		else if (source == nextRoundItem)
		{
			this.electionHandler.nextRound();
		}
		
		else if (source == restartItem)
		{
			this.electionHandler.restart();
		}
		
		else if (source == rulesItem)
		{
			InfoDialogues.displayInfo(InfoDialogues.InfoType.RULES);
		}
		
		else if (source == controlsItem)
		{
			InfoDialogues.displayInfo(InfoDialogues.InfoType.CONTROLS);
		}
		
		
	}
}