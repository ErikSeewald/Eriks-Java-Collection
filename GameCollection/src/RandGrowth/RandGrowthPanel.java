package RandGrowth;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class RandGrowthPanel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1327414797331813387L;
	
	static final int PANEL_WIDTH = 1200;
	static final int PANEL_HEIGHT = 780;
	
	private int gPixelSize = 2;
	private int gPAmountX = PANEL_WIDTH/gPixelSize;
	private int gPAmountY = PANEL_HEIGHT/gPixelSize;
	private GPixel[][] gPixels = new GPixel[gPAmountX][gPAmountY];
	
	private int chanceToDie;
	private int chanceToRevive;
	private int simulationSpeed;
	
	boolean hasStarted;
	
	Timer timer;
	
	RandGrowthPanel()
	{
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		
		ClickListener clickListener = new ClickListener();
		this.addMouseListener(clickListener);
		DragListener dragListener = new DragListener();
		this.addMouseMotionListener(dragListener);
		
		timer = new Timer(simulationSpeed, this);
	}
	
	private void growthSim()
	{
		Random random = new Random();
		
		for (int i = 0; i < gPAmountX; i++)
		{
			for (int j = 0; j < gPAmountY; j++)
			{
				if (gPixels[i][j].isAlive)
				{
					if (random.nextInt(100) < chanceToDie)
					{gPixels[i][j].isAlive = false;}
					
					if (random.nextInt(100) < chanceToRevive)
					{
						switch (random.nextInt(8))
						{
							case 0 : if (j+1 < gPAmountY) {gPixels[i][j+1].isAlive = true;}
							break;
							case 1 : if (j-1 >= 0) {gPixels[i][j-1].isAlive = true;}
							break;
							case 2 : if (i+1 < gPAmountX) {gPixels[i+1][j].isAlive = true;}
							break;
							case 3 : if (i-1 > 0) {gPixels[i-1][j].isAlive = true;}
							break;
							case 4 : if (i+1 < gPAmountX && j+1 < gPAmountY) {gPixels[i+1][j+1].isAlive = true;}
							break;
							case 5 : if (i+1 < gPAmountX && j-1 > 0) {gPixels[i+1][j-1].isAlive = true;}
							break;
							case 6 : if (i-1 > 0 && j+1 < gPAmountY) {gPixels[i-1][j+1].isAlive = true;}
							break;
							case 7 : if (i-1 > 0 && j-1 > 0) {gPixels[i-1][j-1].isAlive = true;}
						}
					}
				}
			}	 
		}
		repaint();
	}
	
	public void reset(int chanceToDie, int chanceToRevive, int pixelSize, int simulationSpeed)
	{
		timer.stop();
		this.chanceToDie = chanceToDie;
		this.chanceToRevive = chanceToRevive;
		this.gPixelSize = pixelSize;
		this.simulationSpeed = simulationSpeed;
		
		if(gPixelSize < 1) {gPixelSize = 1;}
		else if (gPixelSize > 100) {gPixelSize = 100;}
		gPAmountX = PANEL_WIDTH/gPixelSize;
		gPAmountY = PANEL_HEIGHT/gPixelSize;
		gPixels = new GPixel[gPAmountX][gPAmountY];
		
		for (int i = 0; i < gPAmountX; i++)
		{
			for (int j = 0; j < gPAmountY; j++)
			{
				gPixels[i][j] = new GPixel();
				gPixels[i][j].locX = i*gPixelSize;
				gPixels[i][j].locY = j*gPixelSize;
			}	 
		}
		
		timer = new Timer(simulationSpeed, this);
		timer.start();
		
		System.gc();
		hasStarted = true;
		repaint();
	}
	
	private class GPixel
	{
		int locX;
		int locY;
		boolean isAlive = false;
	}

	public void paint (Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		
		//BACKGROUND
		g2D.setPaint(new Color (50,50,70));
		g2D.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
		
		//GPIXELS / GROWTH PIXELS
		if (timer.isRunning())
		{
			g2D.setPaint(Color.white);
			for (int i = 0; i < gPAmountX; i++)
			{
				for (int j = 0; j < gPAmountY; j++)
				{
					if (gPixels[i][j].isAlive)
					{
						g2D.fillRect(gPixels[i][j].locX, gPixels[i][j].locY, gPixelSize, gPixelSize);
					}
				}	 
			}
		}
	}
	
	private class ClickListener extends MouseAdapter
	{
	    public void mousePressed(MouseEvent e) 
	    {  
	    	if (!timer.isRunning()) {return;}
	    	
	    	int i = e.getX() / gPixelSize, j = e.getY() / gPixelSize;
	    	gPixels[i][j].isAlive = true;
	    	repaint();
	    }
	}
	
	private class DragListener extends MouseMotionAdapter
	{
	    public void mouseDragged(MouseEvent e) 
	    {  
	    	if (!timer.isRunning()) {return;}
	    	
	    	int i = e.getX() / gPixelSize, j = e.getY() / gPixelSize;
	    	if (i >= PANEL_WIDTH/gPixelSize || j >= PANEL_HEIGHT/gPixelSize || i < 0 || j < 0) {return;}
	    	gPixels[i][j].isAlive = true;
	    	repaint();
	    }
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{growthSim();}
}
