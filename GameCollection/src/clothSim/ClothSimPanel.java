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
		
		FileHandler.loadString(FileHandler.example_layout, sim);
	}
	
	public void simulate()
	{sim.algorithm(); repaint();}
	
	public void switchIsRunning() {sim.isRunning = !sim.isRunning;}
	
	public void connect()
	{sim.connect(); repaint();}
	
	public void removeLastConnector()
	{sim.removeLastConnector(); repaint();}

	public void restart()
	{sim.restart(); repaint();}
	
	public void saveLayout()
	{FileHandler.saveLayout(sim);}
	
	public void loadLayout()
	{FileHandler.loadLayout(sim); repaint();}
	
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
		if (sim.points.size() < 1) {return;}
		
		for (Point point : sim.points)
		{
			g2D.setPaint(point.isLocked ? locked_col : point_col);
			g2D.fillRect((int) point.x-Point.size/2, (int) point.y-Point.size/2, Point.size, Point.size);	
		}
		
		//SELECTED POINTS
		g2D.setPaint(selected_col);
		if (sim.selectedPoint != null)
		{
			if (!sim.selectedPoint.isLocked)
			{
				g2D.fillRect
				((int) sim.selectedPoint.x-Point.size/2,
				(int) sim.selectedPoint.y-Point.size/2, Point.size, Point.size);
			}
		}
		
		if (sim.prevSelectedPoint != null)
		{
			if (!sim.prevSelectedPoint.isLocked)
			{
				g2D.fillRect
				((int) sim.prevSelectedPoint.x-Point.size/2,
				(int) sim.prevSelectedPoint.y-Point.size/2, Point.size, Point.size);
			}
		}
		
		//CONNECTORS
		g2D.setPaint(connector_col);
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		for (Connector connector : sim.connectors)
		{
			g2D.drawLine
			((int) connector.pointA.x, (int) connector.pointA.y, 
			(int) connector.pointB.x, (int) connector.pointB.y);
		}
	}
}