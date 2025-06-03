package perfectParty.party;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import perfectParty.voters.Population;
import perfectParty.voters.Preference;

/**
 * Class extending {@link Party}. Control the moves of the CPU's {@link Party} against the Player's {@link Party}.
 */
public class CpuParty extends Party
{
	public CpuParty(String name, PolicyPoints policyPoints)
	{
		super(name, policyPoints);
	}
	
	/**
	 * Distributes the {@link Party}'s {@link PolicyPoints} based on information from the given {@link Population}
	 * using the given {@link Random}. Because the player needs to have a fair chance, this algorithm does not
	 * maximize votes but is still pretty effective.
	 */
	public void distributePoints(PolicyCollection policyCollection, Population population, Random random)
	{
		ArrayList<Policy> policies = policyCollection.getPolicies();
		
		// Assign weights to each policy based on voter preference
		HashMap<Policy, Double> policyWeights = new HashMap<>();
		double totalWeight = 0;
		for (Policy policy : policies)
		{
			HashMap<Preference, Long> distribution = population.getPreferenceDistribution(policy);
			double weight = (distribution.get(Preference.PlusPlus)
					+ distribution.get(Preference.Plus) * 0.5
					- distribution.get(Preference.Minus) * 0.5
					- distribution.get(Preference.MinusMinus)) / population.getNumber();
			
			if (weight < 0) {weight = 0;}
			totalWeight += weight;
			
			policyWeights.put(policy, weight);
		}
		
		// Distribute PolicyPoints based on weights
		int totalPoints = this.getNumUnspentPoints();
		for (Policy policy : policies)
		{
			double weight = policyWeights.get(policy);
			int pointsToSpend = (int) Math.round(totalPoints * (weight / totalWeight));
			while (pointsToSpend > this.getNumUnspentPoints())
			{
				pointsToSpend--;
			}
			
			this.spendPoints(pointsToSpend, policy);
		}

		// If some points are unspent, just throw them all into the policy with the highest weight
		if (this.getNumUnspentPoints() > 0)
		{
			Policy highestPolicy = policyWeights.entrySet().stream()
		            .max(Map.Entry.comparingByValue())
		            .map(Map.Entry::getKey)
		            .orElse(null);
			this.spendPoints(this.getNumUnspentPoints(), highestPolicy);
		}
	}
}
