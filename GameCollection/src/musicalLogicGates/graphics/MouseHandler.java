package musicalLogicGates.graphics;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import musicalLogicGates.circuit.CircuitManager;
import musicalLogicGates.gates.Gate;
import musicalLogicGates.gates.Gate.GateType;
import musicalLogicGates.gates.IN;

public class MouseHandler 
{
	private CircuitPanel panel;
	private CircuitManager circuitManager;
	
	private Gate selectedGate;
	private boolean isConnecting;
	
	private int mouseX;
	private int mouseY;
	
	MouseHandler(CircuitPanel panel, CircuitManager circuitManager)
	{
		if (panel == null)
		{throw new IllegalArgumentException("panel cannot be null!");}
		this.panel = panel;
		
		if (circuitManager == null)
		{throw new IllegalArgumentException("circuitManager cannot be null!");}
		this.circuitManager = circuitManager;
	}
	

	public int getMouseX()
	{return mouseX;}
	
	public int getMouseY()
	{return mouseY;}
	
	public boolean isConnecting() 
	{return isConnecting;}
	
	public Gate getSelectedGate()
	{return selectedGate;}


    public class ClickListener extends MouseAdapter
	{
		@Override
		public void mousePressed(MouseEvent e) 
		{		    
		    mouseX = e.getX();
			mouseY = e.getY();
			
			selectedGate = getGateAtCoordinates(mouseX, mouseY);
			
			if (selectedGate == null) {return;}
			
			if (e.isAltDown())
			{circuitManager.removeGate(selectedGate);}
			
			if (e.isControlDown() && selectedGate.getType().equals(GateType.IN))
			{((IN) selectedGate).switchState();}
			
			panel.updateGraphics();
		}
	}
    
    public Gate getGateAtCoordinates(int x, int y)
    {
		for (Gate gate : circuitManager.getGates())
		{
		    if (x > gate.x - 5 && x < gate.x + 60 
		            && y > gate.y - 5 && y < gate.y + 60 )
		    {return gate;}
		}
		return null;
    }

	public class DragListener extends MouseMotionAdapter
	{
		@Override
		public void mouseDragged(MouseEvent e) 
		{
		    mouseX = e.getX();
	        mouseY = e.getY();
		    
	        if (selectedGate == null) {return;}
	        
	        if (e.isShiftDown() && selectedGate.getType() != GateType.OUT) 
            {
                isConnecting = true;
            }
	        
	        else
	        {
	            selectedGate.x = mouseX - 15;
	            selectedGate.y = mouseY - 20;
	        }
	        
	        panel.updateGraphics();
		}
	}

	public class ReleaseListener extends MouseAdapter
	{
		@Override
		public void mouseReleased(MouseEvent e) 
		{
			isConnecting = false;
			
			Gate endGate = getGateAtCoordinates(e.getX(), e.getY());
			if (endGate != null && selectedGate != null && endGate != selectedGate)
			{
			    if (e.getY() - endGate.y < 25)
			    {endGate.setInput1(selectedGate);}
			    
			    else
			    {endGate.setInput2(selectedGate);}
			}
			panel.updateGraphics();
		}
	}
}
