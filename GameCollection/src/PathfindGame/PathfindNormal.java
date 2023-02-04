package PathfindGame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Random;

import javax.swing.JPanel;

public class PathfindNormal extends JPanel
{
	private static final long serialVersionUID = 3986470365499168687L;
	
	//SIZES
	private static int GRID_SIZE = 22;
	private int PANEL_SIZE = 610;
	private int BOX_SIZE = (PANEL_SIZE /GRID_SIZE)+1;
	
	//BOARD
	private class Square
	{
		int hp = 3;
		boolean isAlive;
		
		Square(boolean isAlive)
		{this.isAlive = isAlive;}
		
		public void getHit()
		{
			hp--;
			if (hp <= 0)
			{isAlive = false;}
		}
	}
	private Square[][] board;
	
	//CHASER
	private class Chaser
	{
		int x,y;
		boolean prefersX;
		
		Chaser(int x, int y, boolean prefersX)
		{this.x = x; this.y = y; this.prefersX = prefersX;}
	}
	private int chaser_count = 6;
	private Chaser[] chasers = new Chaser[chaser_count];
	
	//PLAYER
	private class Player
	{
		int x,y;
		int move_count;
	}
	Player player = new Player();
	
	//COLORS
	private final Color background = new Color(45,45,55);
	private final Color foreground1 = new Color(180,180,200);
	private final Color foreground2 = new Color(120,120,140);
	private final Color foreground3 = new Color(80,80,100);
	private final Color playerCol = new Color (50,250,50);
	private final Color chaserCol = new Color(255,80,80);
	
	//OTHERS
	private Random random = new Random();
	
	private boolean finished = false;
	private int seed = random.nextInt();
	
	PathfindNormal()
	{	
		this.setPreferredSize(new Dimension (PANEL_SIZE,PANEL_SIZE));
		reset();
	}
	
	public void changeSize(int c) 
	{
		PANEL_SIZE+=c; BOX_SIZE = (PANEL_SIZE /GRID_SIZE)+1;
		this.setPreferredSize(new Dimension(PANEL_SIZE,PANEL_SIZE));
		repaint(); 
	}
	
	public void setSeed(int seed)
	{this.seed = seed;}
	
	public void restart()
	{initialize(false);}
	
	public void reset()
	{initialize(true);}
	
	private void initialize(boolean fullReset)
	{
		random.setSeed(seed);
		seed+=10;
		
		//BOARD
		if (fullReset)
		{
			board = null;
			System.gc();
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
		
		//CHASERS
		chasers = null;
		System.gc();
		chasers = new Chaser[chaser_count];
		for (int i = 0; i < chaser_count; i++)
		{
			boolean validPos = false;
			while(!validPos)
			{
				int x = random.nextInt(GRID_SIZE);
				int y = random.nextInt(GRID_SIZE);
				
				validPos = !board[x][y].isAlive;
				
				if (validPos)
				{chasers[i] = new Chaser(x,y, random.nextBoolean());}
			}
		}
		
		
		//PLAYER
		boolean validPos = false;
		while(!validPos)
		{
			player.x = random.nextInt(GRID_SIZE);
			player.y = random.nextInt(GRID_SIZE);	
			
			if (board[player.x][player.y].isAlive) {continue;} //generate new coordinates
			
			validPos = true;
			for (Chaser chaser : chasers)
			{
				if (player.x == chaser.x && player.y == chaser.y)
				{validPos = false; break;}
			}
		}
			
		player.move_count = 0;
		
		finished = false;
		repaint();
	}
	
	public void load(int[]sizes, boolean[][]loadboard, int[][]loadchaser, int[] loadplayer)
	{
		GRID_SIZE = sizes[1];
		BOX_SIZE = (PANEL_SIZE /GRID_SIZE)+1;
		chaser_count = sizes[0];
		
		//BOARD
		for (int x = 0; x < GRID_SIZE; x++)
		{
			for (int y = 0; y < GRID_SIZE; y++)
			{
				board[x][y].isAlive = loadboard[x][y];
			}
		}
		
		//CHASERS
		chasers = null;
		System.gc();
		chasers = new Chaser[chaser_count];
		for (int i = 0; i < chaser_count; i++)
		{
			chasers[i] = new Chaser(loadchaser[0][i], loadchaser[1][i], random.nextBoolean());
		}
		
		//PLAYER
		player.x = loadplayer[0];
		player.y = loadplayer[1];
		player.move_count = loadplayer[2];
		
		finished = false;
		repaint();
	}
	
	private void oneMove()
	{	
		for (Chaser c : chasers)
		{nextStep(c); c.prefersX = !c.prefersX;}
		
		deathCheck();
		repaint();
	}
	
	private void nextStep(Chaser chaser)
	{	
		//MOVE
		if (chaser.prefersX)
		{
			if (xMove(chaser)) {return;}	
			if (yMove(chaser)) {return;}
		}
			
		else
		{
			if (yMove(chaser)) {return;}	
			if (xMove(chaser)) {return;}
		}
		
		//NO VALID MOVE FOUND -> START ATTACKING SQUARE
		int attackX = chaser.x;
		int attackY = chaser.y;
		
		if (player.x > chaser.x)
		{attackX++;}
				
		else if (player.x < chaser.x)
		{attackX--;}
		
		else if (player.y > chaser.y)
		{attackY++;}
				
		else
		{attackY--;}
		
		board[attackX][attackY].getHit();
	}
	
	private boolean xMove(Chaser chaser)
	{
		if (player.x > chaser.x)
		{
			if (!board[chaser.x + 1] [chaser.y].isAlive) 
			{chaser.x++; return true;}
		}
		
		else if (player.x < chaser.x)
		{
			if (!board[chaser.x - 1] [chaser.y].isAlive) 
			{chaser.x--; return true;}
		}
		
		return false;
	}
	
	private boolean yMove(Chaser chaser)
	{		
		if (player.y > chaser.y)
		{
			if (!board[chaser.x] [chaser.y + 1].isAlive) 
			{chaser.y++; return true;}
		}
		
		else if (player.y < chaser.y)
		{
			if (!board[chaser.x] [chaser.y - 1].isAlive) 
			{chaser.y--; return true;}
		}
		return false;
	}
	
	public void movePlayer(char key)
	{
		if (!finished)
		{
			switch (key)
			{
				case 'a': if (isValidMove(player.x-1, player.y)) {player.x--;} else {return;}
				break;
				case 's': if (isValidMove(player.x, player.y+1)) {player.y++;} else {return;}
				break;
				case 'd': if (isValidMove(player.x+1, player.y)) {player.x++;} else {return;}
				break;
				case 'w': if (isValidMove(player.x, player.y-1)) {player.y--;} else {return;}
				break;
			}
			
			player.move_count++;
			deathCheck();
			
			if (!finished) 
			{
				oneMove(); 
			}
			repaint();
		}
	}
	
	public boolean isValidMove(int x, int y)
	{
		if (x > -1 && x < GRID_SIZE &&  y > -1 && y < GRID_SIZE)
		{return (!board[x][y].isAlive);}
		return false;
	}
	
	private void deathCheck()
	{
		for (Chaser chaser : chasers)
		{
			if (chaser.x == player.x && chaser.y == player.y) 
			{finished = true;}
		}
	}
	
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
				g2D.setPaint(foreground1);
				if (board[x][y].hp == 2) {g2D.setPaint(foreground2);}
				else if (board[x][y].hp == 1) {g2D.setPaint(foreground3);}
				
				if (board[x][y].isAlive) 
				{g2D.fillRect(x * BOX_SIZE, y * BOX_SIZE, BOX_SIZE, BOX_SIZE);}
				
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