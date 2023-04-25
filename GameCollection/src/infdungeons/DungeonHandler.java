package infdungeons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import infdungeons.Player.Direction;
import infdungeons.Room.Chest;
import infdungeons.Room.Door;

public class DungeonHandler 
{
	private Inf_Panel panel;
	protected Player player;
	private Random random;
	
	private HashMap<String, Room> rooms;
	private Room first_room;
	
	private int room_width, room_height;
	private int top_left_x, top_left_y;
	private int tile_field_x, tile_field_y, tile_size;
	
	private int[][] door_coords;

	private ArrayList<Enemy> enemies; // current enemies on screen
	// As soon as we leave a room, only the information about the enemies spawning tiles is saved, not the enemy objects
	
	private ArrayList<Bomb> dropped_bombs;
	
	DungeonHandler(Inf_Panel panel, int PANEL_WIDTH, int PANEL_HEIGHT)
	{
		this.panel = panel;
		
		enemies = new ArrayList<>();
		dropped_bombs = new ArrayList<>();

		door_coords = new int[4][2];
		setRoomSize(PANEL_WIDTH,PANEL_HEIGHT);
		
		random = new Random();
		rooms = new HashMap<>();
		

		first_room = new Room(random, new int[] {0,0});
		rooms.put(makeHashKey(first_room.coordinates), first_room);
		first_room.setDoor(Direction.NORTH, Door.open); // do not trap player in first room
		
		player = new Player(this, first_room, PANEL_HEIGHT / 26);
	}
	
	public void setRoomSize(int width, int height)
	{
		room_width = (int) (width * 0.918);
		room_height = (int) (height * 0.81);
		top_left_x = (int) (width * 0.043);
		top_left_y = (int) (height * 0.125);
		
		int door_offset = panel.getTileSize() / 2;
		
		door_coords[0][0] = width / 2;
		door_coords[0][1] = top_left_y - door_offset;
		
		door_coords[1][0] = top_left_x + room_width - door_offset;
		door_coords[1][1] = height / 2;
		
		door_coords[2][0] = width / 2;
		door_coords[2][1] = top_left_y + room_height  - door_offset;
		
		door_coords[3][0] = top_left_x  - door_offset;
		door_coords[3][1] = height / 2;
		
		tile_field_x = (int) (top_left_x + room_width / 12.5);
		tile_field_y = (int) (top_left_y + room_height / 7.3);
		tile_size = (int) (room_width / 23.5);
	}
	
	/**
	 * Returns int[4][2] array of door coordinates in
	 * directional order N,E,S,W
	 * @return
	 * <ul> <li>{@link [d][0] door x}</li>
	 * 		<li>{@link [d][1] door y}</li> </ul>
	 */
	public int[][] getDoors()
	{return door_coords.clone();}
	
	/**
	 * @return
	 * <ul> <li>{@link [0] top_left_x}</li>
	 * 		<li>{@link [1] top_left_y}</li>
	 * 		<li>{@link [2] room_width}</li>
	 * 		<li>{@link [3] room_height}</li> </ul>
	 */
	public int[] getRoomRect()
	{return new int[] {top_left_x, top_left_y, room_width, room_height};}
	
	/**
	 * Returns int[3] array of values indicating where the tile array starts
	 * & the tile size
	 * @return
	 * <ul> <li>{@link [0] tile_field_x}</li>
	 * 		<li>{@link [1] tile_field_y}</li>
	 * 		<li>{@link [2] tile_size}</li> </ul>
	 */
	public int[] getTileValues()
	{return new int[] {tile_field_x, tile_field_y, tile_size};}
	
	public boolean playerInDoor(Direction direction)
	{
		int[] door = door_coords[direction.ordinal()];
		int offset = player.getSize() * 2;
		return player.x > door[0] - offset && player.x < door[0] + offset
				&& player.y > door[1] - offset && player.y < door[1] + offset;
	}
	
	public void interactEvent()
	{
		//CHEST
		Chest chest = player.getRoom().chest;
		if (playerAtChest(chest)) 
		{chestEvent(chest); return;}
		
		//DOORS
		Room room = player.getRoom();
		for (Direction direction : Direction.values())
		{
			if (room.getDoor(direction) == Door.open && playerInDoor(direction))
			{doorEvent(direction); return;}
				
			else if (room.getDoor(direction) == Door.locked && playerInDoor(direction))
			{keyEvent(direction); return;}
		}	
	}
	
	private boolean playerAtChest(Chest chest)
	{
		if (chest == null) {return false;}
		int x = chest.i * tile_size + tile_field_x, y = chest.j * tile_size + tile_field_y;
		int offset = player.getSize() * 2;
		
		return player.x > x - offset && player.x < x + offset
				&& player.y > y - offset && player.y < y + offset;
	}
	
	private void chestEvent(Chest chest)
	{
		Room room = player.getRoom();
		room.chest = null;
		room.tiles[chest.i][chest.j] = Room.empty_tile;
		
		switch (chest.content)
		{
			case Chest.key: player.obtainKey(); break;
			case Chest.bomb: player.obtainBomb(); break;
		}
	}
	
	private void keyEvent(Direction direction)
	{
		if (player.key_count < 1) {return;}
		
		player.useKey();
		player.getRoom().setDoor(direction, Door.open);
		doorEvent(direction);
	}
	
	private void doorEvent(Direction direction)
	{
		Room cur_room = player.getRoom();
		Room next_room = cur_room.neighbors[direction.ordinal()];
		
		// KNOWN PATH
		if (next_room != null)
		{
			changePlayerRoom(next_room, direction);
			return;
		}
		
		// NEW PATH -> CHECK IF IT CONNECTS TO KNOWN ROOM, ELSE CREATE A NEW ROOM
		int[] new_coords = cur_room.coordinates.clone();
		switch (direction)
		{
			case NORTH: new_coords[1]++; break;
			case EAST: new_coords[0]++; break;
			case SOUTH: new_coords[1]--; break;
			case WEST: new_coords[0]--; break;
		}
		
		String hash_key = makeHashKey(new_coords);
		next_room = rooms.get(hash_key);
		
		if (next_room == null)
		{
			next_room = new Room(random, new_coords);
			rooms.put(hash_key, next_room);
		}
		
		// ADD BIDIRECTIONAL PATH
		cur_room.neighbors[direction.ordinal()] = next_room;
		next_room.neighbors[reverse(direction).ordinal()] = cur_room;
		
		changePlayerRoom(next_room, direction);
	}
	
	private void changePlayerRoom(Room next_room, Direction entrance_direction)
	{
		Direction exit_direction = reverse(entrance_direction);
		next_room.setDoor(exit_direction, Door.open);
		
		player.setRoom(next_room);
		int[] exit_door = door_coords[exit_direction.ordinal()];
		player.x = exit_door[0];
		player.y = exit_door[1];
		
		switch (entrance_direction)
		{
			case NORTH: player.y -= player.getSize() / 2; break;
			case EAST: player.x += player.getSize(); break;
			case SOUTH: player.y += player.getSize(); break;
			case WEST: player.x -= player.getSize() / 2; break;
		}
		
		dropped_bombs.removeAll(dropped_bombs);
		loadEnemies();
	}
	
	private void loadEnemies()
	{
		enemies.removeAll(enemies);
		System.gc();
		
		Room room = player.getRoom();
		for (int i = 0; i < Room.tiles_x; i++)
		{
			for (int j = 0; j < Room.tiles_y; j++)
			{
				if (room.tiles[i][j] == Room.reddorb_tile) 
				{enemies.add(new Reddorb(i * tile_size + tile_field_x, j * tile_size  + tile_field_y, player.getSize(), i, j));}
			}
		}
	}
	
	public void bombDropEvent()
	{
		Bomb bomb = player.dropBomb();
		if (bomb != null)
		{
			bomb.startTimer();
			dropped_bombs.add(bomb);
		}
	}
	
	public void update()
	{
		updateEnemies();
		updatePlayer();
		updateBombs();
	}
	
	private void updateEnemies()
	{
		Room room = player.getRoom();
		int[] room_rect = this.getRoomRect();
		for (Enemy enemy : enemies)
		{
			if (!enemy.isAlive)
			{room.tiles[enemy.index0][enemy.index1] = Room.empty_tile;}
			
			if (random.nextBoolean()) {continue;}
			enemy.move(random, room_rect);
			enemy.attack(player);
		}
		
		enemies.removeIf((e) -> !e.isAlive);
		
		if (!room.enemies_cleared && enemies.size() == 0) 
		{
			// random chance to spawn chest after player defeats all enemies
			room.generateChest(random, Chest.chance_1_in_2);
			room.enemies_cleared = true;
		}
	}
	
	private void updatePlayer()
	{
		player.updateTimers();
		if (!player.isAlive)
		{
			player.respawn(first_room);
			loadEnemies();
		}
		
		if (player.isAttacking)
		{
			int[] dmg_box = getDamageBox();
			
			for (Enemy enemy : enemies)
			{
				if (enemy.x > dmg_box[0] && enemy.y > dmg_box[1] 
					&& enemy.x < dmg_box[2] && enemy.y < dmg_box[3])
				{enemy.getHit(Player.attack_dmg);}
			}
		}
	}
	
	private void updateBombs()
	{
		for (Bomb bomb : dropped_bombs)
		{
			if (bomb.isExploding) 
			{
				//PLAYER
				if (isInsideBomb(bomb, player.x, player.y, player.getSize())) {player.getHit(Bomb.dmg);}
				
				//ENEMIES
				for (Enemy enemy: enemies)
				{if (isInsideBomb(bomb, enemy.x, enemy.y, enemy.size)) {enemy.getHit(Bomb.dmg);}}
			}
			
			else {bomb.countDown();}
		}
		
		dropped_bombs.removeIf((b) -> b.hasExplosionFinished());
	}
	
	private boolean isInsideBomb(Bomb bomb, int x, int y, int size)
	{
		return x + size > bomb.x - bomb.dmg_radius && x < bomb.x + bomb.dmg_radius
				&& y + size > bomb.y - bomb.dmg_radius && y < bomb.y + bomb.dmg_radius;
	}
		
	public int[] getDamageBox()
	{
		if (player.direction == null) {return null;}
		
		int[] dmg_box = new int[4]; // {e>x, e>y, e<x, e<y}
		int buffer_1 = player.getSize() >> 1, buffer_2 = player.getSize() << 1;
		
		switch (player.direction)
		{
			case NORTH: 
				dmg_box[0] = player.x - buffer_1; dmg_box[1] = player.y - buffer_2 - buffer_1; 
				dmg_box[2] = player.x + buffer_2; dmg_box[3] = player.y;
				break;
			case EAST:
				dmg_box[0] = player.x; dmg_box[1] = player.y - buffer_1; 
				dmg_box[2] = player.x + buffer_2 + buffer_1; dmg_box[3] = player.y + buffer_2;
				break;
			case SOUTH:
				dmg_box[0] = player.x - buffer_1; dmg_box[1] = player.y; 
				dmg_box[2] = player.x + buffer_2; dmg_box[3] = player.y + buffer_2 + buffer_1;
				break;
			case WEST:
				dmg_box[0] = player.x - buffer_2; dmg_box[1] = player.y - buffer_1; 
				dmg_box[2] = player.x; dmg_box[3] = player.y + buffer_2;
				break;
		}
		
		return dmg_box;
	}
	
	public ArrayList<Enemy> getEnemies() {return enemies;}
	
	public ArrayList<Bomb> getDroppedBombs() {return dropped_bombs;}
	
	private static Direction reverse(Direction direction)
	{
		return Direction.values()[(direction.ordinal() + 2) % 4];
	}
	
	private static String makeHashKey(int[] coordinates)
	{
		// using int[] as key for HashMap does not work -> "x,x" is a decent enough, unambiguous key
		return coordinates[0] + "," + coordinates[1];
	}
}