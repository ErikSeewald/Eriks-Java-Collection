package perfectParty.voters;

import java.util.HashMap;
import java.util.Random;
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
}
