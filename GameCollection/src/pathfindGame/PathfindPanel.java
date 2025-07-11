package pathfindGame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.util.Random;

import javax.swing.JPanel;

import ejcMain.util.EJC_Util;

public class PathfindPanel extends JPanel
{
	private static final long serialVersionUID = 3986470365499168687L;
	
	//SIZES
	public static final int GRID_SIZE = 22;
	private int PANEL_SIZE = 638;
	private int BOX_SIZE = (PANEL_SIZE /GRID_SIZE);
	
	//CONTROL
	private Random random = new Random();

	private boolean finished = false;
	
	public static final class ControlBooleans
	{
		final static boolean fullReset = true;
		final static boolean restart = false;
		
		final static boolean isPlayer = true;
		final static boolean isChaser = false;
	}
	
	PathfindPanel()
	{	
		this.setPreferredSize(new Dimension (PANEL_SIZE,PANEL_SIZE));
		this.initialize(ControlBooleans.fullReset);
	}
	
	public void changeSize(int c) 
	{
		PANEL_SIZE+=c;
		BOX_SIZE = (PANEL_SIZE / GRID_SIZE);
		this.setPreferredSize(new Dimension(PANEL_SIZE,PANEL_SIZE));
		repaint();
	}
	
	public void createSeed()
	{
		random.setSeed(EJC_Util.createSeed());
		this.initialize(ControlBooleans.fullReset);
	}
	
	public void initialize(boolean fullReset)
	{	
		board = PathfindInitializer.initBoard(board, fullReset, random);
		chasers = PathfindInitializer.initChasers(chasers, chaser_count, player, board, random);
		PathfindInitializer.initPlayer(player, board, chasers, random);
		
		finished = false;
		repaint();
	}
	
	//---------------------------------------GAMEPLAY---------------------------------------
	
	private Square[][] board;
	
	private static final int chaser_count = 6;
	private Chaser[] chasers = new Chaser[chaser_count];

	private Player player = new Player();
		
	public void nextMove(int key)
	{
		if (finished) {return;}
		
		boolean move_worked = player.move(key);
		
		if (move_worked)
		{
			for (Chaser chaser : chasers)
			{chaser.nextStep();}
			
			finished = player.deathCheck(chasers);
		}
		
		repaint();
	}
	
	//---------------------------------------PAINT---------------------------------------
	
	//COLORS
	private static final Color background = new Color(45,45,55);
	private static final Color foreground1 = new Color(180,180,200);
	private static final Color foreground2 = new Color(120,120,140);
	private static final Color foreground3 = new Color(80,80,100);
	private static final Color playerCol = new Color (50,250,50);
	private static final Color chaserCol = new Color(255,80,80);
	
	public void paint(Graphics g)
	{	
		Toolkit.getDefaultToolkit().sync(); // Force flush (for X11)
		Graphics2D g2D = (Graphics2D) g;

		//BACKGROUND
		g2D.setPaint(background);
		g2D.fillRect(0, 0, PANEL_SIZE, PANEL_SIZE);
		
		//BOARD
		for (int x = 0; x < GRID_SIZE; x++)
		{
			for (int y = 0; y < GRID_SIZE; y++)
			{	
				if (board[x][y].isAlive) 
				{
					g2D.setPaint(foreground1);
					if (board[x][y].hp == 2) {g2D.setPaint(foreground2);}
					else if (board[x][y].hp == 1) {g2D.setPaint(foreground3);}
					
					g2D.fillRect(x * BOX_SIZE, y * BOX_SIZE, BOX_SIZE, BOX_SIZE);
				}
				
				g2D.setPaint(foreground3);
				g2D.drawRect(x * BOX_SIZE, y * BOX_SIZE, BOX_SIZE, BOX_SIZE);
			}
		}
		
		//PLAYER
		g2D.setPaint(playerCol);
		g2D.fillRect(player.x * BOX_SIZE, player.y * BOX_SIZE, BOX_SIZE, BOX_SIZE);
		
		//CHASERS
		g2D.setPaint(chaserCol);
		
		for (Chaser chaser : chasers)
		{g2D.fillRect(chaser.x * BOX_SIZE, chaser.y * BOX_SIZE, BOX_SIZE, BOX_SIZE);}
		
		//UI
		drawUI(g2D);
	}
	
	private void drawUI(Graphics2D g2D)
	{
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		if (finished)
		{
			double counterpush = 2.7;
			
			g2D.setPaint(new Color(200,50,50));
			g2D.setFont(new Font (null, Font.PLAIN, PANEL_SIZE/2));
			
			if (player.move_count >= 100) {counterpush = 11.6;}
			else if (player.move_count >= 10) {counterpush = 4.7;}
			
			g2D.drawString
			(""+player.move_count, (int)(GRID_SIZE/counterpush * BOX_SIZE), (int)(GRID_SIZE/1.5 * BOX_SIZE));
		}
		
		else 
		{	
			int counterpush = 65;
			
			g2D.setPaint(foreground3);
			g2D.setFont(new Font (null, Font.PLAIN, PANEL_SIZE/40));
			
			if (player.move_count >= 100) {counterpush = 350;}
			else if (player.move_count >= 10) {counterpush = 110;}
			else {counterpush = 65;}
			
			g2D.drawString
			(""+player.move_count, player.x * BOX_SIZE + (PANEL_SIZE/counterpush), player.y * BOX_SIZE + (PANEL_SIZE/30));
		}	
	}
}