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
	PlusPlus, Plus, Zero, Minus, MinusMinus
}
