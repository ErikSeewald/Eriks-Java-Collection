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
	
	private int PANEL_SIZE = 600;
	private int BOX_SIZE = (PANEL_SIZE / 9) + 1;
	
	private int[][] board = new int [9][9];	//row, column
	private int activeColumn = -1, activeRow = -1;
	
	SudokuPanel()
	{
		this.setPreferredSize(new Dimension (PANEL_SIZE,PANEL_SIZE));	
		
		ClickListener clickListener = new ClickListener();
		this.addMouseListener(clickListener);
	}
	
	private class ClickListener extends MouseAdapter
	{
	    public void mousePressed(MouseEvent e) 
	    {setActivePosition(e.getX() / BOX_SIZE, e.getY() / BOX_SIZE); }
	}
	
	public void reset()
	{
		board = null; System.gc();
		board = new int[9][9];
		repaint();
	}
	
	public void changeValue(int x)
	{
		if (activeRow < 0 || activeColumn < 0) {return;}
		if (x < 0 || x > 9) {return;}
		
		if (x == 0 || SudokuAlgorithm.isValidPlacement(board, x, activeRow, activeColumn))
		{board[activeRow][activeColumn] = x;}
		
		setActivePosition(-1,-1);
	}
	
	private void setActivePosition(int column, int row)
	{
		activeColumn = column;
    	activeRow = row;
    	repaint();
	}
	
	public void changeSize(int amount) 
	{
		PANEL_SIZE+= amount; 
		BOX_SIZE = (PANEL_SIZE /9)+1; 
		this.setPreferredSize(new Dimension(PANEL_SIZE,PANEL_SIZE));
		repaint(); 
	}
	
	public void sudoku()
	{SudokuAlgorithm.solve(board); repaint();}
		
	//---------------------------------------PAINT---------------------------------------
	
	private static final Color background = new Color(45,45,60);
	private static final Color foreground = new Color(190,190,210);
	
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

		for (int row = 0; row < 9; row++)
		{
			for (int column = 0; column < 9; column++)
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
}