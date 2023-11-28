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

	private Gate input = NullGate.instance; //NOT only has one input -> "override" Gate input functionality
	
	@Override
	public boolean output()
	{
		return !input.output();
	}
	
	@Override
	public boolean output(boolean a, boolean b) 
	{
		return !a;
	}
	
	/**
	 * Sets the single input {@link Gate} of this {@link NOT} {@link Gate}.
	 * 
	 * @param gate the {@link Gate} to be set as the input
	 */
	public void setInput(Gate gate)
	{
		if (gate == null)
		{throw new IllegalArgumentException("gate cannot be null!");}
		input = gate;
	}
	
	//ONLY HAS ONE INPUT -> REDIRECT TO setInput()
	@Override
	public void setInput1(Gate gate) {setInput(gate);}
	
	@Override
	public void setInput2(Gate gate) {setInput(gate);}
	
	//ONLY RETURN INPUT AT INPUT1, OTHERWISE IT WILL BE VISUALIZED WITH TWO WIRES TO ONE INPUT
	@Override
	public Gate getInput1()
	{return input;}
	
	@Override
	public Gate getInput2()
	{return NullGate.instance;}

	@Override
	public GateType getType() 
	{
		return GateType.NOT;
	}
}
