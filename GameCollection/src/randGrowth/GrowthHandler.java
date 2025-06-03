package randGrowth;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;

public class GrowthHandler implements ActionListener
{
	int gPixelSize = 2;
	int gPAmountX = RandGrowthPanel.PANEL_WIDTH/gPixelSize;
	int gPAmountY = RandGrowthPanel.PANEL_HEIGHT/gPixelSize;
	boolean[][] gPixels = new boolean[gPAmountX][gPAmountY];
	
	public class PixelStates
	{
		public static final boolean alive = true;
		public static final boolean dead = false;
	}
	
	private int chanceToDie;
	private int chanceToRevive;
	private int simulationSpeed;
	
	private Random random;
	private Timer timer;
	private RandGrowthPanel panel;
	
	GrowthHandler(RandGrowthPanel panel)
	{
		this.panel = panel;
		random = new Random();
		timer = new Timer(simulationSpeed, this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{growthSim(); panel.repaint();}
	
	public void stop() {timer.stop();}
	
	private void growthSim()
	{
		for (int i = 0; i < gPAmountX; i++)
		{
			for (int j = 0; j < gPAmountY; j++)
			{
				if (gPixels[i][j] == PixelStates.dead) {continue;}
				
				if (random.nextInt(100) < chanceToDie) {gPixels[i][j] = PixelStates.dead;}

				if (random.nextInt(100) < chanceToRevive)
				{reviveRand(i,j);}
			}	 
		}
	}
	
	private void reviveRand(int x, int y)
	{
		switch (random.nextInt(8))
		{
			case 0 : y++; break;
			case 1 : y--; break;
			case 2 : x++; break;
			case 3 : y--; break;
			case 4 : x++; y++; break;
			case 5 : x++; y--; break;
			case 6 : x--; y++; break;
			case 7 : x--; y--; break;
		}
		
		if (x < 0 || y < 0 || x >= gPAmountX || y >= gPAmountY) {return;}
		gPixels[x][y] = PixelStates.alive;
	}
	public void reset(int chanceToDie, int chanceToRevive, int pixelSize, int simSpeed)
	{
		timer.stop(); timer = null;
		this.chanceToDie = chanceToDie;
		this.chanceToRevive = chanceToRevive;
		this.gPixelSize = pixelSize;
		this.simulationSpeed = simSpeed > 0 ? 1000/simSpeed : 1000000;
		
		if (gPixelSize < 1) {gPixelSize = 1;}
		else if (gPixelSize > 100) {gPixelSize = 100;}
		
		gPAmountX = RandGrowthPanel.PANEL_WIDTH/gPixelSize;
		gPAmountY = RandGrowthPanel.PANEL_HEIGHT/gPixelSize;
		
		gPixels = null; System.gc();
		gPixels = new boolean[gPAmountX][gPAmountY];
		
		for (int i = 0; i < gPAmountX; i++)
		{
			for (int j = 0; j < gPAmountY; j++)
			{
				gPixels[i][j] = PixelStates.dead;
			}	 
		}
		
		timer = new Timer(simulationSpeed, this);
		timer.start();
		
		panel.repaint();
	}
	
	public void clickPixel(int x, int y)
	{
		if (x >= RandGrowthPanel.PANEL_WIDTH || y >= RandGrowthPanel.PANEL_HEIGHT || x < 0 || y < 0) {return;}
    	
		int i = x / gPixelSize, j = y / gPixelSize;
    	gPixels[i][j] = PixelStates.alive;
	}
	
	public boolean isRunning() {return timer.isRunning();}
}