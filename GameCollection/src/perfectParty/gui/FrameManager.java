package perfectParty.gui;

import javax.swing.BoundedRangeModel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ejcMain.util.EJC_GUI;
import perfectParty.EJC_PerfectParty;
import perfectParty.party.PolicyCollection;
import perfectParty.voters.Population;

public class FrameManager
{
	private EJC_PerfectParty frame;
	private PartyPanel partyPanel;
	private VoterPanel voterPanel;
	
	private PolicyCollection policyCollection;
	
	public FrameManager(EJC_PerfectParty frame)
	{
		this.frame = frame;
	}
	
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

        partyModel.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) 
            {voterModel.setValue(partyModel.getValue());}
        });

        voterModel.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) 
            {partyModel.setValue(voterModel.getValue());}
        });

        // DIVIDE FRAME USING SPLITPANE
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPaneParty, scrollPaneVoters);
        splitPane.setDividerLocation(frame.getWidth() / 2);
        
        frame.add(splitPane);
		frame.setVisible(true);
	}
	
	public void registerPolicyCollection(PolicyCollection policyCollection, Population population)
	{
		this.policyCollection = policyCollection;
		this.voterPanel.addPolicyCollection(policyCollection, population);
	}
}