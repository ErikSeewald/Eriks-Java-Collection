package infdungeons;

public class Player 
{
	private int size;
	private int sword_length;
	
	int x, y;
	int speed;
	
	public static final class Direction
	{
		final static int NORTH = 0, EAST = 90, SOUTH = 180, WEST = 270;
	};
	int direction;
	
	Player()
	{this.size = 10; this.sword_length = 10; this.x = 10; this.y = 10; this.speed = 10; direction = Direction.NORTH;}
	
	public void setSize(int size) 
	{
		this.size = size;
		this.sword_length = size / 2;
	}
	
	public int getSize() {return this.size;}
	
	public int getSwordLength() {return this.sword_length;}
}
