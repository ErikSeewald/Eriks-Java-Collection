package perfectParty.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import perfectParty.party.Party;
import perfectParty.party.Policy;
import perfectParty.party.PolicyCollection;

/**
 * {@link JPanel} displaying the party view in which the player can spend {@link PolicyPoints}.
 */
public class PartyPanel extends JPanel
{
	private static final long serialVersionUID = 5227466209577033023L;

	private final Party party;
	private JLabel pointsLabel;

	public PartyPanel(Party party)
	{
		this.party = party;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(ElectionStyle.BACKGROUND_COL);

		JPanel headerPanel = ElectionStyle.buildHeaderPanel(this, party.name);
		this.add(headerPanel);

		this.pointsLabel = new JLabel("Policy Points: " + party.getNumUnspentPoints());
		JPanel pointsPanel = ElectionStyle.buildSubHeaderPanel(this, pointsLabel);
		this.add(pointsPanel);
	}
	
	/**
	 * Displays the given {@link PolicyCollection} together with the amount of {@link PolicyPoints} currently
	 * spent on each {@link Policy}
	 */
	public void displayPolicyPoints(PolicyCollection policyCollection)
	{
		ArrayList<Policy> policies = policyCollection.getPolicies();

		for (int i = 0; i < policies.size(); i++)
		{
			final Policy policy = policies.get(i);

			JPanel policyPanel = new JPanel();
			policyPanel.setBackground(i % 2 == 0 ? ElectionStyle.HEADER_COL : ElectionStyle.HEADER_COL_DARKER);
			policyPanel.setPreferredSize(new Dimension(0, 50));
			policyPanel.setBorder(BorderFactory.createLineBorder(ElectionStyle.BORDER_COL));

			JLabel policyLabel = new JLabel(getPolicyString(party, policy));
			policyLabel.setFont(new Font("", Font.BOLD, 14));

			policyPanel.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mousePressed(MouseEvent e)
				{policyPanelPressed(e, policy, policyLabel);}
			});

			policyPanel.setLayout(new GridBagLayout());
	        GridBagConstraints constraints = new GridBagConstraints();
	        constraints.weightx = 1.0; // Stretch horizontally
	        constraints.anchor = GridBagConstraints.WEST; // Left-align
			policyPanel.add(policyLabel, constraints);

			this.add(policyPanel);
		}
	}

	private void policyPanelPressed(MouseEvent e, Policy policy, JLabel policyLabel)
	{
		int button = e.getButton();
		if (button == MouseEvent.BUTTON1 && party.getNumUnspentPoints() > 0)
		{
			party.spendPoints(1, policy);
		}

		else if (button == MouseEvent.BUTTON3 && party.getNumPointsSpentOn(policy) > 0)
		{
			party.refundPoints(1, policy);
		}

		policyLabel.setText(getPolicyString(party, policy));
		pointsLabel.setText("Policy Points: " + party.getNumUnspentPoints());
	}

	private static String getPolicyString(Party party, Policy policy)
	{
		int pointsSpent = party.getNumPointsSpentOn(policy);
		String labelString = "   " + policy.name + ": ";
		
		labelString += "[" + pointsSpent + "] ";
		
		for (int i = 0; i < pointsSpent / 10; i++)
		{
			labelString += "O";
		}
		
		for (int j = 0; j < pointsSpent % 10; j++)
		{
			labelString += "X";
		}

		return labelString;
	}
}