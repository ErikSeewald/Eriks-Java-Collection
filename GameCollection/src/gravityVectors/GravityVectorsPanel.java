package gravityVectors;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPanel;
import gravityVectors.Simulation.ArrowPoint;
import gravityVectors.Simulation.PullPoint;

public class GravityVectorsPanel extends JPanel
{
	private static final long serialVersionUID = 3933829351442493815L;
	
	private int PANEL_WIDTH = 900;
	private int PANEL_HEIGHT = (int) (PANEL_WIDTH*(0.66));
	
	private Simulation sim;
	
	GravityVectorsPanel()
	{
		this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
		this.addMouseListener(new ClickListener());
		this.addMouseMotionListener(new DragListener());
		
		sim = new Simulation();
		start();
	}
	
	public void start()
	{sim.initialize(); repaint();}
	
	public void switchArrowNormalization()
	{sim.switchArrowNormalization(); repaint();}
	 
	public void changeSize(int amount)
	{
		PANEL_WIDTH += amount;
		PANEL_HEIGHT = (int) (PANEL_WIDTH*(0.66));
		this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
		
		sim.increaseArrowCount();
		start();
	}
	
	public void changePPointCount(int amount)
	{sim.changePPOINT_COUNT(amount); repaint();}
	
	private class ClickListener extends MouseAdapter
	{
	    public void mousePressed(MouseEvent e)
	    {sim.selectGravityPoint(e.getX(), e.getY());} 
	}
	
	private class DragListener extends MouseMotionAdapter
	{
	    public void mouseDragged(MouseEvent e) 
	    {sim.moveGravityPoint(e.getX(), e.getY()); repaint();}  
	}
	
	//---------------------------------------PAINT---------------------------------------
	
	//COLORS
	private static final Color background_col = new Color(50,50,70);
	private static final Color arrow_col = new Color(180,180,200);
	private static final Color ppoint_col = new Color(255,80,80);
		
	//GRADIENT MODE
	private boolean gradientMode = false;
	private static final float[] gradientFractions = {0.0f, 1.0f};
	private static final Color[] gradientColors = {ppoint_col, Color.green};
	
	public void gradientMode() {gradientMode = !gradientMode; repaint();}
	
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		
		//BACKGROUND
		g2D.setPaint(background_col);
		g2D.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
		
		//ARROW POINTS
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2D.setPaint
		(	gradientMode ?
				new RadialGradientPaint(sim.selectedPoint.locX, sim.selectedPoint.locY, 500, gradientFractions, gradientColors)
				: 	
				arrow_col
		);
		
		for (ArrowPoint arrow : sim.arrowPoints)
		{
			//prevent trying to draw line when the mouse is exactly on the point
			if (arrow.arrowX == 0) 
			{continue;}

			g2D.drawLine(arrow.locX, arrow.locY, arrow.arrowX, arrow.arrowY);
			g2D.drawRect(arrow.arrowX-1, arrow.arrowY-1, 2, 2);
		}
		
		//PULL POINTS
		g2D.setPaint(ppoint_col);
		for (PullPoint point : sim.pullPoints)
		{g2D.fillOval(point.locX - PullPoint.size / 2, point.locY - PullPoint.size / 2, PullPoint.size, PullPoint.size);}
	}
}