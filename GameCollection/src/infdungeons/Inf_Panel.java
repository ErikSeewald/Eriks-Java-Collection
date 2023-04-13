package infdungeons;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import infdungeons.Player.Direction;
import infdungeons.Room.Door;

public class Inf_Panel extends JPanel
{
	private static final long serialVersionUID = -1258477262677794055L;
	
	private int PANEL_HEIGHT = 700;
	private int PANEL_WIDTH = (int) (PANEL_HEIGHT * 1.52);
	
	protected Player player;
	private DungeonHandler gameHandler;
	
	Inf_Panel()
	{
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		
		gameHandler = new DungeonHandler();
		gameHandler.changeRoomSize(PANEL_WIDTH, PANEL_HEIGHT);
		player = gameHandler.player;
		player.size = PANEL_HEIGHT / 26;
		player.speed = PANEL_HEIGHT / 100;
	}
	
	public void stop() {}
	
	public int getPlayerSpeed() {return player.speed;}
	
	public void movePlayer(int x, int y)
	{
		player.move(x, y);
		repaint();
	}
	
	public void doorEvent() {gameHandler.doorEvent(); repaint();}
	
	public void changeSize(int amount)
	{
		PANEL_HEIGHT += amount;
		PANEL_WIDTH = (int) (PANEL_HEIGHT * 1.52);
		
		player.size = PANEL_HEIGHT / 26;
		player.speed = PANEL_HEIGHT / 100;
		
		tile_stroke = new BasicStroke(PANEL_HEIGHT / 85);
		player_stroke = new BasicStroke(tile_stroke.getLineWidth() /2);
		wall_stroke = new BasicStroke(tile_stroke.getLineWidth() * 2);
		
		gameHandler.changeRoomSize(PANEL_WIDTH, PANEL_HEIGHT);
		
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		repaint();
	}
	
	//---------------------------------------PAINT---------------------------------------
	
	private static final Color background_col = new Color(55, 55, 75);
	private static final Color tile_col = new Color(50, 50, 70);
	private static final Color player_col = new Color(120, 220, 90);
	private static final Color sword_col = new Color(220, 220, 225);
	
	private BasicStroke tile_stroke = new BasicStroke(PANEL_HEIGHT / 85);
	private BasicStroke player_stroke = new BasicStroke(tile_stroke.getLineWidth() /2);
	private BasicStroke wall_stroke = new BasicStroke(tile_stroke.getLineWidth() * 2);
	
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
		
		//WALL
		g2D.setPaint(tile_col.darker());
		g2D.setStroke(wall_stroke);
		int[] room = gameHandler.getRoomRect();
		g2D.drawRect(room[0], room[1], room[2], room[3]);
		
		//DOORS
		int[][] doors = gameHandler.getDoors();
		Direction[] directions = Direction.values();
		Room curRoom = player.getRoom();
		for (int i = 0; i < 4; i++)
		{
			byte door_state = curRoom.getDoor(directions[i]);
			if (door_state == Door.blocked) {continue;}
			
			g2D.setPaint(door_state == Door.open ? Color.green : Color.red);
			g2D.fillRect(doors[i][0], doors[i][1], tile_size, tile_size);
		}
		
		//PLAYER
		g2D.setPaint(player_col);
		g2D.fillRect(player.x, player.y, player.size, player.size);
		
		g2D.setPaint(player_col.darker());
		g2D.setStroke(player_stroke);
		g2D.drawRect(player.x, player.y, player.size, player.size);
		
		//SWORD
		if (player.isAttacking) {drawSword(g2D, player.size);}
		
		//GUI
		g2D.setPaint(Color.white);
		g2D.setFont(new Font("", Font.BOLD, player.size));
		g2D.drawString(curRoom.coordinates[0] + "," + curRoom.coordinates[1], room[0], room[1] - player.size);
	}
	
	private void drawSword(Graphics2D g2D, int player_size)
	{
		g2D.setPaint(sword_col);
		
		boolean facing_north_south = player.direction.angle % 180 == 0;
		
		int sword_width = facing_north_south ? player_size / 4 : player_size;
		int sword_height = facing_north_south ? player_size : player_size / 4;
		
		//KEEP SWORD LEFT HANDED
		int offset_x = 0, offset_y = 0;
		if (facing_north_south)
		{
			offset_x = player.direction == Direction.NORTH ? 0 - sword_width/2 : player_size - sword_width/2;
			offset_y = player.direction == Direction.NORTH ? -player_size : player_size;
		}
		
		else
		{
			offset_x = player.direction == Direction.WEST ? -player_size : player_size;
			offset_y = player.direction == Direction.WEST ? player_size - sword_height/2 : 0 - sword_height/2;
		}
		
		g2D.fillRect(player.x + offset_x, player.y + offset_y, sword_width, sword_height);
	}
}