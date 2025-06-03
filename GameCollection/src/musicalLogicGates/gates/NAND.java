package musicalLogicGates.gates;

/**
 * Logic {@link Gate} representing the logical NAND function.
 */
public class NAND extends Gate 
{
	/**
	 * Creates a new {@link NAND} {@link Gate} at the given coordinates.
	 * 
	 * @param x the x coordinate.
	 * @param y the y coordinate.
	 */
	public NAND(int x, int y) 
	{
		super(x, y);
	}
	
	@Override
	public boolean output(boolean a, boolean b) 
	{
		return !(a && b);
	}
	
	@Override
	public GateType getType() 
	{
		return GateType.NAND;
	}
}
