package musicalLogicGates.gates;

public class NOT extends Gate
{
	public NOT(int x, int y) 
	{
		super(x, y);
	}

	private Gate input = NullGate.instance;
	
	public boolean output()
	{
		return !input.output();
	}
	
	public void setInput(Gate gate)
	{
		if (gate == null)
		{throw new IllegalArgumentException("gate cannot be null!");}
		input = gate;
	}
	
	@Override
	public void setInput1(Gate gate)
	{setInput(gate);}
	
	@Override
	public void setInput2(Gate gate)
	{setInput(gate);}
	
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
