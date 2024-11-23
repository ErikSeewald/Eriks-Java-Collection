package perfectParty.party;

/**
 * Class representing a single policy that a party can spend {@link PolicyPoints} on.
 */
public class Policy
{
	public final String name;
	
	public Policy(String name)
	{
		this.name = name;
	}
	
	/**
	 * Returns a unique name for a policy that has the given index in the {@link PolicyCollection}
	 */
	public static String nameFromIndex(int index)
	{
		String name = "Policy ";
		
		// Letter
		name += (char) (65 + (index % 26));
		
		// Optionally: Trailing number (e.g. "Policy A" is different from "Policy A2")
		int trailNum = (index / 26) + 1;
		if (trailNum > 1)
		{
			name += trailNum;
		}
		
		return name;
	}
}
