package infdungeons;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import infdungeons.Player.Direction;

public class Inf_Panel extends JPanel
{
	private static final long serialVersionUID = -1258477262677794055L;
	
	private int PANEL_HEIGHT = 700;
	private int PANEL_WIDTH = (int) (PANEL_HEIGHT * 1.52);
	
	private Player player;
	
	Inf_Panel()
	{
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		
		player = new Player();
		player.setSize(PANEL_HEIGHT / 26);
		player.speed = PANEL_HEIGHT / 120;
	}
	
	public void stop() {}
	
	public int getPlayerSpeed() {return player.speed;}
	
	public void movePlayer(int x, int y)
	{
		if (x != 0)
		{
			player.x+=x;
			player.direction = x > 0 ? Direction.EAST : Direction.WEST;
		}
		
		else
		{
			player.y+=y;
			player.direction = y > 0 ? Direction.SOUTH : Direction.NORTH;
		}
		
		repaint();
	}
	
	//---------------------------------------PAINT---------------------------------------
	
	private static final Color background_col = new Color(55, 55, 75);
	private static final Color tile_col = new Color(50, 50, 70);
	private static final Color player_col = new Color(120, 220, 90);
	private static final Color sword_col = new Color(220, 220, 225);
	
	private static final BasicStroke tile_stroke = new BasicStroke(8);
	
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		
		//BACKGROUND
		g2D.setPaint(background_col);
		g2D.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
		
		//TILES
		g2D.setPaint(tile_col);
		g2D.setStroke(tile_stroke);
		
		int tile_size = PANEL_HEIGHT / 17;
		for (int i = 0; i < 27; i++)
		{g2D.drawLine(i*tile_size, 0, i*tile_size, PANEL_HEIGHT);} //vertical
		
		for (int i = 0; i < 18; i++)
		{g2D.drawLine(0 , i * tile_size, PANEL_WIDTH, i * tile_size);} //horizontal
		
		//PLAYER
		g2D.setPaint(player_col);
		int player_size = player.getSize();
		g2D.fillRect(player.x, player.y, player_size, player_size);
		
		//SWORD
		g2D.setPaint(sword_col);
		
		int sword_length = player.getSwordLength();
		boolean facing_north_south = player.direction % 180 == 0;
		int sword_width = facing_north_south ? sword_length / 3 : sword_length;
		int sword_height = facing_north_south ? sword_length : sword_length / 3;
		
		int offset_x = 0, offset_y = 0;
		
		if (facing_north_south)
		{
			offset_x = sword_length - sword_width/2;
			offset_y = player.direction == Direction.NORTH ? -sword_length : player_size;
		}
		
		else
		{
			offset_x = player.direction == Direction.WEST ? -sword_length : player_size;
			offset_y = sword_length - sword_height/2;
		}
		
		g2D.fillRect(player.x + offset_x, player.y + offset_y, sword_width, sword_height);
	}

}
