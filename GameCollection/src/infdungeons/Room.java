package infdungeons;

import java.util.Random;
import infdungeons.Player.Direction;

public class Room 
{
	Room[] neighbors; // directions following order N-E-S-W
	final int[] coordinates;
	
	public static final class Door
	{
		public static final byte locked = 0, open = 1, blocked = -1;
		public static final byte[] state_chances = {10, 60, 30};
	}
	private byte[] doors; // door directions following order N-E-S-W
	
	public class Chest
	{
		boolean opened;
		byte content;
		int x, y;
		
		public static final byte key = 1;
		
		Chest(Random random, int x, int y)
		{
			this.x = x;
			this.y = y;
			
			this.opened = false;
			switch (1) // replace with random when more items are added
			{
				case 1: this.content = Chest.key;
			}
		}
	}
	private Chest chest;
	
	Room(Random random, int[] coordinates)
	{
		this.coordinates = coordinates;
		
		neighbors = new Room[4];
		doors = new byte[4];
		generateDoors(random);
		generateChest(random);
	}
	
	private void generateDoors(Random random)
	{
		for(int i = 0; i < 4; i++)
		{
			int r = random.nextInt(100);
			if (r < Door.state_chances[0]) {doors[i] = Door.locked;}
			else if (r < Door.state_chances[0] + Door.state_chances[1]) {doors[i] = Door.open;}
			else {doors[i] = Door.blocked;}
		}
	}
	
	private void generateChest(Random random)
	{
		this.chest = new Chest(random, 200, 200);
	}
	
	public byte getDoor(Direction direction)
	{return doors[direction.ordinal()];}
	
	public void setDoor(Direction direction, byte state)
	{doors[direction.ordinal()] = state;}
}