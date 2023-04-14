package infdungeons;

import java.util.HashMap;
import java.util.Random;
import infdungeons.Player.Direction;
import infdungeons.Room.Door;

public class DungeonHandler 
{
	private Inf_Panel panel;
	protected Player player;
	private Random random;
	
	private HashMap<String, Room> rooms;
	
	private int room_width, room_height;
	private int top_left_x, top_left_y;
	
	private int[][] door_coords;
	
	DungeonHandler(Inf_Panel panel)
	{
		this.panel = panel;
		
		random = new Random();
		rooms = new HashMap<>();
		
		player = new Player(this);
		Room first_room = new Room(random, new int[] {0,0});
		rooms.put(makeHashKey(first_room.coordinates), first_room);
		first_room.setDoor(Direction.NORTH, Door.open); // do not trap player in first room
		player.setRoom(first_room);
		
		door_coords = new int[4][2];
	}
	
	public void changeRoomSize(int width, int height)
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
	
	public boolean playerInDoor(Direction direction)
	{
		int[] door = door_coords[direction.ordinal()];
		int offset = player.size * 2;
		return player.x > door[0] - offset && player.x < door[0] + offset
				&& player.y > door[1] - offset && player.y < door[1] + offset;
	}
	
	public void doorEvent()
	{
		Room room = player.getRoom();
		for (Direction direction : Direction.values())
		{
			if (room.getDoor(direction) == Door.open && playerInDoor(direction))
			{doorEvent(direction); return;}
			else if (room.getDoor(direction) == Door.locked)
			{keyEvent(direction); return;}
		}
	}
	
	private void keyEvent(Direction direction)
	{
		if (player.key_count < 1 || !playerInDoor(direction)) {return;}
		
		player.key_count--;
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
			case NORTH: player.y -= player.size; break;
			case EAST: player.x += player.size; break;
			case SOUTH: player.y += player.size; break;
			case WEST: player.x -= player.size; break;
		}
	}
	
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