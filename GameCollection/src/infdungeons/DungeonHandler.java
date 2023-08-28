package infdungeons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import infdungeons.Room.Chest;
import infdungeons.Room.Door;
import infdungeons.enemies.Enemy;
import infdungeons.enemies.Projectile;
import infdungeons.enemies.Reddorb;
import infdungeons.enemies.Yellorb;
import infdungeons.player.Bomb;
import infdungeons.player.Player;
import Main.EJC_Util;
import Main.EJC_Util.Direction;

public class DungeonHandler 
{
	private Inf_Panel panel;
	protected Player player;
	private Random random;
	
	private HashMap<String, Room> rooms;
	private Room first_room;
	
	private int roomWidth, roomHeight;
	private int topLeftX, topLeftY;
	private int tileFieldX, tileFieldY, tileSize;
	
	private int[][] doorCoords;

	private ArrayList<Enemy> enemies; // current enemies on screen
	// As soon as we leave a room, only the information about the enemies spawning tiles is saved, not the enemy objects
	private ArrayList<Enemy> enemyAddQueue;
	
	private ArrayList<Bomb> droppedBombs;
	
	DungeonHandler(Inf_Panel panel, int PANEL_WIDTH, int PANEL_HEIGHT)
	{
		this.panel = panel;
		
		enemies = new ArrayList<>();
		enemyAddQueue = new ArrayList<>();
		droppedBombs = new ArrayList<>();

		doorCoords = new int[4][2];
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
		roomWidth = (int) (width * 0.918);
		roomHeight = (int) (height * 0.81);
		topLeftX = (int) (width * 0.043);
		topLeftY = (int) (height * 0.125);
		
		int door_offset = panel.getTileSize() / 2;
		
		doorCoords[0][0] = width / 2;
		doorCoords[0][1] = topLeftY - door_offset;
		
		doorCoords[1][0] = topLeftX + roomWidth - door_offset;
		doorCoords[1][1] = height / 2;
		
		doorCoords[2][0] = width / 2;
		doorCoords[2][1] = topLeftY + roomHeight  - door_offset;
		
		doorCoords[3][0] = topLeftX  - door_offset;
		doorCoords[3][1] = height / 2;
		
		tileFieldX = (int) (topLeftX + roomWidth / 12.5);
		tileFieldY = (int) (topLeftY + roomHeight / 7.3);
		tileSize = (int) (roomWidth / 23.5);
	}
	
	/**
	 * Returns int[4][2] array of door coordinates in
	 * directional order N,E,S,W
	 * @return
	 * <ul> <li>{@link [d][0] door x}</li>
	 * 		<li>{@link [d][1] door y}</li> </ul>
	 */
	public int[][] getDoors()
	{return doorCoords.clone();}
	
	/**
	 * @return
	 * <ul> <li>{@link [0] top_left_x}</li>
	 * 		<li>{@link [1] top_left_y}</li>
	 * 		<li>{@link [2] room_width}</li>
	 * 		<li>{@link [3] room_height}</li> </ul>
	 */
	public int[] getRoomRect()
	{return new int[] {topLeftX, topLeftY, roomWidth, roomHeight};}
	
	/**
	 * Returns int[3] array of values indicating where the tile array starts
	 * & the tile size
	 * @return
	 * <ul> <li>{@link [0] tile_field_x}</li>
	 * 		<li>{@link [1] tile_field_y}</li>
	 * 		<li>{@link [2] tile_size}</li> </ul>
	 */
	public int[] getTileValues()
	{return new int[] {tileFieldX, tileFieldY, tileSize};}
	
	public boolean playerInDoor(Direction direction)
	{
		int[] door = doorCoords[direction.ordinal()];
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
		int x = chest.i * tileSize + tileFieldX, y = chest.j * tileSize + tileFieldY;
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
			case Chest.key: player.obtainKey(); 
			break;
			case Chest.bomb: player.obtainBomb(random.nextInt(4) + 1); 
			break;
			case Chest.enemy: enemies.add(new Reddorb(chest.i * tileSize + tileFieldX, chest.j * tileSize  + tileFieldY, player.getSize(), 0, 0));
			break;
			case Chest.health: player.heal(random.nextInt(Player.starting_hp) + 1);
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
		next_room.neighbors[EJC_Util.reverse(direction).ordinal()] = cur_room;
		
		changePlayerRoom(next_room, direction);
	}
	
	private void changePlayerRoom(Room next_room, Direction entrance_direction)
	{
		Direction exit_direction = EJC_Util.reverse(entrance_direction);
		next_room.setDoor(exit_direction, Door.open);
		
		player.setRoom(next_room);
		int[] exit_door = doorCoords[exit_direction.ordinal()];
		player.x = exit_door[0];
		player.y = exit_door[1];
		
		switch (entrance_direction)
		{
			case NORTH: player.y -= player.getSize() / 2; break;
			case EAST: player.x += player.getSize(); break;
			case SOUTH: player.y += player.getSize(); break;
			case WEST: player.x -= player.getSize() / 2; break;
		}
		
		droppedBombs.removeAll(droppedBombs);
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
				{enemies.add(new Reddorb(i * tileSize + tileFieldX, j * tileSize  + tileFieldY, player.getSize(), i, j));}
				
				else if (room.tiles[i][j] == Room.yellorb_tile) 
				{enemies.add(new Yellorb(i * tileSize + tileFieldX, j * tileSize  + tileFieldY, player.getSize(), i, j));}
			}
		}
	}
	
	public void bombDropEvent()
	{
		Bomb bomb = player.dropBomb();
		if (bomb != null)
		{
			bomb.startTimer();
			droppedBombs.add(bomb);
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
			if (!enemy.isAlive && enemy.getType() != Enemy.type_projectile)
			{room.tiles[enemy.index0][enemy.index1] = Room.empty_tile;}
			
			if (random.nextBoolean()) {continue;}
			enemy.move(random, room_rect);
			enemy.attack(player);
			
			// YELLORB SPECIFIC
			if (enemy.getType() != Enemy.type_yellorb) {continue;}
			Projectile projectile = ((Yellorb) enemy).getProjectile();
			if (projectile != null)
			{enemyAddQueue.add(projectile);}
		}
		
		//ADD ENEMIES WAITING IN QUEUE
		if (!enemyAddQueue.isEmpty())
		{
			enemies.addAll(enemyAddQueue);
			enemyAddQueue.removeAll(enemyAddQueue);
		}
		
		//REMOVE DEAD ENEMIES
		enemies.removeIf((e) -> !e.isAlive);
		
		// IF ALL ENEMIES WERE KILLED
		if (!room.enemiesCleared && enemies.size() == 0) 
		{
			// random chance to spawn chest after player defeats all enemies
			room.generateChest(random, Chest.chance_1_in_2);
			room.enemiesCleared = true;
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
		for (Bomb bomb : droppedBombs)
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
		
		droppedBombs.removeIf((b) -> b.hasExplosionFinished());
	}
	
	private boolean isInsideBomb(Bomb bomb, int x, int y, int size)
	{
		return x + size > bomb.x - bomb.dmgRadius && x < bomb.x + bomb.dmgRadius
				&& y + size > bomb.y - bomb.dmgRadius && y < bomb.y + bomb.dmgRadius;
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
	
	public ArrayList<Bomb> getDroppedBombs() {return droppedBombs;}
	
	private static String makeHashKey(int[] coordinates)
	{
		// using int[] as key for HashMap does not work -> "x,x" is a decent enough, unambiguous key
		return coordinates[0] + "," + coordinates[1];
	}
}