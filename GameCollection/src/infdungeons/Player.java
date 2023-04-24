package infdungeons;

public class Player 
{
	int x, y;
	int speed;
	private int size;
	int hp;
	boolean isAttacking;
	private Room currentRoom;
	private DungeonHandler dungeonHandler;
	int key_count;
	int bomb_count;
	int bomb_radius;
	
	private int invincible_time;
	private int key_animation;
	private int bomb_obtain_animation;
	boolean isAlive;
	
	public static final int attack_dmg = 5;
	
	Player(DungeonHandler dungeonHandler, Room room, int size)
	{
		this.dungeonHandler = dungeonHandler;
		this.respawn(room);	
		this.setSize(size);
	}
	
	public void respawn(Room room)
	{
		this.currentRoom = room;
		this.key_count = 0;
		this.hp = 15;
		this.invincible_time = 0;
		this.isAlive = true;
		
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
		boolean movingAlongX = false;
		if (x != 0)
		{
			movingAlongX = true;
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
		
		// BLOCK COLLISION
		int[] tile_values = dungeonHandler.getTileValues();
		int tile_x = (this.x - tile_values[0]) / tile_values[2];
		int tile_y = (this.y - tile_values[1]) / tile_values[2];
		
		if (checkAllBlocksForCollision(tile_values, tile_x, tile_y))
		{
			if (movingAlongX)
			{
				this.x -= x;
				slideAlongTile(tile_values, tile_x, tile_y, 0, y);
			}
			
			else 
			{
				this.y -= y;
				slideAlongTile(tile_values, tile_x, tile_y, x, 0);
			}
		}	
	}
	
	private void slideAlongTile(int[] tile_values, int tile_x, int tile_y, int x, int y)
	{
		this.x += x;
		this.y += y;
		
		if (checkAllBlocksForCollision(tile_values, tile_x, tile_y))
		{
			this.x -= x;
			this.y -= y;
		}
	}
	
	private boolean checkAllBlocksForCollision(int[] tile_values, int tile_x, int tile_y)
	{
		for (int i = 0; i < Room.tiles_x; i++)
		{
			for (int j = 0; j < Room.tiles_y; j++)
			{
				// to far away from tile for collision
				if (i + 1 < tile_x || i - 1 > tile_x || j + 1 < tile_y || j - 1 > tile_y) {continue;}
				
				if (this.currentRoom.tiles[i][j] != Room.block_tile) {continue;}
				
				else if (isInsideBlock(i,j, tile_values))
				{return true;}
			}
		}
			
		return false;
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
	
	public void getHit(int damage)
	{
		if (this.invincible_time > 0) {return;}
		
		this.hp -= damage;
		if (this.hp <= 0) {this.isAlive = false; return;}
		
		this.invincible_time = 10;
	}
	
	public void obtainKey()
	{
		this.key_count++;
		this.key_animation = 10;
	}
	
	public void useKey()
	{
		this.key_count--;
		this.key_animation = 10;
	}
	
	public void obtainBomb()
	{
		this.bomb_count++;
		this.bomb_obtain_animation = 10;
	}
	
	public void updateTimers()
	{
		if (this.invincible_time > 0) {this.invincible_time--;}
		if (this.key_animation > 0) {this.key_animation--;}
		if (this.bomb_obtain_animation > 0) {this.bomb_obtain_animation--;}
	}
	
	public boolean isInvincible() {return this.invincible_time > 0;}
	
	public boolean isInKeyAnimation() {return this.key_animation > 0;}
	
	public boolean isInBombObtainAnimation() {return this.bomb_obtain_animation > 0;}
	
	public void setSize(int size)
	{
		this.size = size;
		this.bomb_radius = size * 2;
	}
	
	public int getSize() {return this.size;}
}
