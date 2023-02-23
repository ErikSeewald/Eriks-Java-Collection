package bloonShoot.level;

import bloonShoot.BlS_Panel;
import bloonShoot.hittable.Balloon;
import bloonShoot.hittable.Block;
import bloonShoot.hittable.BoomBalloon;
import bloonShoot.hittable.BounceBlock;
import bloonShoot.hittable.Hittable;
import bloonShoot.hittable.WoodBlock;

public class LevelHandler 
{
	public class LoadOperations
	{
		public static final int nextLevel = 1;
		public static final int previousLevel = -1;
		public static final int reload = 0;
	}
	
	public final static int CELL_COUNT_X = 42; //for the indices of the cells
	public final static int CELL_COUNT_Y = 24;
	public final static int CELL_COUNT = CELL_COUNT_X * CELL_COUNT_Y;
	
	int levelNum = 1;
	public byte[] levelRAW = new byte[CELL_COUNT]; //array containing the id of the element at each position
	
	public Hittable[] level = new Hittable[CELL_COUNT_X*CELL_COUNT_Y]; //array containing the actual element at each position
	
	private BlS_Panel panel;
	
	public LevelHandler(BlS_Panel panel) {this.panel = panel;}
	
	public void changeLevel(int change) {loadLevel(levelNum + change);}
	
	public void loadLevel(int levelNum)
	{	
		if(panel.shotHandler.shot.isRunning()) {return;}
		int CELL_SIZE = panel.CELL_SIZE;
		
		byte[] levelRAWTEMP = Levels_Databox.loadLevel(levelNum);

		if (levelRAWTEMP == null) {return;}
		
		levelRAW = levelRAWTEMP;
		
		this.levelNum = levelNum;
		
		level = new Hittable[CELL_COUNT_X*CELL_COUNT_Y];
		
		int column = 1, row = 1;
		
		for (int i = 0; i < CELL_COUNT; i++)
		{
			if (column > CELL_COUNT_X) {row++; column = 1;} //go to the next row, reset to the first column
			
			switch (levelRAW[i])
			{
				case 1: level[i] = new Balloon(new int[] {column*CELL_SIZE,row*CELL_SIZE});
				break;
				case 2: level[i] = new Block(new int[] {column*CELL_SIZE,row*CELL_SIZE}, panel.pixelSize);
				break;
				case 3: level[i] = new BounceBlock(new int[] {column*CELL_SIZE,row*CELL_SIZE}, panel.pixelSize);
				break;
				case 4: level[i] = new WoodBlock(new int[] {column*CELL_SIZE,row*CELL_SIZE}, panel.pixelSize);
				break;
				case 5: level[i] = new BoomBalloon(new int[] {column*CELL_SIZE,row*CELL_SIZE});
				break;
			}
			column++;
		}
		System.gc();
	}
}
