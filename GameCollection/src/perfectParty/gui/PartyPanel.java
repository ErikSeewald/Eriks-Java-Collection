package perfectParty.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import perfectParty.party.Party;
import perfectParty.party.Policy;
import perfectParty.party.PolicyCollection;
import perfectParty.voters.Preference;

public class PartyPanel extends JPanel
{
	private static final long serialVersionUID = 5227466209577033023L;

	public PartyPanel()
	{
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel headerPanel = new JPanel();
		headerPanel.setBackground(ElectionStyle.BACKGROUND_COL);
		headerPanel.setPreferredSize(new Dimension(0, 100));
		headerPanel.add(new JLabel("Party"));
		this.add(headerPanel);
	}
	
	public void displayPolicyPoints(PolicyCollection policyCollection, Party party)
	{
		ArrayList<Policy> policies = policyCollection.getPolicies();
		for (int i = 0; i < policies.size(); i++)
		{
			JPanel contentPanel = new JPanel();
			contentPanel.setBackground(i % 2 == 0 ? Color.WHITE : Color.CYAN);
			contentPanel.setPreferredSize(new Dimension(0, 50));
			contentPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			contentPanel.add(new JLabel(policies.get(i).name + ": " + party.numPointsSpentOn(policies.get(i))));
			this.add(contentPanel);
		}
	}
}
