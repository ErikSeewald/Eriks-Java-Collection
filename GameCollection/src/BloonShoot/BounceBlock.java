package BloonShoot;

import java.awt.Color;

public class BounceBlock extends Block
{
	private final static int HITTABLE_ID = 3;
	
	private boolean POPPING = false;
	private short flickerAnimationFrame;
	
	//SPRITE & COLOR
	static final Color Color1 = new Color(24,26,34);
	static final Color Color2 = new Color(40,42,50);
	static final Color Color3 = new Color(41,51,62);
	
	static final Color ReactColor2 = new Color(110,112,120);
	static final Color ReactColor3 = new Color(111,121,132);
	
	static final byte[] SPRITE = 
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

	BounceBlock(int[] origin, int pixelSize) 
	{super(origin, pixelSize); flickerAnimationFrame = 0;}
	
	@Override
	public int getHittableID()
	{return HITTABLE_ID;}
	
	@Override
	public byte[] getSprite()
	{return SPRITE;}
	
	@Override
	public byte[] getReactSprite()
	{return SPRITE;}
	
	@Override
	public Color[] getColors()
	{return new Color[] {Color1, Color2, Color3};}
	
	@Override
	public Color[] getReactColors()
	{flickerAnimationFrame++; return new Color[] {Color1, ReactColor2, ReactColor3};}
	
	@Override
	public void hit()
	{POPPING = true;}
	
	@Override
	public boolean isReacting()
	{
		if (flickerAnimationFrame > 10) {POPPING = false; flickerAnimationFrame = 0;}
		return POPPING;
	}
}
