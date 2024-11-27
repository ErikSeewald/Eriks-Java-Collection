package perfectParty.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ejcMain.util.EJC_GUI;
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
		this.setBackground(ElectionStyle.BACKGROUND_COL);

		JPanel headerPanel = ElectionStyle.buildHeaderPanel(this, "Voters");
		this.add(headerPanel);
	}

	public void displayPreferences(PolicyCollection policyCollection, Population population)
	{
		ArrayList<Policy> policies = policyCollection.getPolicies();
		for (int i = 0; i < policies.size(); i++)
		{
			HashMap<Preference, Long> preferences = population.getPreferenceDistribution(policies.get(i));
			
			long popNum = population.getNumber();
			
			String distributionStr = "| ";
			for (Preference preference : Preference.values())
			{
				distributionStr += preference.symbol + " " + preferences.get(preference) * 100 / popNum + "% | ";
			}
			
			JPanel contentPanel = new JPanel();
			contentPanel.setBackground(i % 2 == 0 ? ElectionStyle.HEADER_COL : ElectionStyle.HEADER_COL_DARKER);
			contentPanel.setPreferredSize(new Dimension(0, 50));
			contentPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			contentPanel.add(new JLabel(policies.get(i).name + ": " + distributionStr));
			this.add(contentPanel);
		}
	}
}
