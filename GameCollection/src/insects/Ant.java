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
		for (InsectsMap.Wall wall : InsectsMap.walls)
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
		if ((loc[0] > 0 && loc[0] < 100 && loc[1] > 145 && loc[1] < 605)) 
		{return 1;}
		
		return 0;
	}
	
	public int gettingWater()
	{
		if ((loc[0] > 45 && loc[0] < 245 && loc[1] > 0 && loc[1] < 80)
			||loc[0] > 600 && loc[0] < 650 && loc[1] > 155 && loc[1] < 635) 
		{return 1;}
		
		return 0;
	}
}