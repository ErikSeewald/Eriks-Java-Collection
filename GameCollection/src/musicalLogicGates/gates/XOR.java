package musicalLogicGates.gates;

/**
 * Logic {@link Gate} representing the logical XOR function.
 */
public class XOR extends Gate 
{
	/**
	 * Creates a new {@link XOR} {@link Gate} at the given coordinates.
	 * 
	 * @param x the x coordinate.
	 * @param y the y coordinate.
	 */
	public XOR(int x, int y) 
	{
		super(x, y);
	}
	
	@Override
	public boolean output(boolean a, boolean b) 
	{
		return a ^ b;
	}

	@Override
	public GateType getType() 
	{
		return GateType.XOR;
	}
}