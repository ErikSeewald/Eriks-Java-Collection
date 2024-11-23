package perfectParty.party;

import java.util.HashMap;

/**
 * Class representing a party in the election. A party can spend {@link PolicyPoints} on {@link Policy} objects
 * to appeal to the {@link Preference}s of {@link VoterBlock}s.
 */
public class Party
{
	public final String name;
	private PolicyPoints policyPoints;
	private HashMap<Policy, PolicyPoints> spentPoints;
	
	public Party(String name, PolicyPoints policyPoints)
	{
		this.name = name;
		this.policyPoints = policyPoints;
		this.spentPoints = new HashMap<>();
	}
	
	/**
	 * Returns the number of {@link PolicyPoints} that the party has spent on the given {@link Policy}.
	 */
	public int numPointsSpentOn(Policy policy) 
	{
		PolicyPoints spent = spentPoints.get(policy);
		if (spent != null)
		{
			return spent.getAmount();
		}
		return 0;
	}
	
	/**
	 * Clears all spent {@link PolicyPoints} and restores the available points to the given
	 * {@link PolicyPoints} object.
	 */
	public void resetPoints(PolicyPoints policyPoints)
	{
		this.spentPoints.clear();
		this.policyPoints = policyPoints;
	}
	
	/**
	 * Spends the given number of the total {@link PolicyPoints} on the given {@link Policy}.
	 * @param pointNum number of {@link PolicyPoints} to spend
	 * @param policy the {@link Policy} to spend on
	 */
	public void spendPoints(int pointNum, Policy policy)
	{
		PolicyPoints allocate = policyPoints.allocate(pointNum);
		PolicyPoints alreadySpent = spentPoints.get(policy);
		if (alreadySpent != null)
		{
			allocate.consumePoints(alreadySpent);
		}
		
		spentPoints.put(policy, allocate);
	}
	
	/**
	 * Refunds the given number of {@link PolicyPoints} from the points spent on given {@link Policy}.
	 * @param pointNum number of {@link PolicyPoints} to refund
	 * @param policy the {@link Policy} to refund from
	 */
	public void refundPoints(int pointNum, Policy policy)
	{
		PolicyPoints spent = spentPoints.get(policy);
		policyPoints.consumePoints(spent.allocate(pointNum));
	}
}
