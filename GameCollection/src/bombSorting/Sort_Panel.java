package bombSorting;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class Sort_Panel extends JPanel
{
	private static final long serialVersionUID = -7304963195064748685L;
	private int PANEL_HEIGHT = 700;
	private int PANEL_WIDTH = (int) (PANEL_HEIGHT * 1.5);
	
	// PLATES
	private int plateWidth;
	private int plateHeight;
	private int plateRedX;
	private int plateBlackX;
	private int plateY;

	// HANDLERS
	private GameHandler gameHandler;
	private MouseHandler mouseHandler;
	
	Sort_Panel()
	{
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		gameHandler = new GameHandler(this);
		
		mouseHandler = new MouseHandler(this, gameHandler);
		this.addMouseListener(mouseHandler.new ClickListener());
		this.addMouseMotionListener(mouseHandler.new DragListener());
		this.addMouseListener(mouseHandler.new ReleaseListener());
		
		this.init_sizes();
		gameHandler.start();
	}
	
	public void changeSize(int change)
	{
		//SAVE RELATIVE SCREEN POSITIONS
		float[] xPos = new float[gameHandler.bombs.size()];
		float[] yPos = new float[gameHandler.bombs.size()];
		for (int i = 0; i < gameHandler.bombs.size(); i++)
		{
			xPos[i] = (float) gameHandler.bombs.get(i).x  / PANEL_WIDTH;
			yPos[i] = (float) gameHandler.bombs.get(i).y  / PANEL_HEIGHT;
		}
		
		PANEL_HEIGHT += change;
		PANEL_WIDTH = (int) (PANEL_HEIGHT * 1.5);
		this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
		this.init_sizes();
		
		//UPDATE RELATIVE SCREEN POSITIONS
		for (int i = 0; i < gameHandler.bombs.size(); i++)
		{
			gameHandler.bombs.get(i).x = (int) (xPos[i] * PANEL_WIDTH);
			gameHandler.bombs.get(i).y = (int) (yPos[i] * PANEL_HEIGHT);
		}
		
		this.repaint();
	}
	
	private void init_sizes()
	{
		plateWidth = (int) (PANEL_WIDTH / 3.5);
		plateHeight = PANEL_HEIGHT / 2;
		plateRedX = PANEL_HEIGHT / 40;
		plateBlackX = PANEL_WIDTH - plateWidth - plateRedX;
		plateY = (int) (PANEL_HEIGHT / 2.6);
		Bomb.size = PANEL_HEIGHT / 16;

		gameHandler.setPlateCoordinates(plateRedX, plateBlackX, plateY, plateWidth, plateHeight);
		gameHandler.setSpawnerCoordinates(PANEL_HEIGHT / 6);
	}
	
	public void stop()
	{gameHandler.stop(); gameHandler = null; mouseHandler = null; System.gc();}
	
	//---------------------------------------PAINT---------------------------------------
	
	private static final Color background_col = new Color(50, 50, 65);
	private static final Color border_col = new Color(80, 80, 95);
	private static final Color left_plate_col = new Color(205, 70, 80);
	private static final Color right_plate_col = new Color(40, 40, 50);
	private static final Color iron_col = new Color(160, 160, 175);
	private static final Color[] bomb_colors = {Color.white, new Color(20, 20, 35), new Color(145, 40, 50)}; // Color.white for debugging
	
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		
		//BACKGROUND
		g2D.setPaint(background_col);
		g2D.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
				
		//PLATES
		g2D.setPaint(left_plate_col);
		g2D.fillRect(plateRedX, plateY, plateWidth, plateHeight);
		
		g2D.setPaint(right_plate_col);
		g2D.fillRect(plateBlackX, plateY, plateWidth, plateHeight);
		
		//PLATE BORDERS
		g2D.setStroke(new BasicStroke(PANEL_HEIGHT / 60));
		g2D.setPaint(border_col);
		g2D.drawRect(plateRedX, plateY, plateWidth, plateHeight);
		g2D.drawRect(plateBlackX, plateY, plateWidth, plateHeight);
		
		//BOMBS
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.setFont(new Font("", Font.PLAIN, Bomb.size / 2));
		for (Bomb bomb: gameHandler.bombs)
		{
			paintBomb(g2D, bomb, bomb.x, bomb.y);
		}
		
		//GUI BOARD
		g2D.setPaint(border_col);
		g2D.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT / 6);
		
		g2D.setPaint(iron_col);
		g2D.setFont(new Font("", Font.BOLD, PANEL_HEIGHT / 10));
		g2D.drawString("SCORE: " + gameHandler.getScore(), (int) (PANEL_WIDTH / 2.9), PANEL_HEIGHT / 8);
	}
	
	private void paintBomb(Graphics2D g2D, Bomb bomb, int x, int y)
	{
		//SPHERE
		g2D.setPaint(bomb_colors[bomb.type]);
		g2D.fillOval(x, y, Bomb.size, Bomb.size);
		
		//TOP
		g2D.setPaint(iron_col);
		g2D.fillRect(x + Bomb.size / 4, y - Bomb.size / 10, Bomb.size / 2, Bomb.size / 5);
		
		//TIMER
		if (bomb.sortState == Bomb.sorted) {return;}
		
		if (bomb.sortState == Bomb.sorted_incorrectly || bomb.timer < 1)
		{g2D.drawString("X", x + (int) (Bomb.size / 2.8), y + (int) (Bomb.size * 0.7));	}
		else 
		{g2D.drawString(""+bomb.timer, x + (int) (Bomb.size / 2.8), y + (int) (Bomb.size * 0.7));}	
	}
}