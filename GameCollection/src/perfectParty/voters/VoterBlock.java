package perfectParty.voters;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import perfectParty.party.Party;
import perfectParty.party.Policy;
import perfectParty.party.PolicyCollection;

/**
 * Class representing a block of voters who all share the same {@link Policy} preferences.
 * The number of voters is randomized by the {@link Population} class to look like an organic number 
 * within certain bounds so that there never need to be too many block objects as the population grows.
 */
public class VoterBlock
{
	public final long numVoters;
	private HashMap<Policy, Preference> preferences;
	
	public VoterBlock(long numVoters)
	{
		preferences = new HashMap<>();
		this.numVoters = numVoters;
	}
	
	/**
	 * Generates {@link Preference}s for each policy in the given {@link PolicyCollection}
	 * @param policyCollection the {@link PolicyCollection} to generate for
	 * @param random The {@link Random} object to be used for generation
	 */
	public void generatePreferences(PolicyCollection policyCollection, Random random)
	{
		for (Policy policy: policyCollection.getPolicies())
		{
			Preference[] options = Preference.values();
			preferences.put(policy, options[random.nextInt(options.length)]);
		}
	}
	
	/**
	 * Votes on the given {@link Party} array and returns the favorite {@link Party}.
	 * <p>
	 * Candidates are scored by the sum of all their spent {@link PolicyPoints} weighed by the VoterBlock's
	 * {@link Preference}s.
	 */
	public Party vote(Party[] parties)
	{
		if (parties.length < 1)
		{
			throw new IllegalArgumentException("Cannot vote on an empty list of parties");
		}
		
		Party bestParty = parties[0];
		int bestScore = 0;
		
		for (Party party : parties)
		{
			int score = 0;
			for (Map.Entry<Policy, Preference> entry : preferences.entrySet())
			{
				int pointsSpent = party.numPointsSpentOn(entry.getKey());
				score += entry.getValue().weighPoints(pointsSpent);
			}
			
			// In cases where multiple parties get the same score, recency bias is applied and the
			// old bestParty gets overwritten :)
			if (score >= bestScore)
			{
				bestScore = score;
				bestParty = party;
			}
		}
		
		return bestParty;
	}
}