package musicalLogicGates.gates;

public class NAND extends Gate 
{
	public NAND(int x, int y) 
	{
		super(x, y);
	}
	
	@Override
	public boolean output() 
	{
		return super.updateState(!(super.getInput1().output() && super.getInput2().output()));
	}
	
	@Override
	public GateType getType() 
	{
		return GateType.NAND;
	}
}
