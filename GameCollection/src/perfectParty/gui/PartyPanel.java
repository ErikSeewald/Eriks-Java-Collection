package perfectParty.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import perfectParty.party.Party;
import perfectParty.party.Policy;
import perfectParty.party.PolicyCollection;

public class PartyPanel extends JPanel
{
	private static final long serialVersionUID = 5227466209577033023L;

	public PartyPanel()
	{
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(ElectionStyle.BACKGROUND_COL);

		JPanel headerPanel = ElectionStyle.buildHeaderPanel(this, "Player Party");
		this.add(headerPanel);
	}

	public void displayPolicyPoints(PolicyCollection policyCollection, Party party)
	{
		ArrayList<Policy> policies = policyCollection.getPolicies();

		for (int i = 0; i < policies.size(); i++)
		{
			JPanel contentPanel = new JPanel();
			contentPanel.setBackground(i % 2 == 0 ? ElectionStyle.HEADER_COL : ElectionStyle.HEADER_COL_DARKER);
			contentPanel.setPreferredSize(new Dimension(0, 50));
			contentPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			contentPanel.add(new JLabel(policies.get(i).name + ": " + party.numPointsSpentOn(policies.get(i))));

			this.add(contentPanel);
		}
	}
}