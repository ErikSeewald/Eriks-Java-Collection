package infdungeons;

public class Player 
{
	int x, y;
	int speed;
	int size;
	boolean isAttacking;
	private Room currentRoom;
	private DungeonHandler dungeonHandler;
	int key_count;
	
	Player(DungeonHandler dungeonHandler)
	{
		this.dungeonHandler = dungeonHandler;
		this.key_count = 2;
		this.x = 300; this.y = 300;
	}
	
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
		
		// DO NOT EXIT ROOM
		int[] room = dungeonHandler.getRoomRect();
		if (this.x < room[0] || this.x > room[0] + room[2] - this.size) {this.x -=x;}
		if (this.y < room[1] || this.y > room[1] + room[3] - this.size) {this.y -=y;}
	}
	
	public Room getRoom() {return currentRoom;}
	
	public void setRoom(Room room) {currentRoom = room;}
}
