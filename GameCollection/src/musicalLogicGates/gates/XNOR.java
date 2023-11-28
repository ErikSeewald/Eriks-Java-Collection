package musicalLogicGates.gates;

/**
 * Logic {@link Gate} representing the logical XNOR function.
 */
public class XNOR extends Gate 
{
	/**
	 * Creates a new {@link XNOR} {@link Gate} at the given coordinates.
	 * 
	 * @param x the x coordinate.
	 * @param y the y coordinate.
	 */
	public XNOR(int x, int y) 
	{
		super(x, y);
	}
	
	@Override
	public boolean output() 
	{
		return !(super.getInput1().output() ^ super.getInput2().output());
	}

	@Override
	public GateType getType() 
	{
		return GateType.XNOR;
	}
}