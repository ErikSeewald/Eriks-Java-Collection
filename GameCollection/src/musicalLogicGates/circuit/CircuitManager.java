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

/**
 * Class to manage a logic gate circuit.
 */
public class CircuitManager 
{
	private List<Gate> gates;
	
	private boolean playing;
	
	/**
	 * Creates a new {@link CircuitManager}
	 */
	public CircuitManager() {gates = new ArrayList<>();}

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
			case AND : return new AND(100, 100);
			case NAND :return new NAND(100, 100);
			case OR : return new OR(100, 100);
			case NOR : return new NOR(100, 100);
			case XOR : return new XOR(100, 100);
			case XNOR : return new XNOR(100, 100);
			case NOT : return new NOT(100,100);
			case IN : return new IN(100,100);
			case OUT: return new OUT(100,100);
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
	
	
	public void updateOneStep()
	{
		if (!playing) {return;}
		
		boolean[] newStates = new boolean[gates.size()];
		for (int i = 0; i < gates.size(); i++)
		{
			Gate gate = gates.get(i);
			newStates[i] = gate.output(gate.getInput1().getPlayState(), gate.getInput2().getPlayState());
		}
		
		for (int i = 0; i < gates.size(); i++)
		{
			Gate gate = gates.get(i);
			
			gate.updatePlayState(newStates[i]);
		}
	}

	public void startPlaying() {playing = true;}
	
	public void stopPlaying()
	{
		playing = false;
		
		for (Gate gate : gates)
		{
			gate.stopAnimation();
			gate.resetPlayState();
		}
	}
	
	public boolean isPlaying() {return playing;}
}