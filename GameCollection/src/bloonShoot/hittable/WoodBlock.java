package bloonShoot.hittable;

import java.awt.Color;

public class WoodBlock extends Block
{
	private final static int HITTABLE_ID = HittableIDs.woodblock;
	
	//SPRITE & COLOR
	private static final Color[] sprite_palette =
	{new Color(178,118,61), new Color(148,105,65), new Color(183,132,85)};
	
	private static final byte[] SPRITE = 
	{
				1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,	
				1,2,2,2,2,2,2,1,1,2,2,2,2,2,2,1,
				1,1,1,3,3,3,2,2,2,2,3,3,3,1,1,1,
				1,2,2,2,2,2,2,1,1,2,2,2,2,2,2,1,
				1,1,1,2,3,3,2,1,1,2,3,3,2,1,1,1,
				1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
				1,1,1,3,3,3,2,2,2,2,3,3,3,1,1,1,
				1,1,1,3,3,3,2,2,2,2,3,3,3,1,1,1,
				1,2,2,2,2,2,2,3,3,2,2,2,2,2,2,1,
				1,1,1,1,3,3,2,2,2,2,3,3,1,1,1,1,
				1,2,2,2,2,2,2,1,1,2,2,2,2,2,2,1,
				1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
				1,2,2,2,2,2,2,1,1,2,2,2,2,2,2,1,
				1,1,1,3,3,3,2,2,2,2,3,3,3,1,1,1,
				1,2,2,2,2,2,2,1,1,2,2,2,2,2,2,1,
				1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
	};
	
	private static final byte[] BREAK_SPRITE = 
	{
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				0,2,0,0,3,2,0,0,0,0,3,3,0,0,0,0,
				0,2,2,0,3,2,1,1,0,0,0,1,2,0,0,0,
				0,0,1,0,0,1,2,1,3,3,0,1,2,2,0,0,
				0,0,1,3,0,0,0,1,0,3,0,1,3,2,2,0,
				0,0,1,3,0,0,0,0,3,0,0,1,3,2,2,0,
				0,0,0,2,2,3,3,1,2,3,0,3,0,2,0,0,
				0,0,2,2,0,0,1,2,2,3,0,0,2,0,0,0,
				0,0,3,0,1,1,1,2,2,3,3,3,3,3,3,0,
				0,3,1,1,0,3,0,3,3,0,0,1,1,1,0,0,
				0,0,0,1,1,0,0,1,0,3,0,0,3,2,2,0,
				0,2,0,3,0,0,1,0,0,2,1,1,0,0,0,0,
				0,2,3,3,0,1,1,3,0,2,1,1,1,0,0,0,
				0,2,0,0,3,3,0,0,0,2,3,1,1,0,3,0,
				0,2,0,3,0,0,0,0,3,0,0,0,1,0,0,0,
				0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
	};
	
	private boolean breaking;	
	private int breakAnimationFrame;

	public WoodBlock(int[] origin) 
	{super(origin); breaking = false;}
	
	public int getHittableID()
	{return HITTABLE_ID;}
	
	public byte[] getSprite()
	{return SPRITE;}
	
	public byte[] getReactSprite()
	{breakAnimationFrame++; return BREAK_SPRITE; }
	
	public Color[] getColors()
	{return sprite_palette;}
	
	public Color[] getReactColors()
	{return sprite_palette;}
	
	public void hit()
	{breaking = true;}
	
	public boolean isAlive()
	{return (breakAnimationFrame < 3);}
	
	public boolean isReacting()
	{return breaking;}
}