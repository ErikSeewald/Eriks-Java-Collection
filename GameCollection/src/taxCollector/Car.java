package taxCollector;

import ejcMain.util.EJC_Util.Direction;

public class Car 
{
	private int sizeTilesX = 3, sizeTilesY = 2;
	
	int i, j;
	private Direction direction;

	int start_i, start_j;
	Direction startDirection;
	
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
			sizeTilesX = 2;
			sizeTilesY = 3;
		}
		
		else
		{
			sizeTilesX = 3;
			sizeTilesY = 2;
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
	
	public int getSizeTilesX() {return sizeTilesX;}
	
	public int getSizeTilesY() {return sizeTilesY;}
	
	public Direction getDirection() {return direction;}
	
	public void respawn()
	{
		this.i = this.start_i; 
		this.j = this.start_j; 
		this.changeDirection(this.startDirection);
	}
	
	public void setStartingValues()
	{
		this.start_i = this.i;
		this.start_j = this.j;
		this.startDirection = this.direction;
	}
}
