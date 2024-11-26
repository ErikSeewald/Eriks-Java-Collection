package perfectParty.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import perfectParty.party.Policy;
import perfectParty.party.PolicyCollection;
import perfectParty.voters.Population;
import perfectParty.voters.Preference;

public class VoterPanel extends JPanel
{
	private static final long serialVersionUID = 8469894058704990916L;

	public VoterPanel()
	{
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.LIGHT_GRAY);
        headerPanel.setPreferredSize(new Dimension(0, 100));
        headerPanel.add(new JLabel("Voters"));
        this.add(headerPanel);
	}
	
	public void addPolicyCollection(PolicyCollection policyCollection, Population population)
	{
		ArrayList<Policy> policies = policyCollection.getPolicies();
        for (int i = 0; i < policies.size(); i++) {
    			HashMap<Preference, Long> preferences = population.getPreferenceDistribution(policies.get(i));
            JPanel contentPanel = new JPanel();
            contentPanel.setBackground(i % 2 == 0 ? Color.WHITE : Color.CYAN);
            contentPanel.setPreferredSize(new Dimension(0, 50));
            contentPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            contentPanel.add(new JLabel(policies.get(i).name + ": " + preferences));
            this.add(contentPanel);
        }
	}
}
