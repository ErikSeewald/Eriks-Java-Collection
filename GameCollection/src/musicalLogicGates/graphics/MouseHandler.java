package musicalLogicGates.graphics;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import musicalLogicGates.circuit.CircuitManager;
import musicalLogicGates.gates.Gate;
import musicalLogicGates.gates.Gate.GateType;
import musicalLogicGates.gates.IN;

/**
 * Class to handle all mouse interactions for {@link EJC_MusicalLogicGates}
 */
public class MouseHandler 
{
	//REFERENCES
	private CircuitPanel panel;
	private CircuitManager circuitManager;
	
	//GATE
	private Gate selectedGate;
	private boolean isConnecting;
	
	//COORDINATES
	private int mouseX;
	private int mouseY;
	
	/**
	 * Creates a new {@link MouseHandler} with the given {@link CircuitPanel} and {@link CircuitManager}.
	 * 
	 * @param panel the {@link CircuitPanel} for this {@link MouseHandler}
	 * @param circuitManager the {@link CircuitManager} for this {@link MouseHandler}
	 */
	MouseHandler(CircuitPanel panel, CircuitManager circuitManager)
	{
		if (panel == null)
		{throw new NullPointerException("panel cannot be null!");}
		this.panel = panel;
		
		if (circuitManager == null)
		{throw new NullPointerException("circuitManager cannot be null!");}
		this.circuitManager = circuitManager;
	}
	
	/**
	 * Returns the last saved x coordinate of the mouse.
	 * 
	 * @return the x coordinate of the mouse
	 */
	public int getMouseX() {return mouseX;}
	
	/**
	 * Returns the last saved y coordinate of the mouse.
	 * 
	 * @return the y coordinate of the mouse
	 */
	public int getMouseY() {return mouseY;}
	
	/**
	 * Returns whether or not the {@link MouseHandler} has entered connecting mode.
	 * 
	 * @return {@link boolean} isConnecting
	 */
	public boolean isConnecting() {return isConnecting;}
	
	/**
	 * Returns the currently selected {@link Gate}. Can be {@literal null}.
	 * 
	 * @return the currently selected {@link Gate}
	 */
	public Gate getSelectedGate() {return selectedGate;}

	/**
	 * Helper method to get the {@link Gate} at the given coordinates. Can return {@literal null} if no {@link Gate}
	 * is present at the coordinates.
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @return the {@link Gate} at the given coordinates
	 */
	private Gate getGateAtCoordinates(int x, int y)
    {
		for (Gate gate : circuitManager.getGates())
		{
		    if (x > gate.x - 5 && x < gate.x + 60 
		            && y > gate.y - 5 && y < gate.y + 60 )
		    {return gate;}
		}
		return null;
    }

	/**
	 * Class to handle click actions for {@link MouseHandler}.
	 */
    public class ClickListener extends MouseAdapter
	{
		@Override
		public void mousePressed(MouseEvent e) 
		{	
		    mouseX = e.getX();
			mouseY = e.getY();
			
			//SELECT
			selectedGate = getGateAtCoordinates(mouseX, mouseY);
			
			if (selectedGate == null) {return;}
			
			//DELETE
			if (e.isAltDown())
			{circuitManager.removeGate(selectedGate);}
			
			//SWITCH STATE
			if (e.isControlDown() && selectedGate.getType().equals(GateType.IN))
			{((IN) selectedGate).switchState();}
			
			panel.updateGraphics();
		}
	}

	/**
	 * Class to handle drag actions for {@link MouseHandler}.
	 */
	public class DragListener extends MouseMotionAdapter
	{
		@Override
		public void mouseDragged(MouseEvent e) 
		{
		    mouseX = e.getX();
	        mouseY = e.getY();
		    
	        if (selectedGate == null) {return;}
	        
	        //CONNECTING MODE
	        if (e.isShiftDown() && selectedGate.getType() != GateType.OUT) 
            {isConnecting = true; }
	        
	        //DRAGGING MODE
	        else
	        {
	            selectedGate.x = mouseX - 15;
	            selectedGate.y = mouseY - 20;
	        }
	        
	        //only updateGraphics while not playing or you risk messing up the frame rates
	        if (!circuitManager.isPlaying()) {panel.updateGraphics();}    
		}
	}

	/**
	 * Class to handle release actions for {@link MouseHandler}.
	 */
	public class ReleaseListener extends MouseAdapter
	{
		@Override
		public void mouseReleased(MouseEvent e) 
		{
			if (!isConnecting) {return;}
			isConnecting = false;
			
			//CONNECT
			Gate endGate = getGateAtCoordinates(e.getX(), e.getY());
			if (endGate != null && selectedGate != null)
			{
				//no circular logic
				if (endGate == selectedGate || circuitManager.inputTreeContains(endGate, selectedGate)) 
				{
					panel.updateGraphics();
					return;
				} 
				
				//UPPER HALF
			    if (e.getY() - endGate.y < CircuitPanel.OUT_POS_Y)
			    {endGate.setInput1(selectedGate);}
			    
			    //LOWER HALF
			    else
			    {endGate.setInput2(selectedGate);}
			}
			panel.updateGraphics();
			
		}
	}
}
