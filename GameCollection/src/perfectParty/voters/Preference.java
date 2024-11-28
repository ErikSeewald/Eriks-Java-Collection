package perfectParty.voters;

import java.awt.Color;

import perfectParty.party.Policy;
import perfectParty.party.PolicyPoints;

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
	PlusPlus(2, "++", new Color(100, 180, 120)), 
	Plus(1, "+", new Color(150, 200, 160)), 
	Zero(0, "=", new Color(210, 210, 220)), 
	Minus(-1, "-", new Color(200, 130, 150)), 
	MinusMinus(-2, "--", new Color(200, 80, 100));
	
	public int pointMultiplier;
	public final String symbol;
	public final Color color;
	Preference(int pointMultiplier, String symbol, Color color)
	{
		this.pointMultiplier = pointMultiplier;
		this.symbol = symbol;
		this.color = color;
	}
	
	/**
	 * Weighs the given number of {@link PolicyPoints} by the {@link Preference}'s point multiplier.
	 */
	public int weighPoints(int numPoints)
	{
		return numPoints * this.pointMultiplier;
	}
	
	/**
	 * Returns whether the {@link Preference} is positive (Plus or PlusPlus)
	 */
	public boolean isPositive()
	{
		return this.pointMultiplier > 0;
	}
	
	/**
	 * Returns whether the {@link Preference} is negative (Minus or MinusMinus)
	 */
	public boolean isNegative()
	{
		return this.pointMultiplier < 0;
	}
}
