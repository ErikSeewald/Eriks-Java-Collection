package musicalLogicGates.gates;

/**
 * {@link Gate} to be used as the default input for {@link Gate}s that have not yet had inputs set by the user.
 * Always returns false.
 */
public class NullGate extends Gate
{	
	public NullGate() 
	{
		super(0, 0);
	}
	
	@Override
	public boolean output(boolean a, boolean b) 
	{
		return false;
	}
	
	@Override
	public GateType getType() 
	{
		return GateType.NULL_GATE;
	}
}
