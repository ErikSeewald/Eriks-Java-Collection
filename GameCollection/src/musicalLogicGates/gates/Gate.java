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
	
	//STATE FOR 'PLAYING' MODE
	private boolean playState;
	
	//INPUTS
	private Gate input1;
	private Gate input2;
	
	//COORDINATES
	public int x;
	public int y;
	
	//ANIMATION
	private static final int ANIMATION_FRAMES = 15;
	private int animationFrame = 0;

	protected Gate(int x, int y) 
	{
		// only set inputs to NullGate if object is not a NullGate itself -> prevent loop
		if (!(this instanceof NullGate)) 
		{
			this.input1 = NullGate.instance;
			this.input2 = NullGate.instance;
		}
		
		this.resetPlayState(); //initialize play state
		
		this.x = x;
		this.y = y;
	}

	/**
	 * Produces an output based on the two input {@link Gate}s.
	 * 
	 * @return {@link boolean} logical output
	 */
	public boolean output() {return output(getInput1().output(), getInput2().output());}
	
	/**
	 * Produces an output based on the two input {@link booleans}.
	 * 
	 * @return {@link boolean} logical output
	 */
	public abstract boolean output(boolean a, boolean b);
	
	/**
	 * Updates the internal {@literal boolean} state in 'playing' mode. 
	 * Allows for step by step advancement of the logical circuit.
	 * @param state {@literal boolean} to update with
	 * @return the {@literal boolean} new state
	 *
	 */
	public boolean updatePlayState(boolean state) 
	{
		//start animation if state has switched
		if (this.playState != state)
		{this.animationFrame = ANIMATION_FRAMES;}
		
		this.playState = state;
		return this.playState;
	}
	
	/**
	 * Resets the {@link Gate}'s play state.
	 */
	public void resetPlayState()
	{
		this.playState = this.output(false, false);
	}
	
	/**
	 * Returns whether this {@link Gate} is currently in an animation.
	 * 
	 * @return whether this {@link Gate} is currently in an animation
	 */
	public boolean isAnimating() {return this.animationFrame > 0;}
	
	/**
	 * Advances the animation frame counter.
	 */
	public void advanceAnimation()
	{
		if (!this.isAnimating()) {return;}
		this.animationFrame--;
	}
	
	/**
	 * Stops the animation. Sets the animation frame counter to 0.
	 */
	public void stopAnimation()
	{this.animationFrame = 0;}
	
	/**
	 * Returns the internal state in 'playing' mode. 
	 * Allows for step by step advancement of the logical circuit.
	 * 
	 * @return {@link boolean} state
	 */
	public boolean getPlayState() {return this.playState;}

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