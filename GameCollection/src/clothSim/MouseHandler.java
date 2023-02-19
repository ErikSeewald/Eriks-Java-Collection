package clothSim;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class MouseHandler 
{
	private VerletSimulation sim;
	private ClothSimPanel panel;
	
	MouseHandler(VerletSimulation simulation, ClothSimPanel panel)
	{this.sim = simulation; this.panel = panel;}
	
	public class ClickListener extends MouseAdapter
	{
	    public void mousePressed(MouseEvent e) 
	    {	
	    	sim.setSelectedPoint(e.getX(), e.getY());
			
			if (e.isControlDown()) {sim.lockSelectedPoint();}
		    		
		    if (sim.selectedPoint == null)
		    {sim.addPoint(e.getX(), e.getY());}	
		    
		    if (!sim.isRunning) {panel.repaint();}	
	    }
	}
	
	public class DragListener extends MouseMotionAdapter
	{
	    public void mouseDragged(MouseEvent e) 
	    {
	    	if (e.isShiftDown())
	    	{sim.setSelectedPoint(e.getX(), e.getY());}
	    	
    		if (sim.selectedPoint == null) {return;}
	    	
	    	if (!e.isShiftDown())
	    	{sim.moveSelectedPoint(e.getX(), e.getY());}
	    	else
	    	{sim.cutSelectedPoint();}
	    	
	    	if (!sim.isRunning) {panel.repaint();}
	    }
	}
}