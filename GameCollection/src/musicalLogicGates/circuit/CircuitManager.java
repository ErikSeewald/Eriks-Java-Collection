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
import musicalLogicGates.gates.OR;
import musicalLogicGates.gates.OUT;
import musicalLogicGates.gates.XNOR;
import musicalLogicGates.gates.XOR;

public class CircuitManager 
{
	private ArrayList<Gate> gates;
	
	public CircuitManager() 
	{
		gates = new ArrayList<>();
	}

	public List<Gate> getGates() 
	{
		return gates;
	}
	
	public void addGate(GateType type)
	{
		if (type == null)
		{
			throw new IllegalArgumentException("Cannot create Gate of type null!");
		}
		
		switch (type)
		{
			case AND : gates.add(new AND(100, 100)); break;
			case NAND : gates.add(new NAND(100, 100)); break;
			case OR : gates.add(new OR(100, 100)); break;
			case NOR : gates.add(new NOR(100, 100)); break;
			case XOR : gates.add(new XOR(100, 100)); break;
			case XNOR : gates.add(new XNOR(100, 100)); break;
			case NOT : gates.add(new NOT(100,100)); break;
			case IN : gates.add(new IN(100,100)); break;
			case OUT: gates.add(new OUT(100,100)); break;
			case NULL_GATE : break;
			default : break;
		}
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