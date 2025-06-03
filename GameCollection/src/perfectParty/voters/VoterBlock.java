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
	 * Returns the {@link Preference} of this VoterBlock for the given {@link Policy}.
	 * Defaults to {@link Prefernce.Zero} if the given {@link Policy} is untracked.
	 */
	public Preference getPreference(Policy policy)
	{
		Preference preference = preferences.get(policy);
		if (preference == null)
		{
			preference = Preference.Zero;
		}
		return preference;
	}
	
	/**
	 * Generates {@link Preference}s for each policy in the given {@link PolicyCollection} and
	 * clears the old set of {@link Preference}s.
	 * @param policyCollection the {@link PolicyCollection} to generate for
	 * @param random The {@link Random} object to be used for generation
	 */
	public void generatePreferences(PolicyCollection policyCollection, Random random)
	{
		this.preferences.clear();
		for (Policy policy: policyCollection.getPolicies())
		{
			Preference[] prefs = Preference.values();
			int rand = random.nextInt(prefs.length);
			
			// Spin again once more if preference is zero to avoid situations where the best choice is not to spend
			// points on any policy because the population hates or is indifferent to all of them.
			if (prefs[rand] == Preference.Zero)
			{rand = random.nextInt(prefs.length);}
			
			// 3/4 chance to apply a heavy lean towards one preference
			if (random.nextInt(4) != 0)
			{
				// Same for all VoterBlocks -> if random chance to apply preference occurs, then that preference
				// is shared. Otherwise it would still look like a mostly flat random distribution
				int preferenceIndex = policy.hashCode() % prefs.length;
				
				// Avoid preference towards zero. Prefer a positive preference in that case.
				if (prefs[preferenceIndex] == Preference.Zero) 
				{
					preferenceIndex = Preference.Plus.ordinal();
				}
				
				if (rand > preferenceIndex) {rand--;}
				if (rand < preferenceIndex) {rand++;}
			}
			
			preferences.put(policy, prefs[rand]);
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
				int pointsSpent = party.getNumPointsSpentOn(entry.getKey());
				
				Preference preference = entry.getValue();
				score += preference.weighPoints(pointsSpent);
				
				// Penalty for not spending points on a policy the voters care about
				if (pointsSpent == 0 && preference.isPositive())
				{
					score -= preference.pointMultiplier;
				}
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