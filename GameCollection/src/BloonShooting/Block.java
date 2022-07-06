package BloonShooting;

import java.awt.Color;

public class Block implements Hittable
{
	private final static int HITTABLE_ID = 2;
	
	//SPRITE & COLOR
	final static Color Color1 = new Color(223,223,223);
	final static Color Color2 = new Color(203,203,203);
	final static Color Color3 = new Color(185,185,185);
	
	private int PIXEL_SIZE;
	
	static final byte[] SPRITE = 
	{
			1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
			1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
			1,1,2,2,2,2,2,2,2,2,2,2,2,2,1,1,
			1,1,2,2,2,2,2,2,2,2,2,2,2,2,1,1,
			1,1,2,2,2,2,2,2,2,2,2,2,2,2,1,1,
			1,1,2,2,2,3,3,3,3,3,3,2,2,2,1,1,
			1,1,2,2,2,3,3,3,3,3,3,2,2,2,1,1,
			1,1,2,2,2,3,3,0,0,3,3,2,2,2,1,1,
			1,1,2,2,2,3,3,0,0,3,3,2,2,2,1,1,
			1,1,2,2,2,3,3,3,3,3,3,2,2,2,1,1,
			1,1,2,2,2,3,3,3,3,3,3,2,2,2,1,1,
			1,1,2,2,2,2,2,2,2,2,2,2,2,2,1,1,
			1,1,2,2,2,2,2,2,2,2,2,2,2,2,1,1,
			1,1,2,2,2,2,2,2,2,2,2,2,2,2,1,1,
			1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
			1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
	};
	
	//COORDINATES & VECTORS
	private int[] ORIGIN = new int[2];
	
	//INITIALIZATION
	Block(int[] origin, int pixelSize)
	{
		setPixelSize(pixelSize);
		ORIGIN[0] = origin[0]; 
		ORIGIN[1] = origin[1];
	}
		
	//COLOR
	public Color[] getColors()
	{return new Color[] {Color1, Color2, Color3};}
	
	public Color[] getReactColors()
	{return null;}
	
	//ORIGIN
	public int[] getOrigin()
	{return ORIGIN;}
	
	//PIXEL SIZE
	public int getPixelSize()
	{return PIXEL_SIZE;}
	
	public void setPixelSize(int pixelSize)
	{PIXEL_SIZE = pixelSize;}
	
	//SPRITE
	public byte[] getSprite()
	{return SPRITE;}
	
	public byte[] getReactSprite()
	{return null;}
	
	//ALIVE
	public boolean isAlive()
	{return true;}
	
	public boolean isReacting()
	{return false;}
	
	//HITTABLE
	@Override
	public void hit()
	{}

	@Override
	public int getHittableID() 
	{return HITTABLE_ID;}

}
