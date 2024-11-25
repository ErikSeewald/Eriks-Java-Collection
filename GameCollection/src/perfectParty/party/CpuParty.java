package perfectParty.party;

import java.util.ArrayList;
import java.util.HashMap;
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

			this.spendPoints((int) (totalPoints * (weight / totalWeight)), policy);
		}
		
		// ALMOST ALL FOR LARGE AMOUNTS OF POLICIES
		System.out.println("Leftover points: " + this.getNumUnspentPoints());
	}
}
