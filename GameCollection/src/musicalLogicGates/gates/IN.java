package musicalLogicGates.gates;

public class IN extends Gate
{
	public IN(int x, int y) 
	{
		super(x, y);
	}

	private boolean state;
	
	public boolean switchState() 
	{
		this.state = !state;
		return state;
	}
	
	@Override
	public void setInput1(Gate gate)
	{}
	
	@Override
	public void setInput2(Gate gate)
	{}

	@Override
	public boolean output() 
	{
		return state;
	}

	@Override
	public GateType getType() 
	{
		return GateType.IN;
	}
}
