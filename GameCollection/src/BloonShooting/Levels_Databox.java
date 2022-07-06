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
			int insert = 0, amount = level[i];
			
			if (level[i] < 0) {amount*=-1;}
			else {insert = (level[i] / 100)+1;} //assigns the number to be inserted into levelRAW per the norm specified at the LEVELS array
			
			int fillUpTo = rawIndex + amount;
			if (insert > 1) {fillUpTo-= (insert-1)*100;} //removes the ID indicator digit from the amount that should be filled in
			
			while(rawIndex < fillUpTo ) //fill the levelRAW for the in level[i] specified amount of indices
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
			-222, 107, -14, 102, -19, 107, -7, 1, -6, 102, -19, 102, -12, 1, -6, 102, -19, 102, -11, 3, -5, 102, -19, 102, -10, 5, -4, 102, 
			-19, 102, -8, 9, -2, 102, -19, 102, -10, 5, -4, 102, -32, 3, -5, 102, -33, 1, -6, 102, -33, 1, -2, 106, -25, 102, -9, 106, 
			-25, 102, -40, 102, -40, 102, -40, 102, -40, 106, -36, 106,
		},
	};
}
