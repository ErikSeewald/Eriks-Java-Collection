package perfectParty.party;

import java.util.ArrayList;

/**
 * Class representing the current global collection of {@link Policy} objects that the {@link VoterBlock}s
 * can have {@link Preference}s about.
 */
public class PolicyCollection
{
	private ArrayList<Policy> policies;
	
	public PolicyCollection()
	{
		this.policies = new ArrayList<>();
	}
	
	public ArrayList<Policy> getPolicies()
	{
		return this.policies;
	}
	
	/**
	 * Generates a new collection of policies, overwriting the old one.
	 * @param numPolicies the number of policies to generate
	 */
	public void generateCollection(int numPolicies)
	{	
		this.policies.clear();
		this.generateAndAdd(numPolicies);
	}
	
	/**
	 * Generates the given number of policies and adds them to the collection.
	 * @param numPolicies the number of policies to generate
	 */
	public void generateAndAdd(int numPolicies)
	{
		for (int i = 0; i < numPolicies; i++)
		{
			policies.add(new Policy(Policy.nameFromIndex(policies.size())));
		}
	}
}
