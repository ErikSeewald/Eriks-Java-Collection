package BloonShooting;

public class Levels_Databox
{
	public static final int LEVEL_COUNT = 2;
	public static final int CELL_COUNT = 1008;
	
	public static byte[] loadLevel(int levelNum) //loadLevel(1) -> loads LEVELS[0] which is Level 1
	{
		if (levelNum < 1 || levelNum > LEVEL_COUNT) {return null;}
		
		short[] level = LEVELS[levelNum-1];		//compressed data
		byte[] levelRAW = new byte[CELL_COUNT]; //raw data
		
		short rawIndex = 0;
		
		for (int i = 0; i < level.length; i++)
		{
			if (level[i] < 0) //-> sub zero mean the number announces the amount of following zeros (number *-1 later to remove the "-" identifier)
			{
				short tempIndex = rawIndex;
				
				while(rawIndex < (tempIndex + level[i]*-1)) //fill the levelRAW with zeros for the in level[i] specified amount of indices
				{levelRAW[rawIndex] = 0; rawIndex++;}
			}
			
			else
			{levelRAW[rawIndex] = (byte) level[i]; rawIndex++;} //fill the id of the type of hittable object from level into the levelRAW array
		}
		return levelRAW;
	}
	
	private static final short[][] LEVELS =
	{
		//LEVEL 1
		{
				-265, 1,1,1,1,1,1,1,1,1,1,1, -31, 1,1,1,1,1,1,1,1,1,1,1, -31, 1,1,1,1,1,1,1,1,1,1,1,
				-31, 1,1,1,1,1,1,1,1,1,1,1, -31, 1,1,1,1,1,1,1,1,1,1,1, -31,  1,1,1,1,1,1,1,1,1,1,1, -80,
				1,1,1,1,1,1,1,1,1,1,1, -31, 1,1,1,1,1,1,1,1,1,1,1, -31, 1,1,1,1,1,1,1,1,1,1,1,
				-31, 1,1,1,1,1,1,1,1,1,1,1, -31, 1,1,1,1,1,1,1,1,1,1,1, -31,  1,1,1,1,1,1,1,1,1,1,1,
		},
		
		//LEVEL 2
		{
				-154, 1,1,1, -37, 1,1,1,1,1,1,1, -34, 1,1,1,1,1,1,1,1,1, -32, 1,1,1,1,1,1,1,1,1,1,1, -23, 1,1,1, -4, 1,1,1,1,1,1,1,1,1,1,1,1,1,
				-21, 1,1,1,1,1, -2, 1,1,1,1,1,1,1,1,1,1,1,1,1,1,1, -19, 1,1,1,1,1,1,1, -1, 1,1,1,1,1,1,1,1,1,1,1,1,1,1,1, -17, 1, -1,
				1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1, -15, 1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1
				-17, 1, -1, 1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1, -18, 1,1,1,1,1,1,1, -1, 1,1,1,1,1,1,1,1,1,1,1,1,1,1,1, -20, 1,1,1,1,1,
				-2, 1,1,1,1,1,1,1,1,1,1,1,1,1,1,1, -21, 1,1,1, -4, 1,1,1,1,1,1,1,1,1,1,1,1,1, -30, 1,1,1,1,1,1,1,1,1,1,1, -32, 1,1,1,1,1,1,1,1,1,
				-34, 1,1,1,1,1,1,1, -37, 1,1,1
				
		},	
	};
}
