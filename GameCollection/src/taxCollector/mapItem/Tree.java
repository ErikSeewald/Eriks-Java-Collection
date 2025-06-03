package taxCollector.mapItem;

import java.util.Random;

public class Tree extends MapItem
{
	public static final int size_tiles = 2;
	public static final float trunk_width_tiles = 0.5f;
	public static final float trunk_height_tiles = 1.5f;
	
	public static enum ColorStates
	{
		NORMAL, BRIGHTER, DARKER;
	};
	
	public ColorStates colorState;

	public Tree(int i, int j, Random random)
	{
		super(i, j);
		colorState = ColorStates.values()[random.nextInt(3)];
	}
}
