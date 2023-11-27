package musicalLogicGates.graphics;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import musicalLogicGates.circuit.CircuitManager;
import musicalLogicGates.gates.Gate;

public class MouseHandler 
{
	private CircuitPanel panel;
	private CircuitManager circuitManager;
	
	private Gate selectedGate;
	
	MouseHandler(CircuitPanel panel, CircuitManager circuitManager)
	{
		if (panel == null)
		{throw new IllegalArgumentException("panel cannot be null!");}
		this.panel = panel;
		
		if (circuitManager == null)
		{throw new IllegalArgumentException("circuitManager cannot be null!");}
		this.circuitManager = circuitManager;
	}
	

	public class ClickListener extends MouseAdapter
	{
		@Override
		public void mousePressed(MouseEvent e) 
		{
			int x = e.getX();
			int y = e.getY();
			
			for (Gate gate : circuitManager.getGates())
				{
					if (x > gate.x - 5 && x < gate.x + 60 
							&& y > gate.y - 5 && y < gate.y + 60 )
					{
						selectedGate = gate;
						return;
					}
				}
			selectedGate = null;
		}
	}

	public class DragListener extends MouseMotionAdapter
	{
		@Override
		public void mouseDragged(MouseEvent e) 
		{
			if (selectedGate == null) {return;}
			
			selectedGate.x = e.getX() - 15;
			selectedGate.y = e.getY() - 20;
			
			panel.updateGraphics();
		}
	}

	public class ReleaseListener extends MouseAdapter
	{
		@Override
		public void mouseReleased(MouseEvent e) 
		{
			selectedGate = null;
		}
	}
}
