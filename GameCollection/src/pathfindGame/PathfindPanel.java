package pathfindGame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Random;

import javax.swing.JPanel;

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
	
	public void setSeed(int seed)
	{
		random.setSeed(seed);
		this.initialize(ControlBooleans.fullReset);
	}
	
	//---------------------------------------GAMEPLAY---------------------------------------
	
	private Square[][] board;
	
	private static final int chaser_count = 6;
	private Chaser[] chasers = new Chaser[chaser_count];

	private Player player = new Player();
		
	public void movePlayer(char key)
	{
		if (finished) {return;}
			
		player.move(key);
			
		finished = player.deathCheck(chasers);
		if (!finished) 
		{chaserTurn();}

		repaint();
	}
	
	private void chaserTurn()
	{	
		for (Chaser chaser : chasers)
		{chaser.nextStep();}
			
		finished = player.deathCheck(chasers);
		repaint();
	}
	
	//---------------------------------------INITIALIZATION---------------------------------------
	
	public void initialize(boolean fullReset)
	{	
		initBoard(fullReset);
		initChasers();
		initPlayer();
		
		finished = false;
		repaint();
	}
	
	private void initBoard(boolean fullReset)
	{
		if (fullReset)
		{
			board = null; System.gc();
			board = new Square[GRID_SIZE][GRID_SIZE];
					
			for (int x = 0; x < GRID_SIZE; x++)
			{
				for (int y = 0; y < GRID_SIZE; y++)
				{	
					boolean isAlive = random.nextInt(4) == 1;
					board[x][y] = new Square(isAlive);
				}
			}
		}
				
		else 
		{
			for (int x = 0; x < GRID_SIZE; x++)
			{
				for (int y = 0; y < GRID_SIZE; y++)
				{	
					//only revive the squares that were alive from the start
					if (board[x][y].hp != 3) {board[x][y].hp = 3; board[x][y].isAlive = true;}
				}
			}
		}
	}
	
	private void initChasers()
	{
		chasers = null; System.gc();
		chasers = new Chaser[chaser_count];
		for (int i = 0; i < chaser_count; i++)
		{
			int[] pos = generateValidPos(ControlBooleans.isChaser);
			chasers[i] = new Chaser(pos[0],pos[1], random.nextBoolean(), player, board);
		}
	}
	
	private void initPlayer()
	{
		int[] pos = generateValidPos(ControlBooleans.isPlayer);
		player.x = pos[0];
		player.y = pos[1];
		player.move_count = 0;
		player.board = board;
	}
	
	private int[] generateValidPos(boolean isPlayer)
	{
		int[] pos = new int[2];
		
		boolean validPos = false;
		while(!validPos)
		{
			pos[0] = random.nextInt(GRID_SIZE);
			pos[1] = random.nextInt(GRID_SIZE);
			
			if (board[pos[0]][pos[1]].isAlive) {continue;} //generate new coordinates
			validPos = true;
			
			if (isPlayer)
			{
				for (Chaser chaser : chasers)
				{
					if (pos[0] == chaser.x && pos[1] == chaser.y)
					{validPos = false; break;}
				}
			}
		}
		
		return pos;
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