package bloonShoot.level;

import bloonShoot.BlS_Panel;
import bloonShoot.hittable.Balloon;
import bloonShoot.hittable.Block;
import bloonShoot.hittable.BoomBalloon;
import bloonShoot.hittable.BounceBlock;
import bloonShoot.hittable.Hittable;
import bloonShoot.hittable.HittableIDs;
import bloonShoot.hittable.WoodBlock;

public class LevelHandler 
{
	public class LoadOperations
	{
		public static final int nextLevel = 1;
		public static final int previousLevel = -1;
		public static final int reload = 0;
	}
	
	public final static int CELL_COUNT_X = 42;
	public final static int CELL_COUNT_Y = 24;
	public final static int CELL_COUNT = CELL_COUNT_X * CELL_COUNT_Y;
	
	//LEVEL
	private int levelNum = 1;
	private Hittable[] level = new Hittable[CELL_COUNT_X*CELL_COUNT_Y];
	
	private BlS_Panel panel;
	public LevelHandler(BlS_Panel panel) {this.panel = panel;}
	
	public void loadLevel(int levelNum)
	{	
		byte[] level_raw = Levels_Databox.loadLevel(levelNum);
		if (level_raw == null) {return;}
		
		this.levelNum = levelNum;
		int column = 1, row = 1, CELL_SIZE = panel.getCellSize();
		
		for (int i = 0; i < CELL_COUNT; i++)
		{
			if (column > CELL_COUNT_X) {row++; column = 1;}
			
			int[] origin = {column * CELL_SIZE, row * CELL_SIZE};
			
			switch (level_raw[i])
			{
				case 1: level[i] = new Balloon(origin); break;
				case 2: level[i] = new Block(origin); break;
				case 3: level[i] = new BounceBlock(origin); break;
				case 4: level[i] = new WoodBlock(origin); break;
				case 5: level[i] = new BoomBalloon(origin); break;
				default: level[i] = null;
			}
			column++;
		}
		System.gc();
	}
	
	public void boomCalc(int gridEdge)
	{
		int[] edges = BoomBalloon.getHitEdges(gridEdge);
		
		for (int edge : edges)
		{
			if (level[edge] == null) {continue;}
			
			if (level[edge].getHittableID() == HittableIDs.boomballoon && !level[edge].isReacting())
			{level[edge].hit(); boomCalc(edge);}
			
			level[edge].hit();
		}
	}
	
	public void changeLevel(int change) {loadLevel(levelNum + change);}
	
	public static boolean isHittable(int index, Hittable[] level)
	{
		if (index >= level.length || index < 0) {return false;}
		if (level[index] == null) {return false;}
		
		return level[index].isAlive();
	}
	
	public Hittable[] getLevel() {return level;}
}