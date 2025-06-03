package insects;

public class Map 
{
	//WALLS
	public static class Wall
	{
		int topX, topY;
		int bottomX, bottomY;

		Wall(int tX, int tY, int bX, int bY)
		{this.topX = tX; this.topY = tY; this.bottomX = bX; this.bottomY = bY;}
	}

	public static final Wall[] walls = 
	{
			new Wall(245, 250, 245, 300), new Wall(245, 0, 245, 220), new Wall(0, 305, 245, 305), new Wall(200, 310, 200, 500),
			new Wall(200, 530, 500, 530), new Wall(250, 130, 500, 130), new Wall(530, 60, 530, 500), new Wall(100, 150, 100, 600),
			new Wall(100, 630, 600, 630), new Wall(600, 160, 600, 630), new Wall(50, 80, 450, 80)
	};
	
	public static final int[] food = {0, 146, 100, 459};
	public static final int[] water_1 = {45, 0, 200, 80};
	public static final int[] water_2 = {600, 156, 50, 479};
}