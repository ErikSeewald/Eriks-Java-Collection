package bomb_sorting;

public class Bomb 
{
	public static final byte BLACK = 1;
	public static final byte RED = 2;
	
	public static int size = 20; //changed in Sort_Panel.initSizes
	
	public int timer = 9;
	public boolean isSorted = false;
	
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
}
