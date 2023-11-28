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

public class CircuitManager 
{
	private List<Gate> gates;
	
	public CircuitManager() 
	{
		gates = new ArrayList<>();
	}

	public List<Gate> getGates() 
	{
		return gates;
	}
	
	public void loadCircuit(List<Gate> gates)
	{
		if (gates == null)
		{throw new IllegalArgumentException("gates cannot be null!");}
		
		this.gates = new ArrayList<>(gates);
	}
	
	public void addGate(GateType type)
	{
		if (type == null)
		{
			throw new IllegalArgumentException("Cannot create Gate of type null!");
		}
		Gate newGate = createGate(type);
		if (newGate.getType() != GateType.NULL_GATE)
		{gates.add(newGate);}
	}
	
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
	
	public boolean removeGate(Gate gate)
	{
		for (Gate g : gates)
		{
			g.removeGateFromInputs(gate);
		}
		return gates.remove(gate);
	}
	
	public boolean clearGates()
	{
		return gates.removeAll(gates);
	}
}