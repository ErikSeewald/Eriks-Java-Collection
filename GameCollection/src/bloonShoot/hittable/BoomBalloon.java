package bloonShoot.hittable;
import java.awt.Color;
import bloonShoot.level.LevelHandler;

public class BoomBalloon extends Balloon
{
	private final static int HITTABLE_ID = HittableIDs.boomballoon;
	
	//SPRITE
	private static final Color[] sprite_palette =
	{new Color(72,71,71), new Color(92,92,92), new Color(126,126,126)};
	
	private static final byte[] SPRITE = 
	{
			0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,
			0,0,0,0,1,1,1,3,3,1,1,1,0,0,0,0,
			0,0,0,1,1,2,2,2,2,2,2,1,1,0,0,0,
			0,0,0,1,2,2,2,2,2,2,2,2,1,0,0,0,
			0,0,0,1,2,3,2,2,2,2,3,2,1,0,0,0,
			0,0,1,1,2,2,1,2,2,1,2,2,1,1,0,0,
			0,0,1,3,2,2,2,3,1,2,2,2,3,1,0,0,
			0,0,1,3,2,2,2,1,3,2,2,2,3,1,0,0,
			0,0,1,1,2,2,1,2,2,1,2,2,1,1,0,0,
			0,0,1,1,2,3,2,2,2,2,3,2,1,1,0,0,
			0,0,0,1,2,2,2,2,2,2,2,2,1,0,0,0,
			0,0,0,1,1,2,2,2,2,2,2,1,1,0,0,0,
			0,0,0,0,1,2,2,2,2,2,2,1,0,0,0,0,
			0,0,0,0,0,1,1,3,3,1,1,0,0,0,0,0,
			0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,
	};
	
	public BoomBalloon(int[] origin) 
	{super(origin);}
	
	public int getHittableID()
	{return HITTABLE_ID;}
	
	public byte[] getSprite()
	{return SPRITE;}
	
	public Color[] getColors()
	{return sprite_palette;}
	
	public Color[] getReactColors()
	{return sprite_palette;}
	
	public static int[] getHitEdges(int edge)
	{
		int width = LevelHandler.CELL_COUNT_X;
		
		return new int[]
		{
			edge - 1, edge -2, edge+1, edge +2, edge-width-1, edge-width-2, edge-width+1, edge-width+2, 
			edge+width-1, edge+width-2, edge+width+1, edge+width+2, edge-width*2-1, edge-width*2-2, 
			edge-width*2+1, edge-width*2+2, edge+width*2-1, edge+width*2-2, edge+width*2+1, edge+width*2+2,
			edge+width, edge-width, edge+width*2, edge-width*2
		};
	}
}