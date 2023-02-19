package clothSim;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class ClothSimPanel extends JPanel
{
	private static final long serialVersionUID = -5643933341241043804L;
	
	private VerletSimulation sim;
	
	ClothSimPanel()
	{
		this.setPreferredSize(new Dimension(960,600));
		sim = new VerletSimulation(this);	
		
		MouseHandler mouseHandler = new MouseHandler(sim ,this);
		this.addMouseListener(mouseHandler.new ClickListener());
		this.addMouseMotionListener(mouseHandler.new DragListener());
	}
	
	public void simulation()
	{sim.simulation(); repaint();}
	
	public void connect(boolean repaint)
	{sim.connect(); if (repaint) {repaint();}}
	
	public void removeLastConnector()
	{sim.removeLastConnector(); repaint();}

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