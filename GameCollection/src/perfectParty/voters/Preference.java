package perfectParty.voters;

/**
 * Enum representing a preference towards a {@link Policy}.
 * <p>
 * These preferences can be (++ | + | 0 | - | --), where Plus is positive, PlusPlus is very positive,
 * Zero is indifferent, Minus is negative and MinusMinus is very negative.
 * <p>
 * If a voter has a positive preference to a {@link Policy}, they will like a party that spends {@link PolicyPoints} on it.
 * A negative preference means they will dislike a party that spends {@link PolicyPoints} on it.
 * Indifferent voters do not care either way.
 */
public enum Preference
{
	PlusPlus(2, "++"), Plus(1, "+"), Zero(0, "0"), Minus(-1, "-"), MinusMinus(-2, "--");
	
	private int pointMultiplier;
	public final String symbol;
	Preference(int pointMultiplier, String symbol)
	{
		this.pointMultiplier = pointMultiplier;
		this.symbol = symbol;
	}
	
	/**
	 * Weighs the given number of {@link PolicyPoints} by the {@link Preference}'s point multiplier.
	 */
	public int weighPoints(int numPoints)
	{
		return numPoints * this.pointMultiplier;
	}
}
