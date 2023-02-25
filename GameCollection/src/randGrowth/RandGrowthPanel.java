package randGrowth;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPanel;

import randGrowth.GrowthHandler.PixelStates;

public class RandGrowthPanel extends JPanel
{
	private static final long serialVersionUID = 1327414797331813387L;
	public static final int PANEL_WIDTH = 1200,  PANEL_HEIGHT = 780;
	
	private GrowthHandler growthHandler;
	
	RandGrowthPanel()
	{
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		this.addMouseListener(new ClickListener());
		this.addMouseMotionListener(new DragListener());
		
		growthHandler = new GrowthHandler(this);
	}
	
	public void stop() {growthHandler.stop();}
	
	public GrowthHandler getGrowthHandler() {return growthHandler;}
	
	//---------------------------------------MOUSE---------------------------------------
	
	private class ClickListener extends MouseAdapter
	{
	    public void mousePressed(MouseEvent e) 
	    {  
	    	if (!growthHandler.isRunning()) {return;}
	    	growthHandler.clickPixel(e.getX(), e.getY());
	    	repaint();
	    }
	}
	
	private class DragListener extends MouseMotionAdapter
	{
	    public void mouseDragged(MouseEvent e) 
	    {  
	    	if (!growthHandler.isRunning()) {return;}
	    	growthHandler.clickPixel(e.getX(), e.getY());
	    	repaint();
	    }
	}
	
	//---------------------------------------PAINT---------------------------------------
	
	private static final Color background_col = new Color(50,50,70);
	
	public void paint (Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		
		//BACKGROUND
		g2D.setPaint(background_col);
		g2D.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
		
		if (!growthHandler.isRunning()) {return;}
		
		//GPIXELS / GROWTH PIXELS
		g2D.setPaint(Color.white);
		int size = growthHandler.gPixelSize;
		for (int i = 0; i < growthHandler.gPAmountX; i++)
		{
			for (int j = 0; j < growthHandler.gPAmountY; j++)
			{
				if (growthHandler.gPixels[i][j] == PixelStates.dead) {continue;}	
				g2D.fillRect(i * size, j * size, size, size);	
			}	 
		}
	}
}