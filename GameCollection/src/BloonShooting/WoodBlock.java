package BloonShooting;

import java.awt.Color;

public class WoodBlock extends Block
{
	private final static int HITTABLE_ID = 4;
	
	//SPRITE & COLOR
	static final Color Color1 = new Color(178,118,61);
	static final Color Color2 = new Color(148,105,65);
	static final Color Color3 = new Color(183,132,85);
	
	static final byte[] SPRITE = 
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
	
	static final byte[] BREAK_SPRITE = 
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
	
	private boolean POPPING;
	
	private int popAnimationFrame;

	WoodBlock(int[] origin, int pixelSize) 
	{super(origin, pixelSize); POPPING = false;}
	
	@Override
	public int getHittableID()
	{return HITTABLE_ID;}
	
	@Override
	public byte[] getSprite()
	{return SPRITE;}
	
	@Override
	public byte[] getReactSprite()
	{popAnimationFrame++; return BREAK_SPRITE; }
	
	@Override
	public Color[] getColors()
	{return new Color[] {Color1, Color2, Color3};}
	
	@Override
	public Color[] getReactColors()
	{return new Color[] {Color1, Color2, Color3};}
	
	@Override
	public void hit()
	{POPPING = true;}
	
	@Override
	public boolean isAlive()
	{return (popAnimationFrame < 3);}
	
	@Override
	public boolean isReacting()
	{return POPPING;}
}