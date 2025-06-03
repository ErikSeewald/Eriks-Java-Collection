package musicalLogicGates.gates;

/**
 * Logic {@link Gate} representing the logical NOT function.
 */
public class NOT extends Gate
{
	/**
	 * Creates a new {@link NOT} {@link Gate} at the given coordinates.
	 * 
	 * @param x the x coordinate.
	 * @param y the y coordinate.
	 */
	public NOT(int x, int y) 
	{
		super(x, y);
	}
	
	@Override
	public boolean output(boolean a, boolean b) 
	{
		return !a;
	}
		
	//ONLY HAS ONE INPUT -> REDIRECT TO setInput()
	@Override
	public void setInput2(Gate gate) {setInput1(gate);}
	
	//ONLY RETURN INPUT AT INPUT1, OTHERWISE IT WILL BE VISUALIZED WITH TWO WIRES TO ONE INPUT
	@Override
	public Gate getInput2()
	{return NullGate.instance;}

	@Override
	public GateType getType() 
	{
		return GateType.NOT;
	}
}
