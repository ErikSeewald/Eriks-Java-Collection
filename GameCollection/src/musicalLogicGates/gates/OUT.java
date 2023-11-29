package musicalLogicGates.gates;

/**
 * Logic {@link Gate} representing an OUT. Used to visualize the output of a circuit.
 */
public class OUT extends Gate
{
	/**
	 * Creates a new {@link OUT} {@link Gate} at the given coordinates.
	 * 
	 * @param x the x coordinate.
	 * @param y the y coordinate.
	 */
	public OUT(int x, int y) 
	{
		super(x, y);
	}
	
	@Override
	public boolean output(boolean a, boolean b)
	{
		return a;
	}
	
	//ONLY HAS ONE INPUT -> REDIRECT TO setInput1()
	@Override
	public void setInput2(Gate gate) {super.setInput1(gate);}
	
	//ONLY RETURN INPUT AT INPUT1, OTHERWISE IT WILL BE VISUALIZED WITH TWO WIRES TO ONE INPUT
	@Override
	public Gate getInput2()
	{return NullGate.instance;}

	@Override
	public GateType getType() 
	{
		return GateType.OUT;
	}
}