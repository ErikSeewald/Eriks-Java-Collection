package SnakesAndLadders;

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
import java.awt.event.MouseMotionAdapter;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class SnL_Panel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 7284705418024953236L;
	
	private final int PANEL_WIDTH = 1170;
	private final int PANEL_HEIGHT = 780;
	
	private final Color backgroundColor = new Color(50,50,70);
	private final Color gridColor = new Color(120,120,140);
	
	private final int gridSize = 130;
	
	//PLAYERS
	private int playerCount;
	private final int playerSize = 60;
	private final Color[] playerColors = 
	{
			new Color(255,80, 80), 
			new Color(120,120, 240),
			new Color(80,180, 80),
			new Color(220,130, 60),  
			new Color(240,140, 240),
	};
	private int currentPlayer;
	private Player[] players = new Player[playerCount];
	
	private SnL_GUI GUI;
	private Timer autoMoveTimer;
	private Timer SnLMoveTimer;
	
	boolean moveAvailable = false;
	private boolean clickInside = false;
	boolean finished = false;
	public boolean hasStarted = false;
	
	//SNAKES AND LADDERS
	private Square[] squares = new Square[54];
	private int snakeAndLadderIterations = 0;
	
	SnL_Panel()
	{
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		autoMoveTimer = new Timer(500, this);
		SnLMoveTimer = new Timer(20,this);
		
		ClickListener clickListener = new ClickListener();
		DragListener dragListener = new DragListener();
		ReleaseListener releaseListener = new ReleaseListener();
		
		this.addMouseListener(clickListener);
		this.addMouseMotionListener(dragListener);
		this.addMouseListener(releaseListener);
		
		generateSnakesAndLadders();
	}
	
	public void addGUI(SnL_GUI GUI)
	{this.GUI = GUI;}

	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		super.paint(g2D);
		
		//BACKGROUND
		g2D.setPaint(backgroundColor);
		g2D.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
		
		//GRID
		g2D.setPaint(gridColor);
		
		for (int i = 0; i < 9; i++) //columns
		{g2D.drawLine(i*gridSize, 0, i*gridSize, PANEL_HEIGHT);}
		
		for (int i = 0; i < 6; i++) //rows
		{g2D.drawLine(0, i*gridSize, PANEL_WIDTH, i*gridSize);}
		
		//GRID NUMBERS
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2D.setFont(new Font("", Font.BOLD, 20));
		int gridNum = 0;
		for (int i = 6; i >= 0; i--) 
		{
			//Make the numbers go up the grid in "S" order (First go 8 right, then up a row, then go 8 left, then up, then 8 right ...)
			if (i % 2 == 0) 
			{
				for (int j = 0; j < 9; j++)
				{g2D.drawString(""+gridNum, j*gridSize + 5, i*gridSize -5); ++gridNum;}
			}
			
			else 
			{
				for (int j = 8; j >= 0; j--)
				{g2D.drawString(""+gridNum, j*130 + 5, i*130 -5);++gridNum;}
			}
		}
		
		//SNAKES AND LADDERS
		Color ladderColor = Color.green;
		Color snakeColor = new Color(255,80,80);
		
		for (int i = 0; i < 54; i++) 
		{
			//LADDERS
			int ladderEnd = squares[i].ladderEnd;
			if (ladderEnd > -1)
			{
				g2D.setPaint(ladderColor);
				g2D.drawLine(squares[i].centerX, squares[i].centerY, squares[ladderEnd].centerX, squares[ladderEnd].centerY);
				paintArrow(g2D, ladderColor, squares[i], squares[ladderEnd]);
				
				paintNormalofArrow
				(g2D, squares[i].centerX-squares[ladderEnd].centerX, squares[i].centerY-squares[ladderEnd].centerY, squares[i], ladderColor);
			}
				
			//SNAKES
			int snakeEnd = squares[i].snakeEnd;
			if (snakeEnd > -1)
			{
				g2D.setPaint(snakeColor);
				g2D.drawLine(squares[i].centerX, squares[i].centerY, squares[snakeEnd].centerX, squares[snakeEnd].centerY);
				paintArrow(g2D, snakeColor, squares[i], squares[snakeEnd]);
				
				paintNormalofArrow
				(g2D, squares[i].centerX-squares[snakeEnd].centerX, squares[i].centerY-squares[snakeEnd].centerY, squares[i], snakeColor);
			}
		}
		
		//PLAYERS
		for (Player player : players)
		{
			g2D.setPaint(playerColors[player.playerNum-1]);
			g2D.fillOval(player.screenX, player.screenY, playerSize, playerSize);
			
			g2D.setPaint(Color.white);
			g2D.setFont(new Font("", Font.BOLD, 40));
			
			int xOffset = 19;
			if (player.playerNum == 1 || player.playerNum == 4)
			{xOffset = 18;}
			
			g2D.drawString(""+player.playerNum, player.screenX+xOffset, player.screenY+44);	
		}	
		
		//FINISHED
		if (finished)
		{
			g2D.setPaint(playerColors[players[currentPlayer].playerNum-1]);
			g2D.setFont(new Font("", Font.BOLD, 120));
			g2D.drawString("PLAYER "+(currentPlayer+1)+" WINS", 117, 430);
		}	
	}
	
	private void paintArrow(Graphics2D g2D, Color color, Square square1, Square square2)
	{
		int Bx = square2.centerX;
		int By = square2.centerY;
		 
		double offs = 30 * Math.PI / 180.0;
		double angle = Math.atan2(square1.centerY - By, square1.centerX - Bx);
		int[] xs = { Bx + (int) (18 * Math.cos(angle + offs)), (int) Bx, Bx + (int) (18 * Math.cos(angle - offs)) };
		int[] ys = { By + (int) (18 * Math.sin(angle + offs)), (int) By, By + (int) (18 * Math.sin(angle - offs)) };
		
		g2D.setColor(color);
		g2D.drawPolyline(xs, ys, 3);
	}
	
	private void paintNormalofArrow(Graphics2D g2D, int x, int y, Square square, Color color)
	{
		double x1 = y, y1 = -x;
		double length1 = Math.sqrt(x1*x1 + y1*y1);
		x1 /= length1/12;
		y1 /= length1/12;
		
		double x2 = -y, y2 = x;
		double length2 = Math.sqrt(x2*x2 + y2*y2);
		x2 /= length2/12;
		y2 /= length2/12;
		
		g2D.setPaint(color);
		g2D.drawLine((int)x1+square.centerX, (int)y1+square.centerY, (int)x2+square.centerX, (int)y2+square.centerY);
	}

	public void start(int playerCount)
	{
		this.playerCount = playerCount;
		players = new Player[playerCount];
		
		for (int i = 0; i < playerCount; i++)
		{players[i] = new Player(i+1);}
		
		setCurrentPlayer(0);
		
		hasStarted = true;
		finished = false;
		GUI.enableDieButton(true);
		
		generateSnakesAndLadders();
		repaint();
	}
	
	
	//PLAYER IMPLEMENTATION
	
	public void setRolledGridPosition(int pos)
	{
		players[currentPlayer].rolledGridPos+= pos;
		if (players[currentPlayer].rolledGridPos > 53) {players[currentPlayer].rolledGridPos = 53;}
	}
	
	private void setCurrentPlayer(int x)
	{
		currentPlayer = x;
		if (currentPlayer >= playerCount) {currentPlayer = 0;}
		
		GUI.currentPlayerLabel.setText("Player "+(currentPlayer+1));
	}
	
	public void autoMove()
	{
		if (!autoMoveTimer.isRunning()) {autoMoveTimer.start();}
		
		else if (players[currentPlayer].gridPos == players[currentPlayer].rolledGridPos)
		{
			autoMoveTimer.stop();
			
			if (players[currentPlayer].gridPos == 53)
			{finish(); return;}
			
			snakeNladderCheck();
			return;
		}
		
		++players[currentPlayer].gridPos;
		players[currentPlayer].setScreenPosition();
		repaint();
	}
	
	
	private class Player
	{
		int playerNum;
		
		int screenX;
		int screenY;
		
		int gridPos = 0;
		int rolledGridPos = 0;
		
		Player(int playerNum)
		{this.playerNum = playerNum; setScreenPosition();}
		
		public void setScreenPosition()
		{
			int screenGridX;
			if (gridPos % 18 >= 9) {screenGridX = 8 - gridPos % 9;}
			else {screenGridX = gridPos % 9;}
		
			screenX = 35 + gridSize * screenGridX;
			screenY = PANEL_HEIGHT - 95 - gridSize * (gridPos / 9);
		}
	}
	
	
	//MANUAL MOVING IMPLEMENTATION
	
	private class ClickListener extends MouseAdapter
	{
	    public void mousePressed(MouseEvent e) 
	    {
	    	if (!moveAvailable) {return;}
	    	
	    	clickInside =
	    	(
	    		e.getX() > players[currentPlayer].screenX && e.getX() < players[currentPlayer].screenX+playerSize
	    		&& e.getY() > players[currentPlayer].screenY && e.getY() < players[currentPlayer].screenY+playerSize
	    	);
	    }
	}
	
	private class DragListener extends MouseMotionAdapter
	{
	    public void mouseDragged(MouseEvent e) 
	    {
	    	if (!moveAvailable || !clickInside) {return;}
	    	
	    	GUI.enableAutoMoveButton(false);
	    	
	    	if (e.getX() < PANEL_WIDTH && e.getX() > 0) 
	    	{players[currentPlayer].screenX = e.getX()-playerSize/2;}
	    	
	    	if (e.getY() < PANEL_HEIGHT && e.getY() > 0) 
	    	{players[currentPlayer].screenY = e.getY()-playerSize/2;}
	    	
	    	repaint();	
	    }
	}
	
	private class ReleaseListener extends MouseAdapter
	{
	    public void mouseReleased(MouseEvent e) 
	    {
	    	if (!moveAvailable || !clickInside) {return;}
	    	
	    	int releaseGridPos;
	    	
	    	if ((e.getY()/gridSize) % 2 == 0)
	    	{
	    		//same as below but column starting from the right instead
	    		releaseGridPos = ( (PANEL_WIDTH - e.getX()) / gridSize) +9*( (PANEL_HEIGHT - e.getY() )/ gridSize );
	    	}
	    	else 
	    	{
	    		//column + 9*row    (PANEL_HEIGHT-e.getY() because Graphics2D coordinates ascend from top to bottom)
	    		releaseGridPos = (e.getX()/gridSize) +9*( (PANEL_HEIGHT - e.getY()) / gridSize );
	    	}
	    	
	    	if (players[currentPlayer].rolledGridPos == releaseGridPos)
	    	{
	    		players[currentPlayer].gridPos = releaseGridPos;
	    		
	    		players[currentPlayer].setScreenPosition();
				moveAvailable = false;
				
				if (releaseGridPos == 53)
				{clickInside = false; finish(); return;}
				
				snakeNladderCheck();
	    	}
	    	
	    	clickInside = false;
	    	
	    	repaint();
	    }
	}
	
	private void finish()
	{
		finished = true; 
		GUI.enableDieButton(false);  
		GUI.enableStartButton(true); 
		repaint(); 
	}
	
	//SNAKES AND LADDERS IMPLEMENTATION
	
	private class Square
	{
		//-1 means there is no ladder/snake, int >= 0 will indicate the square the ladder/snake is connected to
		int ladderEnd = -1;
		int snakeEnd  = -1;
		
		int gridPos;
		int centerX, centerY;
		
		public void calculateCenterPoint()
		{
			int screenGridX;
			if (gridPos % 18 >= 9) {screenGridX = 8 - gridPos % 9;}
			else {screenGridX = gridPos % 9;}
		
			centerX = 60 + gridSize * screenGridX;
			centerY = PANEL_HEIGHT - 20 - gridSize * (gridPos / 9);
		}
	}
	
	private void snakeNladderCheck()
	{
		int playerPos = players[currentPlayer].gridPos;
		
		if (snakeAndLadderIterations < 6) //prevent infinite loops
		{
			if (squares[playerPos].snakeEnd > -1)
			{
				SnakeEvent(players[currentPlayer]);
			}
			
			else if (squares[playerPos].ladderEnd > -1)
			{
				LadderEvent(players[currentPlayer]);
			}
			
			else
			{nextTurn();}
		}
		
		else
		{nextTurn();}
	}
	
	private void nextTurn()
	{
		GUI.enableDieButton(true); 
		GUI.enableStartButton(true);
		moveAvailable = false;
		snakeAndLadderIterations = 0;
		setCurrentPlayer(currentPlayer+1); 
	}
	
	private void SnakeEvent(Player player)
	{
		player.gridPos = squares[player.gridPos].snakeEnd;
		player.rolledGridPos = player.gridPos;
		GUI.enableDieButton(false);
		++snakeAndLadderIterations;
		SnLMoveTimer.start();
	}
	
	private void LadderEvent(Player player)
	{
		player.gridPos = squares[player.gridPos].ladderEnd;
		player.rolledGridPos = player.gridPos;
		GUI.enableDieButton(false);
		++snakeAndLadderIterations;
		SnLMoveTimer.start();
	}
	
	private void moveAlongConnection(Player player)
	{
		//save cur position so you can generate and save goal position and then move the player back to cur position
		int curPlayerX = player.screenX, curPlayerY = player.screenY;
		
		player.setScreenPosition();
		int goalX = player.screenX, goalY = player.screenY;
		
		player.screenX = curPlayerX;
		player.screenY = curPlayerY;
		
		
		double[] v = new double[2];
		v[0] = goalX - player.screenX;
		v[1] = goalY - player.screenY;
	
		double movelength = Math.sqrt(Math.pow(v[0], 2)+Math.pow(v[1], 2));
		
		v[0] /= movelength*0.1; v[1] /= movelength*0.1;
		
		player.screenX+= v[0];
		player.screenY+= v[1];
		
		
		if (player.screenX > goalX-10 &&  player.screenX < goalX+10 && player.screenY > goalY-10 &&  player.screenY < goalY+10)
		{
			player.setScreenPosition();//if its somewhat there, snap into position
			SnLMoveTimer.stop();
			snakeNladderCheck();
		} 
		
		repaint();
	}
	
	private void generateSnakesAndLadders()
	{
		Random random = new Random();
		
		int snakeNladderCount = 0;
		for (int i = 0; i < 54; i++)
		{
			squares[i] = new Square();
			squares[i].gridPos = i;
			squares[i].calculateCenterPoint();
			
			//skip 0 to make sure no ladder is going out from the starting point, skip after 16 connections so it doesnt get too clustered
			//No connections to final square
			if (i == 0 || snakeNladderCount > 16 || i == 53) {continue;} 
			
			//SNAKES
			if (random.nextInt(10) == 1) //else if so there cannot be both a ladder and a snake going out from a square
			{
				if (i > 9) {squares[i].snakeEnd = random.nextInt(i-9);} //no snake going out from a square in the bottom row
				++snakeNladderCount;
			}
			
			//LADDERS
			else if (random.nextInt(10) == 1)
			{
				if (i < 44) //no ladders going up from the highest row
				{
					squares[i].ladderEnd = random.nextInt(53-i-9)+i+9; //difference of 9 so the ladder/snake is definitely in another row
					++snakeNladderCount;
				}
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == autoMoveTimer)
		{autoMove();}

		else if (e.getSource() == SnLMoveTimer)
		{moveAlongConnection(players[currentPlayer]);}
	}
	
	public void stopAllTimers()
	{autoMoveTimer.stop(); SnLMoveTimer.stop(); GUI.stopRollTimer();}
}
