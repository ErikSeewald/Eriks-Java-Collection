package SnakesAndLadders;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class SnL_Panel extends JPanel
{
	private static final long serialVersionUID = 7284705418024953236L;
	
	private final int PANEL_WIDTH = 1170;
	private final int PANEL_HEIGHT = 780;
	
	private final Color backgroundColor = new Color(50,50,70);
	private final Color gridColor = new Color(120,120,140);
	
	private final int gridSize = 130;
	
	private class Player
	{
		int playerNum;
		
		int screenX;
		int screenY;
		
		int gridPos = 0;
		
		Player(int playerNum)
		{this.playerNum = playerNum; setPosition();}
		
		public void setPosition()
		{
			int screenGridX;
			if (gridPos % 18 >= 9) {screenGridX = 8 - gridPos % 9;}
			else {screenGridX = gridPos % 9;}
		
			screenX = 35 + gridSize * screenGridX;
			screenY = PANEL_HEIGHT - 95 - gridSize * (gridPos / 9);
		}
	}
	
	public void changePlayerPos(int x)
	{players[0].gridPos+=x;players[0].setPosition(); repaint();}
	
	private int playerCount;
	private Player[] players = new Player[playerCount];
	
	private final int playerSize = 60;
	private final Color[] playerColors = 
	{
			new Color(255,80, 80), 
			new Color(120,120, 240),
			new Color(240,140, 240), 
			new Color(80,180, 80),
			new Color(220,130, 60),  
			new Color(240,140, 240),
	};
	
	SnL_Panel()
	{
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		
		start(1);
	}

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
		
		repaint();
	}
}
