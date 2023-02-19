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
			
			if (e.isControlDown()) {sim.lockPoint();}
		    		
		    if (sim.selectedPoint == -1)
		    {sim.addPoint(e.getX(), e.getY());}	
		    
	    	panel.repaint();	
	    }
	}
	
	public class DragListener extends MouseMotionAdapter
	{
	    public void mouseDragged(MouseEvent e) 
	    {
	    	if (e.isShiftDown())
	    	{sim.setSelectedPoint(e.getX(), e.getY());}
	    	
    		if (sim.selectedPoint < 0 || sim.pointAmount < 1) {return;}
	    	
	    	if (!e.isShiftDown())
	    	{sim.moveSelectedPoint(e.getX(), e.getY());}
	    	else
	    	{sim.cutSelectedPoint();}
	    	
	    	panel.repaint();
	    }
	}
}