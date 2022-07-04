package BloonShooting;

public class Levels_Databox
{
	public static final int LEVEL_COUNT = 4;
	public static final int CELL_COUNT = 1008;
	
	public static byte[] loadLevel(int levelNum) //loadLevel(1) -> loads LEVELS[0] which is Level 1
	{
		if (levelNum < 1 || levelNum > LEVEL_COUNT) {return null;}
		
		short[] level = LEVELS[levelNum-1];		//compressed data
		byte[] levelRAW = new byte[CELL_COUNT]; //raw data
		
		int rawIndex = 0;
		
		for (int i = 0; i < level.length; i++)
		{
			int insert = 0, amount = level[i], tempIndex = rawIndex;
			
			if (level[i] < 0) {amount*=-1;}
			else {insert = (level[i] / 100)+1;} //assigns the number to be inserted into levelRAW per the norm specified at the LEVELS array
				
			while(rawIndex < (tempIndex + amount)) //fill the levelRAW for the in level[i] specified amount of indices
			{levelRAW[rawIndex] = (byte) insert; rawIndex++;}	
		}
		return levelRAW;
	}
	
	//-30 -> 30 zeros | 30 -> 30 ones | 130 -> 30 twos
	private static final short[][] LEVELS =
	{
		//LEVEL 1
		{
			-225, 17, -25, 17, -25, 17, -25, 17, -25, 17, -25, 17, -25, 17, -25, 17, -25, 17, -25, 17, -25, 17, -25, 17, -25,
		},
			
		//LEVEL 2
		{
			-265, 11, -31, 11, -31, 11, -31, 11, -31, 11, -31,  11, -80, 11, -31, 11, -31, 11, -31, 11, -31, 11, -31,  11,
		},
		
		//LEVEL 3
		{
			-154, 3, -37, 7, -34, 9, -32, 11, -23, 3, -4, 13, -21, 5, -2, 15, -19, 7, -1, 15, -17, 1, -1, 24, -15, 27, -16, 1, -1, 
			24, -18, 7, -1, 15, -20, 5, -2, 15, -21, 3, -4, 13, -30, 11, -32, 9, -34, 7, -37, 3
				
		},
		
		//LEVEL 4
		{
			-222, 7, -14, 2, -19, 7, -7, 1, -6, 2, -19, 2, -12, 1, -6, 2, -19, 2, -11, 3, -5, 2, -19, 2, -10, 5, -4, 2, -19, 2, -8, 9, 
			-2, 2, -19, 2, -10, 5, -4, 2, -32, 3, -5, 2, -33, 1, -6, 2, -33, 1, -2, 6, -25, 2, -9, 6, -25, 2, -40, 2, -40, 2, -40, 2, 
			-40, 6, -36, 6,
		},
	};
}
