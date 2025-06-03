package parallelUniverses;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class PU_Panel extends JPanel
{
	private static final long serialVersionUID = 9082922097976866954L;	
	private static final int PANEL_SIZE = 623;
	
	private boolean universesVisible = true;
	private Player player = new Player();
	
	PU_Panel()
	{this.setPreferredSize(new Dimension (PANEL_SIZE,PANEL_SIZE));}

	public void hideUniverses()
	{
		universesVisible = !universesVisible;
		repaint();
	}
	
	public void movePlayer(int x, int y, int moveCount)
	{
		for (int i = 0; i < moveCount; i++) 
		{player.move(x, y);} 
		repaint();
	}
	
	//---------------------------------------PAINT---------------------------------------
	
	private static final Color background_col = new Color(60,60,70);
	private static final Color outline_col = new Color(180,180,190);
	private static final Color core_col = new Color (50,170,250);
	private static final Color player_col = new Color (50,250,140);
	private static final Color rel_player_col = new Color (255,80,80);
	
	// 64 BITS FOR THE RELATIVE LOCATION
	//--> LOWER BOUND = -32 , UPPER BOUND = 31
	//OFFSET +3 BECAUSE OF PLAYERSIZE - 1/2 BORDERSIZE(2) = 3
	static final int upperEdge = 34;
	static final int lowerEdge = -29;
			
	private static final int rect1Offset = 16;
	private static final int rect2Offset = 36;
	private static final int relativeConstant = 307;
	
	private static final int rect1Size = 25;
	private static final int rect2Size = 15;
	
	private static final int universesPerRow = 9;
	private static final int universeSize = 69;
	
	public void paint (Graphics g)
	{
		Toolkit.getDefaultToolkit().sync(); // Force flush (for X11)
		Graphics2D g2D = (Graphics2D) g;
		
		//BACKGROUND
		g2D.setPaint(background_col);
		g2D.fillRect(0, 0, PANEL_SIZE, PANEL_SIZE);
	
		//UNIVERSES
		g2D.setPaint(outline_col);
		g2D.setStroke(new BasicStroke(2));
		
		if (universesVisible)
		{
			for (int i = 0; i < universesPerRow; i++)
			{
				for (int j = 0; j < universesPerRow; j++)
				{
					if (i == 4 && j == 4)
					{continue;}
					
					int x = j * universeSize, y = i * universeSize; 
					
					g2D.drawRect(x + 1, y + 1, universeSize, universeSize);
					g2D.drawRect(rect1Offset + x, rect1Offset + y, rect1Size, rect1Size);
					g2D.drawRect(rect2Offset + x, rect2Offset + y, rect2Size, rect2Size);
				}
			}
		}
		
		//ORIGINAL UNIVERSE
		g2D.setPaint(core_col);
		g2D.drawRect(4*(universeSize) + 1, 4*(universeSize) + 1, universeSize, universeSize);
		g2D.drawRect(rect1Offset+(4*(universeSize)), rect1Offset+(4*(universeSize)), rect1Size, rect1Size);
		g2D.drawRect(rect2Offset+(4*(universeSize)), rect2Offset+(4*(universeSize)), rect2Size, rect2Size);
		
		
		//DRAW PLAYER LOCATION
		g2D.setPaint(player_col);
		g2D.fillRect(player.location[0], player.location[1], player.size, player.size);
		
		//DRAW RELATIVE PLAYER LOCATION
		g2D.setPaint(rel_player_col);
		g2D.fillRect(player.relativeLocation[0]+relativeConstant, player.relativeLocation[1]+relativeConstant, player.size, player.size);
	}
}