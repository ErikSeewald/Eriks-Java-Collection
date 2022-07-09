package BloonShooting;

public class Levels_Databox
{
	public static final int LEVEL_COUNT = 10;
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
		
		//LEVEL 5
		{
			-100, 201, -41, 201, 6, -36, 6, -36 , 6, -36, 6, -8, 201, -27, 6, -8, 201, -26, 201, 6, -8, 201, -26, 201, 6, -8, 201, -26, 
			201, -14, 201, -26, 201, -14, 201, -30, 6, 5, 201, -30, 6, 5, 201, -30, 6, 5, 201, -30, 6, 5, 201, -41, 201, -41, 201, -32, 210
		},
		
		//LEVEL 6
		{
			-226, 101, -37, 4, 101, -37, 4, 101, -37, 4, 101, -35, 107, -44, 4, -38, 4, -38, 4, -8, 4, -38, 4, -24, 207, -7, 4, -65, 101, 
			-13, 207, -21, 101, -41, 101, -5, 4, -32, 101, 101, -5, 4, -32, 101, 101, -5, 4, -32, 101,
		},
		
		//LEVEL 7
		{
			-275, 201, 3, 201, -37, 201, 3, 201, -37, 201, 3, 201, -37, 201, 3, 201,  -37, 201, 3, 201, -37, 201, 3, 201, -37, 201, 3, 201,
			 -37, 201, 3, 201, -37, 201, 3, 201, -37, 201, 3, 201, -37, 201, 3, 201, -37, 201, 3, 201, -37, 201, 3, 201, -37, 201, 3, 201
		},
		
		//LEVEL 8
		{
			-302, 6, -35, 6, -35,  6, -35,  6, -158, 316 		
		},
		
		//LEVEL 9
		{
			-182, 116, -67, 301, -15, 201, -25, 301, -1, 3, -11, 201, -25, 301, -1, 3, -11, 201, -25, 301, -1, 3, -7, 3, -1, 201, -25, 301,
			-11, 3, -1, 201, -25, 301, -5, 3, -3, 3, -1, 201, -26, -5, 3, -7, 201, -26, 204, 305, 206, -29, 201, -41, 201, -41, 201, -1, 4,
			-36, 201, -1, 4, -36, 201, -1, 4, -36, 206
		},
		
		//LEVEL 9
		{
			-310, 5, -37, 5, -37, 2, 401, 2, -37, 5, -37, 5, -46, 5, -37, 5, -37, 2, 401, 2, -37, 5, -37, 5
		},

	};
}
