package infdungeons;

public class Player 
{
	int x, y;
	int speed;
	int size;
	boolean isAttacking;
	private Room currentRoom;
	
	public static enum Direction
	{
		NORTH(0), EAST(90), SOUTH(180), WEST(270);
		
		int angle;
		Direction(int angle) {this.angle = angle;}
	};
	Direction direction;
	
	public void move(int x, int y)
	{
		if (x != 0)
		{
			this.x+=x;
			this.direction = x > 0 ? Direction.EAST : Direction.WEST;
		}
		
		else
		{
			this.y+=y;
			this.direction = y > 0 ? Direction.SOUTH : Direction.NORTH;
		}
	}
	
	public Room getRoom() {return currentRoom;}
	
	public void setRoom(Room room) {currentRoom = room;}
}
