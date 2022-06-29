package BloonShooting;

public class Levels_Databox
{
	public static final int LEVEL_COUNT = 1;
	public static final int CELL_COUNT = 1008;
	
	public static byte[] loadLevel(int levelNum) //loadLevel(1) -> loads LEVELS[0] which is Level 1
	{
		if (levelNum > 1 || levelNum > LEVEL_COUNT) {return null;}
		
		short[] level = LEVELS[levelNum-1];
		byte[] levelRAW = new byte[CELL_COUNT];
		
		short rawIndex = 0;
		
		for (int i = 0; i < level.length; i++)
		{
			if (level[i] < 0)
			{
				short tempIndex = rawIndex;
				
				while(rawIndex < (tempIndex + level[i]*-1))
				{levelRAW[rawIndex] = 0; rawIndex++;}
			}
			
			else
			{levelRAW[rawIndex] = (byte) level[i]; rawIndex++;}
		}
		return levelRAW;
	}
	
	private static final short[][] LEVELS =
	{
		//LEVEL 1
		{
				-270, 1,1,1,1,1,1, -35, 1,1,1,1,1,1,1,1, -34, 1,1,1,1,1,1,1,1, -34, 1,1,1,1,1,1,1,1, -34, 1,1,1,1,1,1,1,1,
				-35,  1,1,1,1,1,1, -522
		},	
	};
}
