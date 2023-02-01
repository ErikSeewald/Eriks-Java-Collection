package BloonShoot;

import java.awt.Color;

public class BoomBalloon extends Balloon
{

	private final static int HITTABLE_ID = 5;
	
	//SPRITE
	final static Color Color1 = new Color(72,71,71);
	final static Color Color2 = new Color(92,92,92);
	final static Color Color3 = new Color(126,126,126);
	
	static final byte[] SPRITE = 
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
	
	BoomBalloon(int[] origin, int pixelSize) 
	{super(origin, pixelSize);}
	
	@Override
	public int getHittableID()
	{return HITTABLE_ID;}
	
	@Override
	public byte[] getSprite()
	{return SPRITE;}
	
	//COLORS
	@Override
	public Color[] getColors()
	{return new Color[] {Color1, Color2, Color3};}
	
	@Override
	public Color[] getReactColors()
	{return new Color[] {Color1, Color2, Color3};}
	
	public static int[] getHitEdges(int edge)
	{
		int[] edges = new int[24];
		
		edges[0] = edge-1; edges[1] = edge-2;
		edges[2] = edge+1; edges[3] = edge+2;
		edges[4] = edge-42-1; edges[5] = edge-42-2;
		edges[6] = edge-84-1; edges[7] = edge-84-2;
		edges[8] = edge+42-1; edges[9] = edge+42-2;
		edges[10] = edge+84-1; edges[11] = edge+84-2;
		edges[12] = edge-42+1; edges[13] = edge-42+2;
		edges[14] = edge-84+1; edges[15] = edge-84+2;
		edges[16] = edge+42+1; edges[17] = edge+42+2;
		edges[18] = edge+84+1; edges[19] = edge+84+2;
		edges[20] = edge-42; edges[21] = edge-84;
		edges[22] = edge+42; edges[23] = edge+84;
		
		return edges;
	}

}
