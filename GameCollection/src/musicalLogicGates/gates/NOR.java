package musicalLogicGates.gates;

/**
 * Logic {@link Gate} representing the logical NOR function.
 */
public class NOR extends Gate 
{
	/**
	 * Creates a new {@link NOR} {@link Gate} at the given coordinates.
	 * 
	 * @param x the x coordinate.
	 * @param y the y coordinate.
	 */
	public NOR(int x, int y) 
	{
		super(x, y);
	}
	
	@Override
	public boolean output(boolean a, boolean b) 
	{
		return !(a || b);
	}

	@Override
	public GateType getType() 
	{
		return GateType.NOR;
	}
}
