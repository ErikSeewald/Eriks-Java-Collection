package snakesAndLadders;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.util.HashMap;
import javax.swing.JPanel;
import snakesAndLadders.board.BoardHandler;
import snakesAndLadders.board.Player;
import snakesAndLadders.board.Square;
import snakesAndLadders.gui.SnL_GUI;

public class SnL_Panel extends JPanel
{
	private static final long serialVersionUID = 7284705418024953236L;
	public static final int PANEL_WIDTH = 1170,  PANEL_HEIGHT = 780;
	public static final int square_size = 130;
	public static final int player_size = 60;
	
	private MouseHandler mouseHandler;
	private BoardHandler boardHandler;
	
	SnL_Panel(SnL_GUI GUI)
	{	
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		
		boardHandler = new BoardHandler(this, GUI);
		GUI.addBoardHandler(boardHandler);
		
		mouseHandler = new MouseHandler(this, GUI, boardHandler);
		this.addMouseListener(mouseHandler.new ClickListener());
		this.addMouseMotionListener(mouseHandler.new DragListener());
		this.addMouseListener(mouseHandler.new ReleaseListener());
		
		boardHandler.start(1);
	}
	
	public void stop()
	{boardHandler.stop();}
	
	public void restart()
	{arrows.clear(); repaint();}
	
	//---------------------------------------PAINT---------------------------------------
	
	private static final Color background_col = new Color(50,50,70);
	private static final Color grid_col = new Color(120,120,140);
	private static final Color ladder_col = Color.green, snake_col = new Color(255,80,80);
	
	private static final Color[] playerColors = 
	{
			new Color(255,80, 80), 
			new Color(120,120, 240),
			new Color(80,180, 80),
			new Color(220,130, 60),  
			new Color(240,140, 240),
	};
	
	public void paint(Graphics g)
	{
		Toolkit.getDefaultToolkit().sync(); // Force flush (for X11)
		Graphics2D g2D = (Graphics2D) g;
		
		//BACKGROUND
		g2D.setPaint(background_col);
		g2D.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
		
		//GRID
		g2D.setPaint(grid_col);
		for (int i = 0; i < 9; i++) //columns
		{g2D.drawLine(i*square_size, 0, i*square_size, PANEL_HEIGHT);}
		
		for (int i = 0; i < 6; i++) //rows
		{g2D.drawLine(0, i*square_size, PANEL_WIDTH, i*square_size);}
		
		//GRID NUMBERS
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2D.setFont(new Font("", Font.BOLD, 20));
		int gridNum = 0;
		for (int row = BoardHandler.rows; row >= 0; row--) 
		{
			//Make the numbers go up the grid in "S" order (First go 8 right, then up a row, then go 8 left, then up, then 8 right ...)
			if (row % 2 == 0) 
			{
				for (int column = 0; column < BoardHandler.columns; column++)
				{g2D.drawString(""+gridNum, column*square_size + 5, row*square_size -5); ++gridNum;}
			}
			
			else 
			{
				for (int column = BoardHandler.columns; column >= 0; column--)
				{g2D.drawString(""+gridNum, column*square_size + 5, row*square_size -5);++gridNum;}
			}
		}
		
		//SNAKES AND LADDERS
		for (Square square : boardHandler.squares) 
		{
			//LADDERS
			if (square.ladder != null)
			{
				g2D.setPaint(ladder_col);
				g2D.drawLine(square.centerX, square.centerY, square.ladder.centerX, square.ladder.centerY);
				paintArrow(g2D, ladder_col, new Square[] {square, square.ladder});
			}
				
			//SNAKES
			if (square.snake != null)
			{
				g2D.setPaint(snake_col);
				g2D.drawLine(square.centerX, square.centerY, square.snake.centerX, square.snake.centerY);
				paintArrow(g2D, snake_col, new Square[] {square, square.snake});
			}
		}
		
		//PLAYERS
		for (Player player : boardHandler.players)
		{
			g2D.setPaint(playerColors[player.playerNum-1]);
			g2D.fillOval(player.x, player.y, player_size, player_size);
			
			g2D.setPaint(Color.white);
			g2D.setFont(new Font("", Font.BOLD, 40));
			
			//1 and 4 don't look like they are in the middle with offset 19, the others do
			int xOffset = 19;
			if (player.playerNum == 1 || player.playerNum == 4)
			{xOffset = 18;}
			
			g2D.drawString(""+player.playerNum, player.x+xOffset, player.y+44);	
		}	
		
		//FINISHED
		if (boardHandler.hasFinished())
		{
			Player player = boardHandler.getCurrentPlayer();
			g2D.setPaint(playerColors[player.playerNum-1]);
			g2D.setFont(new Font("", Font.BOLD, 120));
			g2D.drawString("PLAYER "+(player.playerNum)+" WINS", 117, 430);
		}
	}
	
	//ARROWS
	private class Arrow
	{
		int[] headA, headB;
		
		Arrow(Square square1, Square square2)
		{
			//ARROW HEAD
			int Bx = square2.centerX, By = square2.centerY;
			
			double offs = 30 * Math.PI / 180.0;
			double angle = Math.atan2(square1.centerY - By, square1.centerX - Bx);
			int[] xs = { Bx + (int) (18 * Math.cos(angle + offs)), (int) Bx, Bx + (int) (18 * Math.cos(angle - offs)) };
			int[] ys = { By + (int) (18 * Math.sin(angle + offs)), (int) By, By + (int) (18 * Math.sin(angle - offs)) };
			
			this.headA = xs;
			this.headB = ys;
		}
	}
	
	private HashMap<Square[], Arrow> arrows = new HashMap<>();
	
	private void paintArrow(Graphics2D g2D, Color color, Square[] squares)
	{	
		Arrow arrow = arrows.get(squares);
		if (arrow == null) {arrow = new Arrow(squares[0], squares[1]); arrows.put(squares, arrow);}
		
		//HEAD
		g2D.setColor(color);
		g2D.drawPolyline(arrow.headA, arrow.headB, 3);
		
		//ROOT
		g2D.fillRect(squares[0].centerX -3, squares[0].centerY - 3, 6, 6);
	}
}