package snakesAndLadders.board;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;

import ejcMain.EJC_Util;
import snakesAndLadders.SnL_Panel;
import snakesAndLadders.gui.SnL_GUI;

public class BoardHandler implements ActionListener
{
	public static final int columns = 9, rows = 6;

	//PLAYERS
	private int playerCount = 1;
	private Player currentPlayer;
	public Player[] players;
	
	private Timer autoMoveTimer;
	private Timer SnLMoveTimer;
	
	private boolean moveAvailable = false;
	private boolean finished = false;
	
	//SNAKES AND LADDERS
	public Square[] squares = new Square[54];
	private int snakeAndLadderIterations = 0;
	
	private SnL_Panel panel;
	private SnL_GUI GUI;
	private Random random;
	
	public BoardHandler(SnL_Panel panel, SnL_GUI GUI) 
	{
		this.panel = panel;
		this.GUI = GUI;

		autoMoveTimer = new Timer(500, this);
		SnLMoveTimer = new Timer(20,this);
		random = new Random();
	}
	
	public void start(int playerCount)
	{
		this.playerCount = playerCount;
		players = null; System.gc();
		players = new Player[playerCount];
		
		for (int i = 0; i < playerCount; i++)
		{players[i] = new Player(i+1);}
		
		setCurrentPlayer(0);
		
		finished = false;
		GUI.enableDieButton(true);
		
		generateSnakesAndLadders();
		panel.restart();
	}
	
	public void stop() {autoMoveTimer.stop(); SnLMoveTimer.stop(); GUI.stopRollTimer();}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == autoMoveTimer)
		{autoMove();}

		else if (e.getSource() == SnLMoveTimer)
		{moveAlongConnection(currentPlayer);}
	}
	
	//---------------------------------------PLAYER---------------------------------------
	
	public void setRolledGridPosition(int pos)
	{
		currentPlayer.rolledGridPos+= pos;
		if (currentPlayer.rolledGridPos > 53) {currentPlayer.rolledGridPos = 53;}
	}
	
	private void setCurrentPlayer(int x)
	{
		x--; //for array index 0
		if (x >= playerCount || x < 0) {x = 0;}
		currentPlayer = players[x];
		
		GUI.setCurrentPlayerLabel("Player "+(currentPlayer.playerNum));
	}
	
	public void autoMove()
	{
		moveAvailable = false;
		
		if (!autoMoveTimer.isRunning()) 
		{autoMoveTimer.start();}
		
		else if (currentPlayer.gridPos == currentPlayer.rolledGridPos)
		{
			autoMoveTimer.stop();
			
			if (currentPlayer.gridPos == 53)
			{finish(); return;}
			
			SnLCheck(currentPlayer.gridPos);
			return;
		}
		
		++currentPlayer.gridPos;
		currentPlayer.setScreenPosition();
		panel.repaint();
	}
	
	public void releasePlayer(Player player, int x, int y)
	{
		int releaseGridPos;

		x/= SnL_Panel.square_size;
		y/= SnL_Panel.square_size;
		
		if (y % 2 == 1)
		{releaseGridPos = x + columns*(rows -1 - y);}//current column + 9*row (-1 because of 0)
		
		else 
		{releaseGridPos = (columns -1 - x) + columns*(rows -1 -y);} //same as above but flipped because of the way the numbers snake up

		if (player.rolledGridPos != releaseGridPos) {return;}
		
		player.gridPos = releaseGridPos;
		player.setScreenPosition();
		setMoveAvailable(false);

		if (releaseGridPos == 53)
		{finish(); return;}

		SnLCheck(player.gridPos);
	}
	
	public Player getCurrentPlayer() {return currentPlayer;}
	
	private void finish()
	{
		finished = true; 
		GUI.enableDieButton(false);  
		GUI.enableStartButton(true); 
		panel.repaint(); 
	}
	
	public void setMoveAvailable(boolean b) {moveAvailable = b;}
	
	public boolean isMoveAvailable() {return moveAvailable;}
	
	public boolean hasFinished() {return finished;}
	
	//---------------------------------------SNAKES_AND_LADDERS---------------------------------------
	
	private class SNL
	{
		public static final boolean snake = false;
		public static final boolean ladder = true;
	}
	
	private void SnLCheck(int playerPos)
	{
		if (snakeAndLadderIterations < 6) //prevent infinite loops
		{
			if (squares[playerPos].snake != null)
			{SnLEvent(currentPlayer, SNL.snake);  return;}
				
			else if (squares[playerPos].ladder != null)
			{SnLEvent(currentPlayer, SNL.ladder);  return;}
		}
				
		nextTurn();
	}
	
	private void nextTurn()
	{
		GUI.enableDieButton(true); 
		GUI.enableStartButton(true);
		moveAvailable = false;
		snakeAndLadderIterations = 0;
		setCurrentPlayer(currentPlayer.playerNum+1); 
	}
	
	private void SnLEvent(Player player, boolean ladder)
	{
		if (ladder) 
		{player.gridPos = squares[player.gridPos].ladder.gridPos;}
		else
		{player.gridPos = squares[player.gridPos].snake.gridPos;}
		
		player.rolledGridPos = player.gridPos;
		GUI.enableDieButton(false);
		setPlayerGoal(player);
		SnLMoveTimer.start();
		
		++snakeAndLadderIterations;
	}
	
	private void setPlayerGoal(Player player)
	{
		//save cur position so you can generate and save goal position and then move the player back to cur position
		int curPlayerX = player.x, curPlayerY = player.y;
		
		player.setScreenPosition();
		player.goalX = player.x;
		player.goalY = player.y;
		
		player.x = curPlayerX;
		player.y = curPlayerY;
	}
	
	private void moveAlongConnection(Player player)
	{
		float[] v = EJC_Util.normalize(player.goalX - player.x, player.goalY - player.y);
		player.x+= v[0] * 10;
		player.y+= v[1] * 10;
		
		if (player.x > player.goalX-10 &&  player.x < player.goalX+10 && player.y > player.goalY-10 &&  player.y < player.goalY+10)
		{
			player.setScreenPosition();//if its somewhat there, snap into position
			SnLMoveTimer.stop();
			SnLCheck(currentPlayer.gridPos);
		} 
		panel.repaint();
	}
	
	private void generateSnakesAndLadders()
	{	
		int[] snakes = new int[54], ladders = new int[54];
		squares = null; System.gc();
		squares = new Square[54];
		
		for (int i = 0; i < 54; i++)
		{
			squares[i] = new Square();
			squares[i].gridPos = i;
			squares[i].calculateCenterPoint();
			
			//SNAKES
			if (i > 9 && random.nextInt(10) == 1) //i > 9 => no snake going out from a square in the bottom row
			{
				snakes[i] = random.nextInt(i-9);
			}
			
			//LADDERS
			else if (i < 44 && random.nextInt(10) == 1)//i < 44 => no ladders going up from the highest row
			{
				ladders[i] = random.nextInt(53-i-9)+i+9;
			}
		}
		
		for (int i = 1; i < 53; i++) //set square pointers separately, as some are still null during first loop
		{
			if (snakes[i] != 0)
			{squares[i].snake = squares[snakes[i]];}
			
			else if (ladders[i] != 0)
			{squares[i].ladder = squares[ladders[i]];}
		}
	}
}