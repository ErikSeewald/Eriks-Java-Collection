package infdungeons;

import java.util.Random;

import ejcMain.util.EJC_Util.Direction;

public class Room 
{
	Room[] neighbors; // directions following order N-E-S-W
	final int[] coordinates;
	
	boolean enemiesCleared = false;
	
	public static final class Door
	{
		public static final byte locked = 0, open = 1, blocked = -1;
		public static final byte[] state_chances = {20, 40, 40};
	}
	private byte[] doors; // door directions following order N-E-S-W
	
	public class Chest
	{	
		public static final byte chance_1_in_4 = 1, chance_1_in_2 = 2;
		
		public static final byte key = 1, bomb = 2, enemy = 3, health = 4;
		byte content;
		
		int i, j; //tile array indices
				
		Chest(Random random, int i, int j)
		{
			this.i = i;
			this.j = j;
			
			switch (random.nextInt(4))
			{
				case 0: this.content = Chest.key; break;
				case 1: this.content = Chest.bomb; break;
				case 2: this.content = Chest.enemy; break;
				case 3: this.content = Chest.health; break;
			}
		}
	}
	Chest chest;
	
	public byte[][] tiles; // tiles to spawn blocks, enemies, chests etc in
	public static final int tiles_x = 21, tiles_y = 11;
	public static final byte empty_tile = 0, chest_tile = 1, block_tile = 2, reddorb_tile = 3, yellorb_tile = 4;
	
	Room(Random random, int[] coordinates)
	{
		this.coordinates = coordinates;
		tiles = new byte[tiles_x][tiles_y];
		
		neighbors = new Room[4];
		doors = new byte[4];
		generateDoors(random);
		generateChest(random, Chest.chance_1_in_4);
		generateBlocks(random);
		generateEnemies(random);
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
	
	/**
     * Generates chests based on random chance
     * @param random the Random object to be used
     * @param chance Chest.chance_1_in_4 or Chest.chance_1_in_2
     */
	public void generateChest(Random random, byte chance)
	{
		if (random.nextInt(4) >= chance) {return;}
		
		int[] index = getValidIndex(random);
		if (index == null) {return;}
		
		if (this.chest != null) {tiles[this.chest.i][this.chest.j] = empty_tile;} // clear previous chest
		
 		this.chest = new Chest(random, index[0], index[1]);
 		tiles[index[0]][index[1]] = chest_tile;
	}
	
	private void generateBlocks(Random random)
	{
		int block_count = random.nextInt(45);
		for (int i = 0; i < block_count; i++)
		{
			int[] index = getValidIndex(random);
			if (index == null) {return;}
			tiles[index[0]][index[1]] = block_tile;
		}
	}
	
	private void generateEnemies(Random random)
	{
		int enemy_count = random.nextInt(16);
		
		for (int i = 0; i < enemy_count; i++)
		{
			int[] index = getValidIndex(random);
			if (index == null) {return;}
			
			byte type = Room.empty_tile;
			int rand = random.nextInt(4);
			if (rand < 3) {type = Room.reddorb_tile;}
			else {type = Room.yellorb_tile;}
			
			tiles[index[0]][index[1]] = type;
		}
	}
	
	private int[] getValidIndex(Random random)
	{
		int[] index = new int[2];
		int check = 0, check_max = tiles_x * tiles_y;
		do
		{
			index[0] = random.nextInt(tiles_x);
			index[1] = random.nextInt(tiles_y);
			check++;
			if (check >= check_max) {return null;}
		}
		while (tiles[index[0]][index[1]] != empty_tile);
		return index;
	}
	
	public byte getDoor(Direction direction)
	{return doors[direction.ordinal()];}
	
	public void setDoor(Direction direction, byte state)
	{doors[direction.ordinal()] = state;}
}