package perfectParty.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import perfectParty.election.ElectionResult;
import perfectParty.party.Party;
import perfectParty.party.Policy;
import perfectParty.party.PolicyCollection;
import perfectParty.voters.Population;
import perfectParty.voters.Preference;

public class PartyResultPanel extends JPanel
{
	private static final long serialVersionUID = 654736523443424L;

	private final Party party;
	private PolicyCollection policyCollection;
	private Population population;
	
	private JLabel percentageLabel;
	private JProgressBar progressBar;
	private JLabel votesLabel;
	private Thread animationThread;
	
	private ArrayList<JPanel> policyPanels;

	public PartyResultPanel(Party party, PolicyCollection policyCollection, Population population)
	{
		this.party = party;
		this.policyCollection = policyCollection;
		this.population = population;

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(ElectionStyle.BACKGROUND_COL);

		// HEADER PANEL
		JPanel headerPanel = ElectionStyle.buildHeaderPanel(this, party.name);
		this.add(headerPanel);

		// RESULT PANEL
		JPanel resultPanel = buildResultPanel();
		this.add(resultPanel);
		
		// POLICY PANELS
		this.policyPanels = new ArrayList<>();
	}

	private JPanel buildResultPanel()
	{
		JPanel resultPanel = new JPanel();
		resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.X_AXIS));
		resultPanel.setBackground(ElectionStyle.BRIGHTER_BACKGROUND_COL);
		resultPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

		// PERCENTAGE LABEL
		percentageLabel = new JLabel("00%");
		percentageLabel.setFont(new Font("", Font.BOLD, 45));
		percentageLabel.setForeground(ElectionStyle.HEADER_COL);
		percentageLabel.setAlignmentY(CENTER_ALIGNMENT);

		// PROGRESS BAR
		progressBar = new JProgressBar(SwingConstants.VERTICAL);
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);
		progressBar.setValue(0);
		progressBar.setMinimumSize(new Dimension(40, 120));
		progressBar.setMaximumSize(new Dimension(40, 120));
		progressBar.setAlignmentY(CENTER_ALIGNMENT);

		// TOTAL VOTES LABEL
		votesLabel = new JLabel("0 votes");
		votesLabel.setFont(new Font("", Font.PLAIN, 14));
		votesLabel.setForeground(ElectionStyle.HEADER_COL);
		votesLabel.setAlignmentX(CENTER_ALIGNMENT);
		votesLabel.setHorizontalAlignment(SwingConstants.CENTER);

		// BAR PANEL
		JPanel barPanel = new JPanel();
		barPanel.setLayout(new BoxLayout(barPanel, BoxLayout.Y_AXIS));
		barPanel.setBackground(ElectionStyle.BRIGHTER_BACKGROUND_COL);
		barPanel.add(progressBar);
		barPanel.add(Box.createVerticalStrut(5));
		barPanel.add(votesLabel);

		// BUILD
		resultPanel.add(Box.createVerticalStrut(10));
		resultPanel.add(percentageLabel);
		resultPanel.add(barPanel);
		resultPanel.add(Box.createVerticalStrut(10));

		return resultPanel;
	}

	/**
	 * Updates the view with the given {@link ElectionResult}
	 */
	public void updateResult(ElectionResult electionResult)
	{
		this.buildPolicyPanels();
		
		long absoluteVotes = electionResult.getVotes(party);
		int finalPercentage = electionResult.getPercentage(party);
		boolean isWinner = electionResult.getWinner() == party;
		
		percentageLabel.setForeground(ElectionStyle.HEADER_COL);
		animateProgressBar(progressBar, votesLabel, percentageLabel, finalPercentage, absoluteVotes, isWinner);
	}
	
	private void buildPolicyPanels()
	{
		// Clear old policy panels
		for (JPanel p : policyPanels) {this.remove(p);}
		policyPanels.clear();
		
		ArrayList<Policy> policies = policyCollection.getPolicies();

		for (int i = 0; i < policies.size(); i++)
		{
			final Policy policy = policies.get(i);

			JPanel policyPanel = new JPanel();
			policyPanel.setBackground(i % 2 == 0 ? ElectionStyle.HEADER_COL : ElectionStyle.HEADER_COL_DARKER);
			policyPanel.setPreferredSize(new Dimension(0, 50));
			policyPanel.setBorder(BorderFactory.createLineBorder(ElectionStyle.BORDER_COL));

			JLabel policyLabel = new JLabel("  " + policy.name + ": " + party.getNumPointsSpentOn(policy));
			policyLabel.setFont(new Font("", Font.BOLD, 14));

			policyPanel.setLayout(new GridBagLayout());
	        GridBagConstraints constraints = new GridBagConstraints();
	        constraints.weightx = 1.0; // Stretch horizontally
	        constraints.anchor = GridBagConstraints.WEST; // Left-align
			policyPanel.add(policyLabel, constraints);
			
			HashMap<Preference, Long> preferences = population.getPreferenceDistribution(policies.get(i));
			DistributionBar bar = new DistributionBar(preferences, population.getNumber());
			policyPanel.add(bar, constraints);

			this.add(policyPanel);
			policyPanels.add(policyPanel);
		}
	}

	private void animateProgressBar(JProgressBar progressBar, JLabel votesLabel, JLabel percentageLabel,
			int finalPercentage, long absoluteVotes, boolean isWinner)
	{
		stopAnimation();
		
		animationThread = new Thread(() -> 
		{
			for (int i = 0; i <= finalPercentage; i++)
			{
				final int percentage = i;
				final long currentVotes = (long) ((percentage / (double) finalPercentage) * absoluteVotes);

				SwingUtilities.invokeLater(() -> {
					progressBar.setValue(percentage);
					votesLabel.setText(currentVotes + " votes");
					percentageLabel.setText(String.format("%02d", percentage) + "%");
				});

				try
				{Thread.sleep(20);} 
				catch (InterruptedException e)
				{Thread.currentThread().interrupt();}
			}
			
			if (isWinner) {percentageLabel.setForeground(ElectionStyle.WINNER_COL);}
		});
		animationThread.start();
	}
	
	public void stopAnimation()
	{
		if (animationThread != null && animationThread.isAlive())
		{
			animationThread.interrupt();
		}
	}
}
