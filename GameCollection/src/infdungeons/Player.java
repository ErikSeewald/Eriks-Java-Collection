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
		this.key_count = 0;
		
		// SPAWN POINT
		int[] entrance_door = dungeonHandler.getDoors()[2];
		this.x = entrance_door[0];
		this.y = entrance_door[1] - dungeonHandler.getTileValues()[2] / 3; // offset to not glitch into wall
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
			y = 0; // to indicate that we picked x later
			this.x+=x;
			this.direction = x > 0 ? Direction.EAST : Direction.WEST;
		}
		
		else
		{
			x = 0;
			this.y+=y;
			this.direction = y > 0 ? Direction.SOUTH : Direction.NORTH;
		}
		
		// DO NOT EXIT ROOM
		int[] room = dungeonHandler.getRoomRect();
		if (this.x < room[0] || this.x > room[0] + room[2] - this.size) {this.x -=x;}
		if (this.y < room[1] || this.y > room[1] + room[3] - this.size) {this.y -=y;}
		
		// TILE ARRAY COLLISION
		int[] tile_values = dungeonHandler.getTileValues();
		int tile_x = (this.x - tile_values[0]) / tile_values[2];
		int tile_y = (this.y - tile_values[1]) / tile_values[2];
		
		for (int i = 0; i < Room.tiles_x; i++)
		{
			for (int j = 0; j < Room.tiles_y; j++)
			{
				if (this.currentRoom.tiles[i][j] == Room.empty_tile) {continue;}
				
				// to far away from tile for collision
				if (i + 1 < tile_x || i - 1 > tile_x || j + 1 < tile_y || j - 1 > tile_y) {continue;}
				
				else if (isInsideBlock(i,j, tile_values))
				{
					if (x != 0) {this.x -= x;}
					else {this.y -= y;}
				}
			}
		}
		
	}
	
	private boolean isInsideBlock(int i, int j, int[] tile_values)
	{
		int bSize = tile_values[2];
		int bX = tile_values[0] + i * bSize;
		int bY = tile_values[1] + j * bSize;
		
		return (
				(this.x > bX &&  this.x < bX+bSize && this.y > bY &&  this.y < bY+bSize)
			||	(this.x+size > bX &&  this.x+size < bX+bSize && this.y+size > bY &&  this.y+size < bY+bSize)
			||	(this.x+size > bX &&  this.x+size < bX+bSize && this.y > bY &&  this.y < bY+bSize)
			||	(this.x > bX &&  this.x < bX+bSize && this.y+size > bY &&  this.y+size < bY+bSize)
		);
	}
	
	public Room getRoom() {return currentRoom;}
	
	public void setRoom(Room room) {currentRoom = room;}
}
