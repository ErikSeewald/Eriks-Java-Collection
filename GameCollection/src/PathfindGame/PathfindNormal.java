package PathfindGame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class PathfindNormal extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 3986470365499168687L;
	
	//SIZES
	private static int GRID_SIZE = 22;
	private int PANEL_SIZE = 610;
	private int BOX_SIZE = (PANEL_SIZE /GRID_SIZE)+1;
	
	//BOARD
	private int[][] board = new int[GRID_SIZE][GRID_SIZE];
	
	//CHASER
	private class Chaser
	{
		int x,y;
		int RM_count = 0; //how many times has the chaser needed to make a random move because something is blocking the direct path
		
		Chaser(int x, int y)
		{this.x = x; this.y = y;}
	}
	private int chaser_count = 10;
	private Chaser[] chasers = new Chaser[chaser_count];
	
	//PLAYER
	private class Player
	{
		int x,y;
		int move_count;
	}
	Player player = new Player();
	
	Timer timer;
	
	Random random = new Random();
	
	Color halfred = new Color(200,50,50);
	
	boolean moveDone = true; 	//is the chaser move done? -> Player move ready
	
	boolean finished = false;	//have the chasers caught the player?
	
	//numbers used to push the counter coordinates on the two axis depending on the digit count
	int counterpush1;
	double counterpush2;
	
	
	PathfindNormal()
	{	
		this.setPreferredSize(new Dimension (PANEL_SIZE,PANEL_SIZE));
		
		ClickListener clickListener = new ClickListener();
		this.addMouseListener(clickListener);
		
		timer = new Timer(100, this);
		
		start(true);
	}
	
	public int[][] getBoard()
	{return board;}
	
	public int[][] getChasers()
	{
		int[][] ch = new int[2][chaser_count];
		for (int i = 0; i < chaser_count; i++)
		{
			ch[0][i] = chasers[i].x;
			ch[1][i] = chasers[i].y;
		}
		return ch;
	}
	
	public int[] getSizes()
	{int[] sizes = {chaser_count, GRID_SIZE}; return sizes;}
	
	public int[] getPlayer()
	{int[] playerArr = {player.x, player.y, player.move_count}; return playerArr;}
	
	public void start(boolean fullReset)
	{
		if (fullReset)
		{
			for (int i = 0; i < GRID_SIZE; i++)
			{
				for (int j = 0; j < GRID_SIZE; j++)
				{
					if (random.nextInt(4) == 1) {board[i][j] = 1;}
					else {board[i][j] = 0;}	
				}
			}
		}
		
		//CHASERS
		chasers = null;
		System.gc();
		chasers = new Chaser[chaser_count];
		for (int i = 0; i < chaser_count; i++)
		{
			chasers[i] = new Chaser(random.nextInt(GRID_SIZE),random.nextInt(GRID_SIZE));
		}
		
		finished = false;
		
		//PLAYER
		player.x = random.nextInt(GRID_SIZE);
		player.y = random.nextInt(GRID_SIZE);
		player.move_count = 0;
		
		repaint();
	}
	
	public void load(int[]sizes, int[][]loadboard, int[][]loadchaser, int[] loadplayer)
	{
		GRID_SIZE = sizes[1];
		chaser_count = sizes[0];
		
		for (int i = 0; i < GRID_SIZE; i++)
			{
				for (int j = 0; j < GRID_SIZE; j++)
				{
					board[i][j] = loadboard[i][j];
				}
			}
		
		//CHASERS
		chasers = null;
		System.gc();
		chasers = new Chaser[chaser_count];
		for (int i = 0; i < chaser_count; i++)
		{
			chasers[i] = new Chaser(loadchaser[0][i], loadchaser[1][i]);
		}
		
		finished = false;
		
		//PLAYER
		player.x = loadplayer[0];;
		player.y = loadplayer[1];
		player.move_count = loadplayer[2];
		
		BOX_SIZE = (PANEL_SIZE /GRID_SIZE)+1;
		repaint();
	}
	
	private class ClickListener extends MouseAdapter
	{
	    public void mousePressed(MouseEvent e) 
	    {  
	    	if (!finished)
	    	{
	    		int column = e.getX() / BOX_SIZE;
	    		int row = e.getY() / BOX_SIZE;
	    	
	    		if (e.isShiftDown())
	    		{
	    			if (board[row][column] == 0) {board[row][column] = 1;}
	    			else {board[row][column] = 0;}
	    		}
	    	
	    		else
	    		{
	    			player.x = column;
	    			player.y = row;
	    		}
	    	
	    		repaint();
	    	}
	    }
	}
		
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		timer.stop();
		oneMove();
	}
	
	public void oneMove()
	{	
		for (Chaser c : chasers)
		{nextStep(c);}
		
		repaint();
		
		moveDone = true;
	}
	
	public void nextStep(Chaser chaser)
	{	
		
		if (chaser.RM_count == 0)
		{
			if (random.nextBoolean())
			{	
				if (xMove(chaser)) {return;}	
				if (yMove(chaser)) {return;}	
			}
			
			else 
			{
				if (yMove(chaser)) {return;}	
				if (xMove(chaser)) {return;}	
			}
		}
		
		chaser.RM_count++;
		
		if (chaser.RM_count < 3)
		{
			switch (random.nextInt(4))
			{
				case 0: if (isValidMove(chaser.x+1, chaser.y))  {chaser.x++; break;}
				case 1: if (isValidMove(chaser.x-1, chaser.y)) {chaser.x--; break;}
				case 2: if (isValidMove(chaser.x, chaser.y+1))  {chaser.y++; break;}
				case 3: if (isValidMove(chaser.x, chaser.y-1)) {chaser.y--; break;}
			}
		}
	
		else {chaser.RM_count = 0;}
	}
	
	public boolean isValidMove(int column, int row)
	{
		if (column > -1 && column < GRID_SIZE &&  row > -1 && row < GRID_SIZE)
		{return (board[row][column] == 0);}
		return false;
	}
	
	public boolean xMove(Chaser chaser)
	{
		if (player.x > chaser.x)
		{
			if (board[chaser.y] [chaser.x + 1] == 0) 
			{chaser.x++; return true;}
		}
		
		else if (player.x < chaser.x)
		{
			if (board[chaser.y] [chaser.x - 1] == 0) 
			{chaser.x--; return true;}
		}
		return false;
	}
	
	public boolean yMove(Chaser chaser)
	{		
		if (player.y > chaser.y)
		{
			if (board[chaser.y + 1] [chaser.x] == 0) 
			{chaser.y++; return true;}
		}
		
		else if (player.y < chaser.y)
		{
			if (board[chaser.y - 1] [chaser.x] == 0) 
			{chaser.y--; return true;}
		}
		return false;
	}
	
	
	public void wipeBoard()
	{
		if (!finished)
		{
			for (int i = 0; i < GRID_SIZE; i++)
			{
				for (int j = 0; j < GRID_SIZE; j++)
				{board[i][j] = 0;}
			}
		
			repaint();
		}
	}
	
	public void movePlayer(char key)
	{
		if (moveDone && !finished)
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
		
			repaint();

			timer.start(); 
			moveDone = false;
		}
	}
	
	public void paint(Graphics g)
	{
		for (Chaser chaser : chasers)
		{
			if (chaser.x == player.x && chaser.y == player.y) 
			{finished = true;}
		}
		
		Graphics2D g2D = (Graphics2D) g;
		super.paint(g2D);
		
		g2D.setPaint(Color.DARK_GRAY);
		
		for (int row = 0; row < GRID_SIZE; row++)
		{
			for (int column = 0; column < GRID_SIZE; column++)
			{	
				if (board[row][column] != 0) 
				{g2D.fillRect(column * BOX_SIZE, row * BOX_SIZE, BOX_SIZE, BOX_SIZE);}
				
				g2D.drawRect(column * BOX_SIZE, row * BOX_SIZE, BOX_SIZE, BOX_SIZE); //Grid	
			}
		}
		
		g2D.setPaint(new Color (0, 140, 250));
		g2D.fillRect(player.x * BOX_SIZE, player.y * BOX_SIZE, BOX_SIZE, BOX_SIZE);
		
		if (!finished) {g2D.setPaint(halfred.brighter());}
		else {g2D.setPaint(new Color (50, 250, 50));}
		
		for (Chaser chaser : chasers)
		{g2D.fillRect(chaser.x * BOX_SIZE, chaser.y * BOX_SIZE, BOX_SIZE, BOX_SIZE);}
		
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		if (!finished)
		{
			g2D.setPaint(Color.DARK_GRAY);
			g2D.setFont(new Font (null, Font.PLAIN, PANEL_SIZE/40));
			if (player.move_count >= 100) {counterpush1 = 350;}
			else if (player.move_count >= 10) {counterpush1 = 110;}
			else {counterpush1 = 65;}
			g2D.drawString
			(""+player.move_count, player.x * BOX_SIZE + (PANEL_SIZE/counterpush1), player.y * BOX_SIZE + (PANEL_SIZE/30));
		}
		
		else 
		{
			g2D.setPaint(halfred);
			g2D.setFont(new Font (null, Font.PLAIN, PANEL_SIZE/2));
			if (player.move_count >= 100) {counterpush2 = 11.6;}
			else if (player.move_count >= 10) {counterpush2 = 4.7;}
			else {counterpush2 = 2.7;}
			g2D.drawString
			(""+player.move_count, (int)(GRID_SIZE/counterpush2 * BOX_SIZE), (int)(GRID_SIZE/1.5 * BOX_SIZE));
		}
		
		
	}
	
	public void changeSize(int c) 
	{
		PANEL_SIZE+=c; BOX_SIZE = (PANEL_SIZE /GRID_SIZE)+1; 
		repaint(); 
		this.setPreferredSize(new Dimension(PANEL_SIZE,PANEL_SIZE));
	}
}