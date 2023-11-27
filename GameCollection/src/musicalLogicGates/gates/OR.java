package musicalLogicGates.gates;

public class OR extends Gate 
{
	public OR(int x, int y) 
	{
		super(x, y);
	}
	
	@Override
	public boolean output(boolean a, boolean b) 
	{
		return super.updateState(a||b);
	}

	@Override
	public GateType getType() 
	{
		return GateType.OR;
	}
}
