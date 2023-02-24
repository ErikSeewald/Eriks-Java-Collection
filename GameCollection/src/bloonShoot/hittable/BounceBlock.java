package bloonShoot.hittable;

import java.awt.Color;

public class BounceBlock extends Block
{
	private final static int HITTABLE_ID = HittableIDs.bounceblock;
	
	//SPRITE & COLOR
	private static final Color[] sprite_palette =
	{new Color(24,26,34), new Color(40,42,50), new Color(41,51,62)};
	
	private static final Color[] flicker_palette =
	{new Color(24,26,34), new Color(110,112,120), new Color(110,112,120)};
	
	private static final byte[] SPRITE = 
	{
				1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
				1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
				1,1,2,2,2,2,1,1,1,1,2,2,2,2,1,1,
				1,1,2,3,3,2,1,1,1,1,2,3,3,2,1,1,
				1,1,2,3,3,2,1,1,1,1,2,3,3,2,1,1,
				1,1,2,2,2,2,1,1,1,1,2,2,2,2,1,1,
				1,1,1,1,1,1,2,2,2,2,1,1,1,1,1,1,
				1,1,1,1,1,1,2,3,3,2,1,1,1,1,1,1,
				1,1,1,1,1,1,2,3,3,2,1,1,1,1,1,1,
				1,1,1,1,1,1,2,2,2,2,1,1,1,1,1,1,
				1,1,2,2,2,2,1,1,1,1,2,2,2,2,1,1,
				1,1,2,3,3,2,1,1,1,1,2,3,3,2,1,1,
				1,1,2,3,3,2,1,1,1,1,2,3,3,2,1,1,
				1,1,2,2,2,2,1,1,1,1,2,2,2,2,1,1,
				1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
				1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
	};
	
	private boolean flickering = false;
	private short flickerAnimationFrame;

	public BounceBlock(int[] origin) 
	{super(origin); flickerAnimationFrame = 0;}
	
	public int getHittableID()
	{return HITTABLE_ID;}
	
	public byte[] getSprite()
	{return SPRITE;}
	
	public byte[] getReactSprite()
	{return SPRITE;}
	
	public Color[] getColors()
	{return sprite_palette;}
	
	public Color[] getReactColors()
	{flickerAnimationFrame++; return flicker_palette;}
	
	public void hit()
	{flickering = true;}
	
	public boolean isReacting()
	{
		if (flickerAnimationFrame > 10) {flickering = false; flickerAnimationFrame = 0;}
		return flickering;
	}
}