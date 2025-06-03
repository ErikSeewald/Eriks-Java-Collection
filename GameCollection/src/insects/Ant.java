package insects;
import java.awt.Color;
import java.util.Random;

public class Ant 
{
	int loc[] = {325,325};
	int size;
	Color color;

	Ant(int size)
	{this.size = size;}
	
	public void move(Random random)
	{
		int x = loc[0], y = loc[1];

		switch (random.nextInt(5)) //0 = no movement
		{ 
			case 1: x+=random.nextInt(10); break;
			case 2: x-=random.nextInt(10); break;
			case 3: y+=random.nextInt(10); break;
			case 4: y-=random.nextInt(10); break;
		}

		//COLLISION CHECK
		if (x > 0 && x < InsectsPanel.PANEL_SIZE && y > 0 && y < InsectsPanel.PANEL_SIZE)
		{
			if (!collision(x, y))
			{loc[0] = x; loc[1] = y;} 
		}
	}
	
	private boolean collision(int x, int y)
	{
		for (Map.Wall wall : Map.walls)
		{
			if
			((x > wall.topX-6 &&  x < wall.bottomX+6 && y > wall.topY-6 && y < wall.bottomY+6) 
			|| (x+size > wall.topX-5 &&  x+size < wall.bottomX+5 && y+size > wall.topY-5 &&  y+size < wall.bottomY+5))

			{return true;}
		}
		return false;
	}
	
	public int gettingFood()
	{
		if ((loc[0] > Map.food[0] && loc[0] < Map.food[0] + Map.food[2] 
				&& loc[1] > Map.food[1] && loc[1] < Map.food[1] + Map.food[3])) 
		{return 1;}

		return 0;
	}

	public int gettingWater()
	{
		if ((loc[0] > Map.water_1[0] && loc[0] < Map.water_1[0] + Map.water_1[2] 
				&& loc[1] > Map.water_1[1] && loc[1] < Map.water_1[1] + Map.water_1[3])

				|| loc[0] > Map.water_2[0] && loc[0] < Map.water_2[0] + Map.water_2[2] 
						&& loc[1] > Map.water_2[1] && loc[1] < Map.water_2[1] + Map.water_2[3])
		{return 1;}

		return 0;
	}
}