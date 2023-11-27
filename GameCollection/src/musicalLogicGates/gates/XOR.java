package musicalLogicGates.gates;

public class XOR extends Gate 
{
	public XOR(int x, int y) 
	{
		super(x, y);
	}
	
	@Override
	public boolean output() 
	{
		return super.updateState(super.getInput1().output() ^ super.getInput2().output());
	}

	@Override
	public GateType getType() 
	{
		return GateType.XOR;
	}
}
