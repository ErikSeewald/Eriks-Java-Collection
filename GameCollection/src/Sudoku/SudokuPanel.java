package Sudoku;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

public class SudokuPanel extends JPanel
{
	private static final long serialVersionUID = 3986470365499168687L;
	
	static final int GRID_SIZE = 9;
	int PANEL_SIZE = 600;
	int BOX_SIZE = (PANEL_SIZE /GRID_SIZE)+1;
	
	int[][] board = new int [GRID_SIZE][GRID_SIZE];			//row, column
	int oldboard[][] = new int [GRID_SIZE][GRID_SIZE];		//state of board before "solve" method
		
	/*	column	 0 1 2 3 4 5 6 7 8	  
	 								   row
				{7,0,2,0,5,0,6,0,0},  	0
				{0,0,0,0,0,3,0,0,0},	1
				{1,0,0,0,0,9,5,0,0},	2
				{8,0,0,0,0,0,0,9,0},	3
				{0,4,3,0,0,0,7,5,0},	4
				{0,9,0,0,0,0,0,0,8},	5
				{0,0,9,7,0,0,0,0,5},	6
				{0,0,0,2,0,0,0,0,0},	7
				{0,0,7,0,4,0,2,0,3},	8 */
		
	int activeColumn = -1;						//column that has been clicked on - by default outside of screen
	int activeRow = -1;
	
	long startTime;								//used to check if "solve" is taking too long
	
	SudokuPanel()
	{
		this.setPreferredSize(new Dimension (PANEL_SIZE,PANEL_SIZE));	
		
		ClickListener clickListener = new ClickListener();
		this.addMouseListener(clickListener);
	}
	
	private class ClickListener extends MouseAdapter
	{
	    public void mousePressed(MouseEvent e) 
	    {  
	    	activeColumn = e.getX() / BOX_SIZE;
	    	activeRow = e.getY() / BOX_SIZE;
	    	repaint();
	    }
	}
	
	public void reset()
	{
		for (int i = 0; i < GRID_SIZE; i++)
		{for (int j = 0; j < GRID_SIZE; j++){board[i][j] = 0; oldboard[i][j] = 0;}}
		repaint();
	}
	
	public void changeValue(int x)
	{
		if (x >= 0 && x <=GRID_SIZE)
		{
			board[activeRow][activeColumn] = x;
			oldboard[activeRow][activeColumn] = x;
		}
		
		activeColumn = -1;
    	activeRow = -1;
    	repaint();
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
			
		g2D.setFont(new Font ("Arial", Font.PLAIN, PANEL_SIZE / 14));
		
		int columnpush = 0;
		int rowpush = 0; 
		
		for (int row = 0; row < GRID_SIZE; row++)
		{
			for (int column = 0; column < GRID_SIZE; column++)
			{	
				if (oldboard[row][column] != 0) 
				{
					g2D.setPaint(Color.GRAY);
					g2D.fillRect(column * BOX_SIZE, row * BOX_SIZE, BOX_SIZE, BOX_SIZE);
					g2D.setPaint(Color.DARK_GRAY);
				}
				
				g2D.drawRect(column * BOX_SIZE, row * BOX_SIZE, BOX_SIZE, BOX_SIZE); //Grid
		
				g2D.fillRect(activeColumn * BOX_SIZE, activeRow * BOX_SIZE, BOX_SIZE, BOX_SIZE);
				
				g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				
				if (board[row][column] != 0) 
				{
					g2D.drawString(""+board[row][column], (PANEL_SIZE/28)+columnpush, (PANEL_SIZE/13)+2+rowpush);
				}

				g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
				
				columnpush+= BOX_SIZE;
			}
			columnpush = 0;
			rowpush+= BOX_SIZE;
		}
		
		g2D.setStroke(new BasicStroke(PANEL_SIZE/150)); //Large Box Grid
		for (int i = 1; i <= 2; i++) 
		{
		g2D.drawLine(0, BOX_SIZE*3*i, BOX_SIZE*9, BOX_SIZE*3*i); 	//horizontal lines
		g2D.drawLine(BOX_SIZE*3*i, 0, BOX_SIZE*3*i, BOX_SIZE*9);	//vertica lines
		}
		
	}
	
	public void sudoku() {startTime = System.currentTimeMillis(); solve(board); repaint();}
	
	private boolean solve(int[][] board)
	{
		
		if ((System.currentTimeMillis()-startTime)>500) {reset(); return false;}
		
		for (int row = 0; row < GRID_SIZE; row++)
		{
			for (int column = 0; column < GRID_SIZE; column++)
			{
				if (board[row][column] == 0)
				{
					for (int number = 1; number <= GRID_SIZE; number++)
					{
						if (isValidPlacement(board,number,row,column))
						{
							board[row][column] = number; 
							
							if (solve(board)) {return true;} //check if it can solve board with current inputs recursively
							else {board[row][column] = 0;} //backtrack if cannot solve rest of board
						}
					}
					return false; //if current position is not solvable
				}
			}
		}
		return true; //final return
	}
	
	private static boolean isValidPlacement(int[][] board, int number, int row, int column)
	{
		return !isNumberInRow(board,number,row) && !isNumberInColumn(board,number,column)
				&& !isNumberInBox(board, number,row,column);
	}

	
	private static boolean isNumberInRow(int[][] board, int number, int row)
	{
		for (int i = 0; i < GRID_SIZE; i++)
		{if(board[row][i] == number) {return true;}}
		return false;
	}
	
	
	private static boolean isNumberInColumn(int[][] board, int number, int column)
	{
		for (int i = 0; i < GRID_SIZE; i++)
		{if(board[i][column] == number) {return true;}}
		return false;
	}
	
	
	private static boolean isNumberInBox(int[][] board, int number, int row, int column)
	{
		int localBoxRow = row - row % 3;
		int localBoxColumn = column - column % 3;
		
		for (int i = localBoxRow; i < localBoxRow + 3; i++)
		{
			for (int j = localBoxColumn; j < localBoxColumn + 3; j++)
			{if(board[i][j] == number) {return true;}}
		}
		return false;	
	}

}
