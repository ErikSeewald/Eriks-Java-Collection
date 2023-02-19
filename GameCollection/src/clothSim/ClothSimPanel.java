package clothSim;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPanel;
import clothSim.Simulation.Point;

public class ClothSimPanel extends JPanel
{
	private static final long serialVersionUID = -5643933341241043804L;
	
	private Simulation sim;
	
	private boolean mouseHeld = false;
	
	ClothSimPanel()
	{
		this.setPreferredSize(new Dimension(960,600));
		sim = new Simulation(this);	
		
		ClickListener clickListener = new ClickListener();
		DragListener dragListener = new DragListener();
		ReleaseListener releaseListener = new ReleaseListener();
		
		this.addMouseListener(clickListener);
		this.addMouseMotionListener(dragListener);
		this.addMouseListener(releaseListener);
	}
	
	private class ClickListener extends MouseAdapter
	{
	    public void mousePressed(MouseEvent e) 
	    {	
	    	sim.prevSelectedPoint = sim.selectedPoint;
	    	sim.selectedPoint = sim.getPointIndex(e.getX(), e.getY());
			
			if (e.isControlDown()) {lockPoint(); return;}
		    		
		    if (sim.selectedPoint == -1) //= NULL -> ADD POINT THERE
		    {
		    	sim.pointAmount++;
				    	
				int pIndex = sim.pointAmount-1;
				sim.points[pIndex] = sim.new Point(pIndex);
				    
				sim.points[pIndex].positionX = e.getX();
				sim.points[pIndex].positionY = e.getY();
						
				sim.points[pIndex].prevPositionX = sim.points[pIndex].positionX;
				sim.points[pIndex].prevPositionY = sim.points[pIndex].positionY;
		    }	
	    	repaint();	
	    }
	}
	
	private class DragListener extends MouseMotionAdapter
	{
	    public void mouseDragged(MouseEvent e) 
	    {
	    	if (!mouseHeld || e.isShiftDown()) //ONLY CHANGE THE SELECTED POINT WHEN CUTTING OR WHEN JUST STARTING TO PRESS
	    	{sim.selectedPoint = sim.getPointIndex(e.getX(), e.getY());}
	    	
    		if (sim.selectedPoint == -1 || sim.pointAmount < 1) {return;}
	    	
	    	if (!e.isShiftDown()) //MOVE HELD POINT
	    	{
	    		sim.points[sim.selectedPoint].positionX = e.getX();
	    		sim.points[sim.selectedPoint].positionY = e.getY();
	    	}
	    	
	    	else //"CUT" POINT BY MOVING IT OFF SCREEN AND KILLING IT'S CONNECTOR
	    	{
	    		sim.points[sim.selectedPoint].positionX = -10;
	    		sim.points[sim.selectedPoint].positionY = -10;
	    		
	    		for (int i = 0; i < sim.connectorAmount; i++)
	    		{
	    			if (sim.connectors[i].pointA == sim.points[sim.selectedPoint]) {sim.connectors[i].isAlive = false;}
	    			else if (sim.connectors[i].pointB == sim.points[sim.selectedPoint]) {sim.connectors[i].isAlive = false;}
	    		}
	    	}
	    	
	    	mouseHeld = true;
	    	repaint();
	    }
	}
	
	public void simulation()
	{sim.simulation(); repaint();}
	
	public void connect(boolean repaint)
	{sim.connect(); if (repaint) {repaint();}}
	
	public void removeLastConnector()
	{sim.removeLastConnector(); repaint();}
	
	public void lockPoint()
	{sim.lockPoint(); repaint();}
	
	private class ReleaseListener extends MouseAdapter	
	{
	    public void mouseReleased(MouseEvent e) 
	    {mouseHeld = false;}
	}

	public void restart()
	{sim.restart(); repaint();}
	
	//---------------------------------------PAINT---------------------------------------
	
	private static final Color backround_col = new Color(50,50,60);
	private static final Color point_col = new Color(230,230,235);
	private static final Color locked_col = new Color(255,150,150);
	private static final Color selected_col = new Color(150,255,150);
	private static final Color connector_col = new Color(230,230,235);
			
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		
		//BACKGROUND
		g2D.setPaint(backround_col);
		g2D.fillRect(0, 0, this.getWidth(), this.getHeight());
	
		//POINTS
		if (sim.pointAmount < 1) {return;}
		int pointSize = sim.pointSize, selectedPoint = sim.selectedPoint, prevSelectedPoint = sim.prevSelectedPoint;
		for (int i = 0; i < sim.pointAmount; i++)
		{
			g2D.setPaint(sim.points[i].isLocked ? locked_col : point_col);
			g2D.fillRect((int)sim.points[i].positionX-pointSize/2, (int)sim.points[i].positionY-pointSize/2, pointSize, pointSize);	
		}
		
		//SELECTED POINTS
		g2D.setPaint(selected_col);
		if (selectedPoint >= 0)
		{
			if (!sim.points[selectedPoint].isLocked)
			{
				g2D.fillRect
				((int)sim.points[selectedPoint].positionX-pointSize/2,
				(int)sim.points[selectedPoint].positionY-pointSize/2, pointSize, pointSize);
			}
		}
		
		if (prevSelectedPoint >= 0)
		{
			if (!sim.points[prevSelectedPoint].isLocked)
			{
				g2D.fillRect
				((int)sim.points[prevSelectedPoint].positionX-pointSize/2,
				(int)sim.points[prevSelectedPoint].positionY-pointSize/2, pointSize, pointSize);
			}
		}
		
		//CONNECTORS
		g2D.setPaint(connector_col);
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		for (int i = 0; i < sim.connectorAmount; i++)
		{
			if (sim.connectors[i].isAlive)
			{
				g2D.drawLine
				((int) sim.connectors[i].pointA.positionX, (int) sim.connectors[i].pointA.positionY, 
				(int) sim.connectors[i].pointB.positionX, (int) sim.connectors[i].pointB.positionY);
			}
		}
	}
}