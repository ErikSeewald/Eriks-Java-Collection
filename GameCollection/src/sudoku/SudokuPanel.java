package sudoku;
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
	
	//RESOLUTION
	private static final int GRID_SIZE = 9;
	private int PANEL_SIZE = 600;
	private int BOX_SIZE = (PANEL_SIZE /GRID_SIZE)+1;
	
	//BOARD
	private int[][] board = new int [GRID_SIZE][GRID_SIZE];	//row, column	
	private int activeColumn = -1;	//column that has been clicked on
	private int activeRow = -1;
	
	//CONTROL
	private long startTime;		//check if "solve" is taking more than a minute (absolute max -> assume something has gone wrong)
	private static final Color background = new Color(45,45,60);
	private static final Color foreground = new Color(190,190,210);
	
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
		board = new int[GRID_SIZE][GRID_SIZE];
		repaint();
	}
	
	public void changeValue(int x)
	{
		if (activeRow < 0 || activeColumn < 0) {return;}
		if (x < 0 || x > GRID_SIZE) {return;}
		
		if (isValidPlacement(x, activeRow, activeColumn))
		{board[activeRow][activeColumn] = x;}
		
		activeColumn = -1;
    	activeRow = -1;
    	repaint();
	}
	
	public void changeSize(int c) 
	{
		PANEL_SIZE+=c; 
		BOX_SIZE = (PANEL_SIZE /GRID_SIZE)+1; 
		this.setPreferredSize(new Dimension(PANEL_SIZE,PANEL_SIZE));
		repaint(); 
	}
	
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		
		//BACKGROUND
		g2D.setPaint(background);
		g2D.fillRect(0, 0, PANEL_SIZE, PANEL_SIZE);
		
		//FOREGROUND
		g2D.setPaint(foreground);
		g2D.setFont(new Font ("Arial", Font.PLAIN, PANEL_SIZE / 14));
		
		int columnpush = 0, rowpush = 2; 
		
		for (int row = 0; row < GRID_SIZE; row++)
		{
			for (int column = 0; column < GRID_SIZE; column++)
			{	
				//GRID
				g2D.drawRect(column * BOX_SIZE, row * BOX_SIZE, BOX_SIZE, BOX_SIZE);
		
				//SELECTED BOX
				g2D.fillRect(activeColumn * BOX_SIZE, activeRow * BOX_SIZE, BOX_SIZE, BOX_SIZE);
				
				//VALUES
				g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				if (board[row][column] != 0) 
				{
					g2D.drawString(""+board[row][column], (PANEL_SIZE/28)+columnpush, (PANEL_SIZE/13)+rowpush);
				}
				g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
				
				columnpush+= BOX_SIZE;
			}
			columnpush = 0;
			rowpush+= BOX_SIZE;
		}
		
		//OUTER GRID
		g2D.setStroke(new BasicStroke(PANEL_SIZE/150));
		for (int i = 1; i <= 2; i++) 
		{
			g2D.drawLine(0, BOX_SIZE*3*i, BOX_SIZE*9, BOX_SIZE*3*i);
			g2D.drawLine(BOX_SIZE*3*i, 0, BOX_SIZE*3*i, BOX_SIZE*9);
		}
	}
	
	//SUDOKU ALGORITHM
	public void sudoku() 
	{startTime = System.currentTimeMillis(); solve(board); repaint();}
	
	private boolean solve(int[][] board)
	{
		if ((System.currentTimeMillis()-startTime) > 60000) {reset(); return false;}
		
		for (int row = 0; row < GRID_SIZE; row++)
		{
			for (int column = 0; column < GRID_SIZE; column++)
			{
				if (board[row][column] == 0)
				{
					for (int number = 1; number <= GRID_SIZE; number++)
					{
						if (isValidPlacement(number,row,column))
						{
							board[row][column] = number; 
							
							if (solve(board)) {return true;}
							else {board[row][column] = 0;} //backtrack if cannot solve rest of board
						}
					}
					return false; //if current position is not solvable
				}
			}
		}
		return true; //final return
	}
	
	private boolean isValidPlacement(int number, int row, int column)
	{
		return !isNumberInRow(number,row) && !isNumberInColumn(number,column)
				&& !isNumberInBox(number,row,column);
	}

	private boolean isNumberInRow(int number, int row)
	{
		for (int i = 0; i < GRID_SIZE; i++)
		{if(board[row][i] == number) {return true;}}
		return false;
	}
	
	private boolean isNumberInColumn(int number, int column)
	{
		for (int i = 0; i < GRID_SIZE; i++)
		{if(board[i][column] == number) {return true;}}
		return false;
	}
	
	private boolean isNumberInBox(int number, int row, int column)
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