package taxCollector;

import Main.EJC_Util.Direction;

public class Car 
{
	private int size_tiles_x = 3, size_tiles_y = 2;
	
	int i, j;
	int start_i, start_j;
	private Direction direction;
	Direction start_direction;
	
	Car(int i, int j, Direction direction)
	{
		this.i = i;
		this.j = j;
		this.changeDirection(direction);
	}
	
	public void changeDirection(Direction direction)
	{
		this.direction = direction;
		
		if (direction == Direction.NORTH || direction == Direction.SOUTH)
		{
			size_tiles_x = 2;
			size_tiles_y = 3;
		}
		
		else
		{
			size_tiles_x = 3;
			size_tiles_y = 2;
		}
	}
	
	public void move()
	{
		switch (this.direction)
		{
			case NORTH: this.j--; break;
			case SOUTH: this.j++; break;
			case EAST: this.i++; break;
			case WEST: this.i--; break;
		}
	}
	
	public int getSizeTilesX() {return size_tiles_x;}
	
	public int getSizeTilesY() {return size_tiles_y;}
	
	public Direction getDirection() {return direction;}
}
