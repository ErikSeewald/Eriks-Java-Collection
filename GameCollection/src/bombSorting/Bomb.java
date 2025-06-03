package bombSorting;

import java.util.Random;

public class Bomb 
{
	public static final byte BLACK = 1;
	public static final byte RED = 2;
	public static final byte not_sorted = 0;
	public static final byte sorted = 1;
	public static final byte sorted_incorrectly = 2;
	
	public static int size = 20; //changed in Sort_Panel.initSizes
	
	public int timer = 6;
	public byte sortState = Bomb.not_sorted;
	public boolean isHeld = false;
	
	public int x, y;
	
	public byte type;
	
	/**
     * Creates a Bomb_Sorting bomb object
     * @param type Bomb.BLACK or Bomb.RED
     * @param x the x position of the bomb
     * @param y the y position of the bomb
     */
	Bomb(byte type, int x, int y)
	{
		this.type = type;
		this.x = x;
		this.y = y;
	}
	
	public void move(Random random)
	{
		if (this.isHeld) {return;}
		
		int speed = this.sortState == Bomb.sorted ? 2 : 5;
		
		switch (random.nextInt(5))
		{
			case 1: this.x+=random.nextInt(speed); break;
			case 2: this.x-=random.nextInt(speed); break;
			case 3: this.y+=random.nextInt(speed); break;
			case 4: this.y-=random.nextInt(speed); break;
		}
	}
}