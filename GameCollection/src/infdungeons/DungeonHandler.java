package infdungeons;

import java.util.HashMap;
import java.util.Random;
import infdungeons.Player.Direction;

public class DungeonHandler 
{
	protected Player player;
	private Random random;
	
	private HashMap<Long, Room> rooms;
	
	DungeonHandler()
	{
		random = new Random();
		
		player = new Player();
		player.setRoom(new Room(random, new int[] {0,0}));
		
		rooms = new HashMap<>();
	}
	
	private void doorEvent(Direction direction)
	{
		Room cur_room = player.getRoom();
		Room next_room = cur_room.neighbors[direction.ordinal()];
		
		// KNOWN PATH
		if (next_room != null)
		{
			player.setRoom(next_room);
			return;
		}
		
		// NEW PATH -> CHECK IF IT CONNECTS TO KNOWN ROOM, ELSE CREATE A NEW ROOM
		int[] new_coords = cur_room.coordinates;
		switch (direction)
		{
			case NORTH: new_coords[1]++; break;
			case EAST: new_coords[0]++; break;
			case SOUTH: new_coords[1]--; break;
			case WEST: new_coords[0]--; break;
		}
		
		// CONVERT TO SINGLE LONG AS KEY TO AVOID TOO MANY COLLISIONS IN HASHMAP
		// (array keys -> mainly look at first value of the array -> encounter collisions)
		// store bits of first int in upper half long and second int in lower half
		long key = ((long) new_coords[0] << 32) | new_coords[1]; 
		
		if (rooms.containsKey(key)) {player.setRoom(rooms.get(key));}
		
		else
		{
			player.setRoom(new Room(random, new_coords));
			rooms.put(key, player.getRoom());
		}
	}
}
