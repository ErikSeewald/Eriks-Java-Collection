package BloonShooting;

public class Levels_Databox
{
	
	public static byte[] loadLevel(int levelNum)
	{
		short[] level = LEVELS[levelNum];
		byte[] levelRAW = new byte[1008];
		
		int levelIndex = 0, rawIndex = 0;
		
		for (int i = 0; i < level.length; i++)
		{
			if (level[levelIndex] < 0)
			{
				for (int j = rawIndex; j < rawIndex + level[levelIndex]; j++)
				{levelRAW[j] = 0; rawIndex++;}
			}
			
			else
			{levelRAW[rawIndex] = (byte) level[levelIndex]; rawIndex++;}
		}
		return levelRAW;
	}
	
	private static final short[][] LEVELS =
	{
		//LEVEL 1
		{
				-322, 1,1,1,1,1,1, -35, 1,1,1,1,1,1,1,1, -34, 1,1,1,1,1,1,1,1, -34, 1,1,1,1,1,1,1,1, -34, 1,1,1,1,1,1,1,1,
				-35,  1,1,1,1,1,1, -522
		},	
	};
}
