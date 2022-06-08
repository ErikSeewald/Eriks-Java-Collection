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
import javax.swing.Timer;

public class PathfindNormal extends PathfindPanel implements ActionListener
{
	private static final long serialVersionUID = 3986470365499168687L;
	
	static int GRID_SIZE = 22;
	int PANEL_SIZE = 610;
	int BOX_SIZE = (PANEL_SIZE /GRID_SIZE)+1;
	
	int[][] board = new int[GRID_SIZE][GRID_SIZE];
	
	int chasercount = 10;
	int[][] chaser = new int[2][chasercount];
	
	int[] randommove = new int [chasercount]; 
	//how many times has the chaser needed to make a random move because something is blocking the direct path

	int playerColumn;
	int playerRow;
	
	int counter = 0;	//how many player moves
	
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
	
	public int[][] getChaser()
	{return chaser;}
	
	public int[] getSizes()
	{int[] sizes = {chasercount, GRID_SIZE}; return sizes;}
	
	public int[] getPlayer()
	{int[] player = {playerColumn, playerRow, counter}; return player;}
	
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
		
		for (int i = 0; i < chasercount; i++)
		{
			chaser[0][i] = random.nextInt(GRID_SIZE);
			chaser[1][i] = random.nextInt(GRID_SIZE);
			randommove[i] = 0;
		}
		
		finished = false;
		counter = 0;
		
		playerColumn = random.nextInt(GRID_SIZE);
		playerRow = random.nextInt(GRID_SIZE);
		
		repaint();
	}
	
	public void load(int[]sizes, int[][]loadboard, int[][]loadchaser, int[] loadplayer)
	{
		GRID_SIZE = sizes[1];
		chasercount = sizes[0];
		
		for (int i = 0; i < GRID_SIZE; i++)
			{
				for (int j = 0; j < GRID_SIZE; j++)
				{
					board[i][j] = loadboard[i][j];
				}
			}
		
		for (int i = 0; i < chasercount; i++)
		{
			chaser[0][i] = loadchaser[0][i];
			chaser[1][i] = loadchaser[1][i];
			randommove[i] = 0;
		}
		
		finished = false;
		counter = loadplayer[2];
		
		playerColumn = loadplayer[0];;
		playerRow = loadplayer[1];
		
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
	    			playerColumn = column;
	    			playerRow = row;
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
		for (int i = 0; i < chasercount; i++)
		{nextStep(i);}
		
		repaint();
		
		moveDone = true;
	}
	
	public void nextStep(int chasernum)
	{
			
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
				case 'a': if (isValidMove(playerColumn-1, playerRow)) {playerColumn--;} else {return;}
				break;
				case 's': if (isValidMove(playerColumn, playerRow+1)) {playerRow++;} else {return;}
				break;
				case 'd': if (isValidMove(playerColumn+1, playerRow)) {playerColumn++;} else {return;}
				break;
				case 'w': if (isValidMove(playerColumn, playerRow-1)) {playerRow--;} else {return;}
				break;
			}
			
			counter++;
		
			repaint();

			timer.start(); 
			moveDone = false;
		}
	}
	
	public void paint(Graphics g)
	{
		for (int i = 0; i < chasercount; i++)
		{
			if (chaser[0][i] == playerColumn && chaser[1][i] == playerRow) 
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
		g2D.fillRect(playerColumn * BOX_SIZE, playerRow * BOX_SIZE, BOX_SIZE, BOX_SIZE);
		
		if (!finished) {g2D.setPaint(halfred.brighter());}
		else {g2D.setPaint(new Color (50, 250, 50));}
		
		for (int i = 0; i < chasercount; i++)
		{g2D.fillRect(chaser[0][i] * BOX_SIZE, chaser[1][i] * BOX_SIZE, BOX_SIZE, BOX_SIZE);}
		
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		if (!finished)
		{
			g2D.setPaint(Color.DARK_GRAY);
			g2D.setFont(new Font (null, Font.PLAIN, PANEL_SIZE/40));
			if (counter >= 100) {counterpush1 = 350;}
			else if (counter >= 10) {counterpush1 = 110;}
			else {counterpush1 = 65;}
			g2D.drawString
			(""+counter, playerColumn * BOX_SIZE + (PANEL_SIZE/counterpush1), playerRow * BOX_SIZE + (PANEL_SIZE/30));
		}
		
		else 
		{
			g2D.setPaint(halfred);
			g2D.setFont(new Font (null, Font.PLAIN, PANEL_SIZE/2));
			if (counter >= 100) {counterpush2 = 11.6;}
			else if (counter >= 10) {counterpush2 = 4.7;}
			else {counterpush2 = 2.7;}
			g2D.drawString
			(""+counter, (int)(GRID_SIZE/counterpush2 * BOX_SIZE), (int)(GRID_SIZE/1.5 * BOX_SIZE));
		}
		
		
	}
	
	public void changeSize(int c) 
	{
		PANEL_SIZE+=c; BOX_SIZE = (PANEL_SIZE /GRID_SIZE)+1; 
		repaint(); 
		this.setPreferredSize(new Dimension(PANEL_SIZE,PANEL_SIZE));
	}
}