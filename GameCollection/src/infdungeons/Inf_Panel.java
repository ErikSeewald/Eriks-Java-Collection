package infdungeons;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.JPanel;

import ejcMain.util.EJC_Util.Direction;
import infdungeons.Room.Door;
import infdungeons.enemies.Enemy;
import infdungeons.enemies.Projectile;
import infdungeons.enemies.Yellorb;
import infdungeons.player.Bomb;
import infdungeons.player.Player;

public class Inf_Panel extends JPanel
{
	private static final long serialVersionUID = -1258477262677794055L;
	
	private int PANEL_HEIGHT = 700;
	private int PANEL_WIDTH = (int) (PANEL_HEIGHT * 1.52);
	
	protected Player player;
	private DungeonHandler dungeonHandler;
	
	private boolean debugMode = false;
	
	Inf_Panel()
	{
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		
		dungeonHandler = new DungeonHandler(this, PANEL_WIDTH, PANEL_HEIGHT);
		player = dungeonHandler.player;
		player.speed = PANEL_HEIGHT / 100;
	}
	
	public void stop() {}
	
	public int getPlayerSpeed() {return player.speed;}
	
	public void updateDungeon() {dungeonHandler.update(); repaint();}
	
	public void interactEvent() {dungeonHandler.interactEvent(); repaint();}
	
	public void bombDropEvent() {dungeonHandler.bombDropEvent(); repaint();}
	
	public void changeSize(int amount)
	{
		// SAVE RELATIVE POSITIONS
		double[] playerLocRatio = {(double) player.x / PANEL_WIDTH, (double) player.y / PANEL_HEIGHT};
		
		ArrayList<Enemy> enemies = dungeonHandler.getEnemies();
		double[][] enemyLocRatio = new double[2][enemies.size()];
		for (int i = 0; i < enemies.size(); i++)
		{
			enemyLocRatio[0][i] = (double) enemies.get(i).x / PANEL_WIDTH;
			enemyLocRatio[1][i] = (double) enemies.get(i).y / PANEL_HEIGHT;
		}
		
		PANEL_HEIGHT += amount;
		PANEL_WIDTH = (int) (PANEL_HEIGHT * 1.52);
		
		// LOAD RELATIVE POSITIONS
		player.x = (int) (playerLocRatio[0] * PANEL_WIDTH);
		player.y = (int) (playerLocRatio[1] * PANEL_HEIGHT);
		
		player.setSize(PANEL_HEIGHT / 26);
		player.speed = PANEL_HEIGHT / 100;
		
		for (int i = 0; i < enemies.size(); i++)
		{
			Enemy enemy = enemies.get(i);
			enemy.x = (int) (enemyLocRatio[0][i] * PANEL_WIDTH);
			enemy.y = (int) (enemyLocRatio[1][i] * PANEL_HEIGHT);
			if (enemy.getType() != Enemy.type_projectile)
			{enemy.setSize(player.getSize());} // also handles enemy.speed
			else {enemy.setSize(player.getSize() / 2);}
		}
		
		// OTHERS
		tileStroke = new BasicStroke(PANEL_HEIGHT / 85);
		playerStroke = new BasicStroke(tileStroke.getLineWidth() /2);
		wallStroke = new BasicStroke(tileStroke.getLineWidth() * 2);
		
		bgTileSize = PANEL_HEIGHT / 17;
		
		dungeonHandler.setRoomSize(PANEL_WIDTH, PANEL_HEIGHT);
		
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		repaint();
	}
	
	public int getTileSize() {return bgTileSize;}
	
	public void switchDebugMode() 
	{	
		debugMode = !debugMode;
		if (!debugMode) // reset when exiting Debug Mode
		{player.respawn(player.getRoom());}
	}
	
	//---------------------------------------PAINT---------------------------------------
	
	private static final Color background_col = new Color(55, 55, 75);
	private static final Color tile_col = new Color(50, 50, 70);
	private static final Color player_col = new Color(120, 220, 90);
	private static final Color player_hurt_col = new Color(255, 40, 80);
	private static final Color sword_col = new Color(220, 220, 225);
	private static final Color door_open_col = new Color(38, 126, 255);
	private static final Color door_closed_col = new Color(255, 160, 28);
	private static final Color gui_col = new Color(180, 220, 255);
	private static final Color block_col_1 = new Color(50, 60, 90);
	private static final Color block_col_2 = block_col_1.darker();
	private static final Color chest_col = new Color(185, 70, 255);
	private static final Color chest_border_col = new Color(125, 60, 235);
	private static final Color bomb_col = new Color(20, 20, 40);
	private static final Color explosion_col = new Color(255, 120, 50);
	private static final Color reddorb_col = new Color(255, 80, 80);
	private static final Color yellorb_col = new Color(255, 200, 80);
	private static final Color yellorb_hurt_col = new Color(225, 110, 40);
	
	private BasicStroke tileStroke = new BasicStroke(PANEL_HEIGHT / 85);
	private BasicStroke playerStroke = new BasicStroke(tileStroke.getLineWidth() /2);
	private BasicStroke wallStroke = new BasicStroke(tileStroke.getLineWidth() * 2);
	
	private int bgTileSize = PANEL_HEIGHT / 17; // size of tiles in the background
	
	public void paint(Graphics g)
	{
		Toolkit.getDefaultToolkit().sync(); // Force flush (for X11)
		Graphics2D g2D = (Graphics2D) g;
		
		//BACKGROUND
		g2D.setPaint(background_col);
		g2D.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
		
		//TILES
		g2D.setPaint(tile_col);
		g2D.setStroke(tileStroke);
		
		for (int i = 0; i < 27; i++)
		{g2D.drawLine(i*bgTileSize, 0, i*bgTileSize, PANEL_HEIGHT);} //vertical
		
		for (int i = 0; i < 18; i++)
		{g2D.drawLine(0 , i * bgTileSize, PANEL_WIDTH, i * bgTileSize);} //horizontal
		
		//WALL
		g2D.setPaint(tile_col.darker());
		g2D.setStroke(wallStroke);
		int[] room = dungeonHandler.getRoomRect();
		g2D.drawRect(room[0], room[1], room[2], room[3]);
		
		//DOORS
		int[][] doors = dungeonHandler.getDoors();
		Direction[] directions = Direction.values();
		Room curRoom = player.getRoom();
		for (int i = 0; i < 4; i++)
		{
			byte door_state = curRoom.getDoor(directions[i]);
			if (door_state == Door.blocked) {continue;}
			
			g2D.setPaint(door_state == Door.open ? door_open_col : door_closed_col);
			g2D.fillRect(doors[i][0], doors[i][1], bgTileSize, bgTileSize);
		}
		
		//TILE ARRAY
		g2D.setStroke(tileStroke);
		drawTiles(g2D, curRoom);
		
		//PLAYER
		Color p_color = player.isInvincible() ? player_hurt_col : player_col;
		g2D.setPaint(p_color);
		g2D.fillRect(player.x, player.y, player.getSize(), player.getSize());
		
		g2D.setPaint(p_color.darker());
		g2D.setStroke(playerStroke);
		g2D.drawRect(player.x, player.y, player.getSize(), player.getSize());
		
		//ENEMIES
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		for (Enemy enemy : dungeonHandler.getEnemies())
		{
			switch (enemy.getType())
			{
				case Enemy.type_reddorb: drawReddorb(g2D, enemy.x, enemy.y, enemy.size); break;
				case Enemy.type_yellorb: drawYellorb(g2D, enemy); break;
				case Enemy.type_projectile: drawProjectile(g2D, (Projectile) enemy); break;
			}
		}
		
		//SWORD
		if (player.isAttacking) {drawSword(g2D, player.getSize());}
		
		//BOMBS
		drawBombs(g2D);
		
		//GUI
		int gui_size = (int) (bgTileSize * 0.75);
		g2D.setPaint(gui_col);
		g2D.setFont(new Font("", Font.BOLD, gui_size));
		g2D.drawString("Room " +curRoom.coordinates[0] + "," + curRoom.coordinates[1], room[0], room[1] - gui_size);
				
		g2D.setPaint(player.isInvincible() ? p_color : gui_col); // damage or standard color
		if (player.isInHealingAnimation()) {g2D.setPaint(player_col);}
		g2D.drawString("HP: " + player.hp, room[0] + gui_size * 10, room[1] - gui_size);
		
		g2D.setPaint(player.isInKeyAnimation() ? chest_col : gui_col);
		g2D.drawString("Keys: " + player.key_count, room[0] + gui_size * 20, room[1] - gui_size);
		
		g2D.setPaint(player.isInBombGUIAnimation() ? chest_col : gui_col);
		g2D.drawString("Bombs: " + player.bomb_count, room[0] + gui_size * 27, room[1] - gui_size);
		
		//DEBUG
		if (debugMode) {runDebugCode(g2D);}
	}
	
	private void drawReddorb(Graphics2D g2D, int x, int y, int size)
	{
		g2D.setPaint(reddorb_col);
		g2D.fillOval(x, y, size, size);
	}
	
	private void drawYellorb(Graphics2D g2D, Enemy yellorb)
	{
		g2D.setPaint(yellorb.hp == Yellorb.starting_hp ? yellorb_col : yellorb_hurt_col);
		g2D.fillOval(yellorb.x, yellorb.y, yellorb.size, yellorb.size);
	}
	
	private void drawProjectile(Graphics2D g2D, Projectile projectile)
	{
		if (projectile.parentType == Enemy.type_yellorb) {g2D.setPaint(yellorb_col);}
		
		g2D.fillRect(projectile.x, projectile.y, projectile.size, projectile.size);
	}
	
	private void drawBombs(Graphics2D g2D)
	{
		ArrayList<Bomb> bombs = dungeonHandler.getDroppedBombs();

		for (Bomb bomb : bombs)
		{
			if (bomb.isExploding)
			{
				g2D.setPaint(explosion_col);
				g2D.fillRect(bomb.x - bomb.dmgRadius, bomb.y - bomb.dmgRadius, bomb.dmgRadius * 2, bomb.dmgRadius * 2);
			}
			
			else
			{
				g2D.setPaint(bomb_col);
				g2D.fillRect(bomb.x, bomb.y, bomb.size, bomb.size);
			}
		}
	}
	
	private void drawTiles(Graphics2D g2D, Room curRoom)
	{
		int[] tile_values = dungeonHandler.getTileValues();
		int size = tile_values[2];
		byte tile;
		for (int i = 0; i < Room.tiles_x; i++)
		{
			for (int j = 0; j < Room.tiles_y; j++)
			{
				tile = curRoom.tiles[i][j];
				if (tile == Room.empty_tile) {continue;}
				
				else if (tile == Room.block_tile)
				{
					g2D.setPaint(block_col_2);
					g2D.fillRect(tile_values[0] + size * i, tile_values[1] + size * j, size, size);
					g2D.setPaint(block_col_1);
					int offset = size / 4;
					g2D.fillRect(tile_values[0] + size * i + offset, tile_values[1] + size * j + offset, offset * 2, offset * 2);
				}
				
				else if (tile == Room.chest_tile)
				{
					int offset = size / 3;
					g2D.setPaint(chest_col);
					g2D.fillRect(tile_values[0] + size * i + offset, tile_values[1] + size * j + offset, offset, offset);
					g2D.setPaint(chest_border_col);
					g2D.drawRect(tile_values[0] + size * i + offset, tile_values[1] + size * j + offset, offset, offset);
				}
			}
		}
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
	
	private void runDebugCode(Graphics2D g2D)
	{
		player.bomb_count = 100;
		player.key_count = 100;
		player.hp = 100;
		
		int[] box = dungeonHandler.getDamageBox();
		if (box == null) {return;}
		g2D.setPaint(Color.red);
		g2D.drawRect(box[0], box[1], box[2] - box[0], box[3] - box[1]);
	}
}