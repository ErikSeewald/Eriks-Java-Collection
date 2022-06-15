package PathfindGame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.Timer;

public class PathfindDemo extends PathfindPanel implements ActionListener
{
	private static final long serialVersionUID = 3986470365499168687L;
	
	static int GRID_SIZE = 30;
	int PANEL_SIZE = 610;
	int BOX_SIZE = (PANEL_SIZE /GRID_SIZE)+1;
	
	int[][] board = new int[GRID_SIZE][GRID_SIZE];
	
	int chasercount = 50;
	int[][] chaser = new int [2][chasercount];
	int[] randommove = new int [chasercount]; 
		
	int playerColumn = -1;
	int playerRow= -1;
	
	static Timer timer;
	
	int step = -1;
	
	boolean movestate = false;
	
	Random random = new Random();
	
	Color halfred = new Color(200,50,50);
	
	
	PathfindDemo()
	{
		this.setPreferredSize(new Dimension (PANEL_SIZE,PANEL_SIZE));
		
		ClickListener clickListener = new ClickListener();
		this.addMouseListener(clickListener);
		
		start(true);
		
		timer = new Timer(50, this);
	}
	
	public static void stop()
	{if (timer.isRunning()) {timer.stop();}}
	
	public void movePlayer(char x) {}
	
	public int[][] getBoard()
	{return board;}
	
	public int[][] getChaser()
	{return chaser;}
	
	public int[] getSizes()
	{int[] sizes = {chasercount, GRID_SIZE}; return sizes;}
	
	public int[] getPlayer()
	{int[] player = {playerColumn, playerRow, 0}; return player;}
	
	public void load(int[]sizes, int[][]loadboard, int[][]loadchaser, int[] loadplayer)
	{
		GRID_SIZE = sizes[1];
		chasercount = sizes[0];
		
		for (int i = 0; i < GRID_SIZE; i++)
			{
				for (int j = 0; j < GRID_SIZE; j++)
				{board[i][j] = loadboard[i][j];}
			}
		
		for (int i = 0; i < chasercount; i++)
		{
			chaser[0][i] = loadchaser[0][i];
			chaser[1][i] = loadchaser[1][i];
			randommove[i] = 0;
		}
		
		playerColumn = loadplayer[0];;
		playerRow = loadplayer[1];
		
		BOX_SIZE = (PANEL_SIZE /GRID_SIZE)+1;
		repaint();
	}
	
	public void start(boolean fullReset)
	{
		if (fullReset)
		{
			for (int i = 0; i < GRID_SIZE; i++)
			{
				for (int j = 0; j < GRID_SIZE; j++)
				{
					if (random.nextInt(8) == 1) {board[i][j] = 1;}
					else {board[i][j] = 0;}	
				}
			}
		}
		
		for (int i = 0; i < chasercount; i++)
		{
			chaser[0][i] = random.nextInt(GRID_SIZE);
			chaser[1][i] = random.nextInt(GRID_SIZE);
			randommove[i] = 0;
		}			
		
		repaint();
	}
	
	private class ClickListener extends MouseAdapter
	{
	    public void mousePressed(MouseEvent e) 
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
	    		if (!timer.isRunning()) {timer.start();}
	    		
	    		playerColumn = column;
	    		playerRow = row;
	    	}
	    	
	    	repaint();
	    }
	}
	
	public void changeSize(int c) 
	{
		PANEL_SIZE+=c; BOX_SIZE = (PANEL_SIZE /GRID_SIZE)+1; 
		repaint(); 
		this.setPreferredSize(new Dimension(PANEL_SIZE,PANEL_SIZE));
	}
	
	public void paint(Graphics g)
	{
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
		
		g2D.setPaint(halfred);
		g2D.fillRect(playerColumn * BOX_SIZE, playerRow * BOX_SIZE, BOX_SIZE, BOX_SIZE);
		
		g2D.setPaint(new Color (0, 140, 250));
		for (int i = 0; i < chasercount; i++)
		{g2D.fillRect(chaser[0][i] * BOX_SIZE, chaser[1][i] * BOX_SIZE, BOX_SIZE, BOX_SIZE);}
	}
	
	public void nextStep(int chasernum)
	{
		if (chaser[0][chasernum] == playerColumn && chaser[1][chasernum] == playerRow)
		{return;}
		
		if (randommove[chasernum] == 0)
		{
			if (random.nextBoolean())
			{	
				if (directionMove(0,chasernum)) {return;}	
				if (directionMove(1,chasernum)) {return;}	
			}
			
			else 
			{
				if (directionMove(1,chasernum)) {return;}	
				if (directionMove(0,chasernum)) {return;}	
			}
		}
		
		randommove[chasernum]++;
		
		if (randommove[chasernum] < 3)
		{
			switch (random.nextInt(4))
			{
				case 0: if (isValidMove(chaser[0][chasernum]+1, chaser[1][chasernum]))  {chaser[0][chasernum]++; break;}
				case 1: if (isValidMove(chaser[0][chasernum]-1, chaser[1][chasernum])) {chaser[0][chasernum]--; break;}
				case 2: if (isValidMove(chaser[0][chasernum], chaser[1][chasernum]+1))  {chaser[1][chasernum]++; break;}
				case 3: if (isValidMove(chaser[0][chasernum], chaser[1][chasernum]-1)) {chaser[1][chasernum]--; break;}
			}
		}
	
		else {randommove[chasernum] = 0;}
	}
	
	public boolean isValidMove(int column, int row)
	{
		if (column > -1 && column < GRID_SIZE &&  row > -1 && row < GRID_SIZE)
		{return (board[row][column] == 0);}
		return false;
	}

	public boolean directionMove(int direction, int chasernum)
	{
		int[] rowcol = {playerColumn, playerRow};
		
		if (rowcol[direction] > chaser[direction][chasernum])
		{
			if (board[chaser[1][chasernum] + 1 + (direction-1)] [chaser[0][chasernum]+ Math.abs(direction-1)] == 0) 
			{chaser[direction][chasernum]++; return true;}
		}
		
		if (rowcol[direction] < chaser[direction][chasernum])
		{
			if (board[chaser[1][chasernum]- (1 + (direction-1))] [chaser[0][chasernum]- Math.abs(direction-1)] == 0) 
			{chaser[direction][chasernum]--; return true;}
		}
		
		return false;
	}
	
	public void oneMove()
	{	
		for (int i = 0; i < chasercount; i++)
		{nextStep(i);}
		
		repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{oneMove();}
	
	public void wipeBoard()
	{
		for (int i = 0; i < GRID_SIZE; i++)
		{
			for (int j = 0; j < GRID_SIZE; j++)
			{
				board[i][j] = 0;
			}
		}
		repaint();
	}
}
