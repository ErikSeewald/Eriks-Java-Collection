package musicalLogicGates.gates;

/**
 * Logic {@link Gate} representing the logical OR function.
 */
public class OR extends Gate 
{
	/**
	 * Creates a new {@link OR} {@link Gate} at the given coordinates.
	 * 
	 * @param x the x coordinate.
	 * @param y the y coordinate.
	 */
	public OR(int x, int y) 
	{
		super(x, y);
	}
	
	@Override
	public boolean output(boolean a, boolean b) 
	{
		return a || b;
	}

	@Override
	public GateType getType() 
	{
		return GateType.OR;
	}
}