package SnakesAndLadders;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	
	private Timer autoMoveTimer;
	public boolean hasStarted = false;
	
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
	
	SnL_Panel()
	{
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		autoMoveTimer = new Timer(500, this);
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
	}
	
	public void start(int playerCount)
	{
		this.playerCount = playerCount;
		players = new Player[playerCount];
		
		for (int i = 0; i < playerCount; i++)
		{players[i] = new Player(i+1);}
		
		setCurrentPlayer(0);
		repaint();
		
		hasStarted = true;
	}
	
	public void setRolledGridPosition(int pos)
	{players[currentPlayer].rolledGridPos+= pos;}
	
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
			setCurrentPlayer(currentPlayer+1);
			GUI.enableDieButton(true); 
			GUI.enableStartButton(true);
			return;
		}
		
		++players[currentPlayer].gridPos;
		players[currentPlayer].setScreenPosition();
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{autoMove();}
	
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
}
