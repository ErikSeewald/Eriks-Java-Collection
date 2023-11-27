package musicalLogicGates.gates;

public class XNOR extends Gate 
{
	public XNOR(int x, int y) 
	{
		super(x, y);
	}
	
	@Override
	public boolean output(boolean a, boolean b) 
	{
		return super.updateState(!(a^b));
	}

	@Override
	public GateType getType() 
	{
		return GateType.XNOR;
	}

}
