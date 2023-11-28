package musicalLogicGates.gates;

/**
 * Abstract class representing a logic gate.
 */
public abstract class Gate 
{
	/**
	 * All implemented logic gate types
	 */
	public enum GateType 
	{
		AND, NAND, OR, NOR, XOR, XNOR, NOT, IN, OUT, NULL_GATE
	}
	
	//STATE
	private boolean state;
	
	//INPUTS
	private Gate input1;
	private Gate input2;
	
	//COORDINATES
	public int x;
	public int y;

	protected Gate(int x, int y) 
	{
		// only set inputs to NullGate if object is not a NullGate itself -> prevent loop
		if (!(this instanceof NullGate)) 
		{
			this.input1 = NullGate.instance;
			this.input2 = NullGate.instance;
		}
		
		this.x = x;
		this.y = y;
	}

	/**
	 * Produces an output based on the two input {@link Gate}s. Also updates the
	 * {@link Gate}'s internal state variable.
	 * 
	 * @return {@link boolean} logical output
	 */
	public abstract boolean output();
	
	/**
	 * Updates the internal {@literal boolean} state.
	 * 
	 * @param state {@literal boolean} to update with
	 * @return the {@literal boolean} new state
	 */
	protected boolean updateState(boolean state) 
	{
		this.state = state;
		return this.state;
	}
	
	/**
	 * Returns the internal state.
	 * 
	 * @return {@link boolean} state
	 */
	protected boolean getState() {return this.state;}

	/**
	 * Gets the {@link Gate}'s first input.
	 * 
	 * @return the first input {@link Gate}
	 */
	public Gate getInput1() {return input1;}

	/**
	 * Gets the {@link Gate}'s second input.
	 * 
	 * @return the second input {@link Gate}
	 */
	public Gate getInput2() {return input2;}
	
	/**
	 * Sets the first input {@link Gate}.
	 * 
	 * @param gate must not be {@literal null}
	 * @throws IllegalArgumentException
	 */
	public void setInput1(Gate gate) 
	{
		if (gate == null ) 
		{throw new IllegalArgumentException("Cannot set input to null");}
		
		this.input1 = gate;
	}
	
	/**
	 * Sets the second input {@link Gate}.
	 * 
	 * @param gate must not be {@literal null}
	 * @throws IllegalArgumentException
	 */
	public void setInput2(Gate gate) 
	{
		if (gate == null ) 
		{throw new IllegalArgumentException("Cannot set input to null");
		}
		this.input2 = gate;
	}
	
	/**
	 * Returns the {@link GateType} of this {@link Gate}
	 * 
	 * @return the {@link GateType}
	 */
	public abstract GateType getType();
	
	/**
	 * Removes a given {@link Gate} from this {@link Gate}'s inputs if it is present.
	 * 
	 * @param gate the {@link Gate} to be deleted
	 */
	public void removeGateFromInputs(Gate gate)
	{
		if (getInput1().equals(gate)) {setInput1(NullGate.instance);}
		if (getInput2().equals(gate)) {setInput2(NullGate.instance);}
	}
}