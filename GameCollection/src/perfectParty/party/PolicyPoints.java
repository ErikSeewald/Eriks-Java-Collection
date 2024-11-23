package perfectParty.party;

/**
 * Class representing the points a party can spend on each {@link Policy}.
 */
public class PolicyPoints
{
	private int amount;
	
	private PolicyPoints(int amount)
	{
		if (amount < 0)
		{
			throw new IllegalArgumentException("PolicyPoint amount cannot be < 0");
		}
		
		this.amount = amount;
	}
	
	public int getAmount()
	{
		return this.amount;
	}
	
	/**
	 * Allocates a set of PolicyPoints.
	 * @param numToAllocate the number of points to allocate
	 * @return the newly allocated PolicyPoints object
	 * @throws IllegalArgumentException if numToAllocate is less than 0 or greater than 
	 * the number of available points in this object 
	 */
	public PolicyPoints allocate(int numToAllocate)
	{
		if (numToAllocate > this.amount)
		{
			throw new IllegalArgumentException("Cannot allocate more PolicyPoints than are available"
					+ "(" + numToAllocate + " > " + this.amount + ")");
		}
		
		if (numToAllocate < 0)
		{
			throw new IllegalArgumentException("Cannot allocate negative points");
		}
		
		this.amount -= numToAllocate;
		return new PolicyPoints(numToAllocate);
	}
	
	/**
	 * Consumes the points of the given PolicyPoints object, leaving that one with 0 points.
	 */
	public void consumePoints(PolicyPoints pointsToConsume)
	{
		this.amount += pointsToConsume.amount;
		pointsToConsume.amount = 0;
	}
	
	/**
	 * Creates a starting PolicyPoints object with the given number of points.
	 * Since the constructor is private, all other PolicyPoints objects should be created using
	 * the allocate() method.
	 */
	public static PolicyPoints getInitialPoints(int initialPointNum)
	{
		return new PolicyPoints(initialPointNum);
	}
}
