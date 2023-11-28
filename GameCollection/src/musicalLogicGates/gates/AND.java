package musicalLogicGates.gates;

/**
 * Logic {@link Gate} representing the logical AND function.
 */
public class AND extends Gate 
{
	/**
	 * Creates a new {@link AND} {@link Gate} at the given coordinates.
	 * 
	 * @param x the x coordinate.
	 * @param y the y coordinate.
	 */
	public AND(int x, int y) 
	{
		super(x, y);
	}

	@Override
	public boolean output() 
	{
		return super.getInput1().output() && super.getInput2().output();
	}

	@Override
	public GateType getType() 
	{
		return GateType.AND;
	}
}
