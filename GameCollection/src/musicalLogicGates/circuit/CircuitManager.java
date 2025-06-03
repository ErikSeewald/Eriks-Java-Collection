package musicalLogicGates.circuit;

import java.util.ArrayList;
import java.util.List;

import musicalLogicGates.gates.AND;
import musicalLogicGates.gates.Gate;
import musicalLogicGates.gates.Gate.GateType;
import musicalLogicGates.gates.IN;
import musicalLogicGates.gates.NAND;
import musicalLogicGates.gates.NOR;
import musicalLogicGates.gates.NOT;
import musicalLogicGates.gates.NullGate;
import musicalLogicGates.gates.OR;
import musicalLogicGates.gates.OUT;
import musicalLogicGates.gates.XNOR;
import musicalLogicGates.gates.XOR;
import musicalLogicGates.music.SoundManager;

/**
 * Class to manage a logic gate circuit.
 */
public class CircuitManager 
{
	private List<Gate> gates;
	
	private boolean playing; // true if in 'playing' mode
	
	private SoundManager soundManager;
	
	/**
	 * Creates a new {@link CircuitManager}
	 */
	public CircuitManager(SoundManager soundManager) 
	{
		if (soundManager == null)
		{throw new NullPointerException("soundManager cannot be null!");}
		this.soundManager = soundManager;
		
		gates = new ArrayList<>();
	}

	/**
	 * Returns a list of all gates in the circuit.
	 * 
	 * @return {@code List<Gate>} gates
	 */
	public List<Gate> getGates() {return gates;}
	
	/**
	 * Loads the given list of {@link Gate}s into the circuit.
	 * 
	 * @param gates the {@link Gate}s to load
	 * @throws IllegalArgumentException if {@code} gates is {@literal null}
	 */
	public void loadCircuit(List<Gate> gates)
	{
		if (gates == null)
		{throw new IllegalArgumentException("gates cannot be null!");}
		
		this.gates = new ArrayList<>(gates);
	}
	
	/**
	 * Adds a new {@link Gate} of the given {@link GateType} to the circuit.
	 * 
	 * @param type the {@link GateType} to add
	 * @throws IllegalArgumentException if {@code type} is {@literal null}
	 */
	public void addGate(GateType type)
	{	
		if (type == null)
		{throw new IllegalArgumentException("Cannot create Gate of type null!");}
		
		Gate newGate = createGate(type);
		
		if (newGate.getType() != GateType.NULL_GATE)
		{gates.add(newGate);}
	}
	
	/**
	 * Creates a new {@link Gate} object with the given {@link GateType}.
	 * 
	 * @param type the {@link GateType} to create
	 * @return new {@link Gate}
	 */
	public static Gate createGate(GateType type)
	{
		switch (type)
		{
			case AND : return new AND(100, 60);
			case NAND :return new NAND(180, 60);
			case OR : return new OR(260, 60);
			case NOR : return new NOR(340, 60);
			case XOR : return new XOR(420, 60);
			case XNOR : return new XNOR(500, 60);
			case NOT : return new NOT(580, 60);
			case IN : return new IN(30, 60);
			case OUT: return new OUT(660, 60);
			case NULL_GATE : break;
			default : break;
		}
		return NullGate.instance;
	}
	
	/**
	 * Removes the given {@link Gate} from the circuit if it is present.
	 * Handles removing any connections to the given {@link Gate} as well.
	 * 
	 * @param gate the {@link Gate} to remove.
	 * @return {@link boolean} successful removal
	 */
	public boolean removeGate(Gate gate)
	{
		for (Gate g : gates)
		{
			g.removeGateFromInputs(gate);
		}
		return gates.remove(gate);
	}
	
	/**
	 * Removes all {@link Gate}s from the circuit.
	 */
	public void clearGates() {gates.removeAll(gates);}
	
	/**
	 * Returns whether the 'input tree' of the {@link Gate} 'findIn' (all inputs of 'findIn',
	 * all of the inputs' inputs and so on) contains the {@link Gate} 'find'.
	 * Useful to prevent adding an {@link Gate} connection that would cause a logic cycle.
	 * 
	 * @param find the {@link Gate} to be searched for in the input tree
	 * @param findIn the {@link Gate} to be searched through
	 * @return {@link boolean} whether the input tree contains 'find'
	 */
	public boolean inputTreeContains(Gate find, Gate findIn)
	{
		if (findIn.getType().equals(GateType.NULL_GATE)) {return false;}
		
		boolean input1Contains = findIn.getInput1().equals(find);
		if (!input1Contains)
		{input1Contains = inputTreeContains(find, findIn.getInput1());}
		
		boolean input2Contains = findIn.getInput2().equals(find);
		if (!input1Contains && !input2Contains)
		{input2Contains = inputTreeContains(find, findIn.getInput2());}
		
		return input1Contains || input2Contains;
	}
	
	//'PLAYING' MODE
	/**
	 * Updates the 'playing' mode states by one step.
	 */
	public void updateOneStep()
	{
		if (!playing) {return;}
		
		boolean[] newStates = new boolean[gates.size()];
		for (int i = 0; i < gates.size(); i++)
		{
			Gate gate = gates.get(i);
			newStates[i] = gate.output(gate.getInput1().getPlayState(), gate.getInput2().getPlayState());
		}
		
		//UPDATE STATE IN SECOND LOOP SO THE FIRST LOOP ONLY USES OLD STATES AS INPUTS
		for (int i = 0; i < gates.size(); i++)
		{
			Gate gate = gates.get(i);
			
			//SOUND
			if (newStates[i] != gate.getPlayState()) //state changed
			{soundManager.playGateSound(gate.getType());}
			
			gate.updatePlayState(newStates[i]);
		}
	}

	/**
	 * Starts the 'playing' mode
	 */
	public void startPlaying() 
	{
		playing = true;
		
		for (Gate gate : gates)
		{gate.resetPlayState();} //NOT REDUNDANT
	}
	
	
	/**
	 * Stops the 'playing' mode
	 */
	public void stopPlaying()
	{
		playing = false;
		
		for (Gate gate : gates)
		{
			gate.stopAnimation();
			gate.resetPlayState();
		}
	}
	
	/**
	 * Returns whether the {@link CircuitManager} is in 'playing' mode.
	 * 
	 * @return {@link boolean} whether the {@link CircuitManager} is in 'playing' mode
	 */
	public boolean isPlaying() {return playing;}
}