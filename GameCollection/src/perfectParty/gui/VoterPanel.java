package perfectParty.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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

/**
 * {@link JPanel} displaying the voter view in which the {@link Population}'s
 * {@link Preference} distribution for each {@link Policy} is visualized.
 */
public class VoterPanel extends JPanel
{
	private static final long serialVersionUID = 8469894058704990916L;

	private JLabel populationLabel;
	private Population population;

	public VoterPanel(Population population)
	{
		this.population = population;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(ElectionStyle.BACKGROUND_COL);

		JPanel headerPanel = ElectionStyle.buildHeaderPanel(this, "Voters");
		this.add(headerPanel);

		this.populationLabel = new JLabel(this.getPopulationString());
		JPanel populationPanel = ElectionStyle.buildSubHeaderPanel(this, this.populationLabel);
		this.add(populationPanel);
	}

	/**
	 * Displays the given {@link PolicyCollection} together with the
	 * {@link Preference}s of the {@link Population}
	 */
	public void displayPreferences(PolicyCollection policyCollection)
	{
		// Layout constraints
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 1.0;

		// Loop over policies
		ArrayList<Policy> policies = policyCollection.getPolicies();
		long popNum = population.getNumber();
		for (int i = 0; i < policies.size(); i++)
		{
			HashMap<Preference, Long> preferences = population.getPreferenceDistribution(policies.get(i));

			JPanel contentPanel = new JPanel();
			contentPanel.setBackground(i % 2 == 0 ? ElectionStyle.HEADER_COL : ElectionStyle.HEADER_COL_DARKER);
			contentPanel.setPreferredSize(new Dimension(0, 50));
			contentPanel.setBorder(BorderFactory.createLineBorder(ElectionStyle.BORDER_COL));
			contentPanel.setLayout(new GridBagLayout());

			String distributionStr = "| ";
			for (Preference preference : Preference.values())
			{
				distributionStr += preference.symbol + " " + preferences.get(preference) + " | ";
			}
			contentPanel.add(new JLabel(distributionStr), constraints);

			DistributionBar bar = new DistributionBar(preferences, popNum);
			contentPanel.add(bar, constraints);

			this.add(contentPanel);
		}
	}

	private String getPopulationString()
	{
		return "Population: " + String.format("%,d", population.getNumber()).replace(",", " ");
	}
}
