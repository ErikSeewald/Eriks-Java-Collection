package musicalLogicGates.gates;

/**
 * Logic {@link Gate} representing an input. Can be switched on or off.
 */
public class IN extends Gate
{
	/**
	 * Creates a new {@link IN} {@link Gate} at the given coordinates.
	 * 
	 * @param x the x coordinate.
	 * @param y the y coordinate.
	 */
	public IN(int x, int y) 
	{
		super(x, y);
	}
	
	/**
	 * Switches the {@link boolean} state of this {@link IN} {@link Gate} on and off.
	 * 
	 * @return the updated {@link boolean} state
	 */
	public boolean switchState() 
	{
		super.updateState(!super.getState());
		return super.getState();
	}
	
	//IN HAS NO INPUT
	@Override
	public void setInput1(Gate gate)
	{}
	
	@Override
	public void setInput2(Gate gate)
	{}

	//IN RETURNS ITS STATE AS OUTPUT
	@Override
	public boolean output() 
	{
		return super.getState();
	}

	@Override
	public GateType getType() 
	{
		return GateType.IN;
	}
}
