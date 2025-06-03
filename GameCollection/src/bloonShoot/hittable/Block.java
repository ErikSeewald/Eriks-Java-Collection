package bloonShoot.hittable;

import java.awt.Color;

public class Block implements Hittable
{
	private final static int HITTABLE_ID = HittableIDs.block;
	
	//SPRITE & COLOR
	private final static Color[] sprite_palette = 
	{new Color(223,223,223), new Color(203,203,203), new Color(185,185,185)};
	
	private static final byte[] SPRITE = 
	{
			1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
			1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
			1,1,2,2,2,2,2,2,2,2,2,2,2,2,1,1,
			1,1,2,2,2,2,2,2,2,2,2,2,2,2,1,1,
			1,1,2,2,2,2,2,2,2,2,2,2,2,2,1,1,
			1,1,2,2,2,3,3,3,3,3,3,2,2,2,1,1,
			1,1,2,2,2,3,3,3,3,3,3,2,2,2,1,1,
			1,1,2,2,2,3,3,3,3,3,3,2,2,2,1,1,
			1,1,2,2,2,3,3,3,3,3,3,2,2,2,1,1,
			1,1,2,2,2,3,3,3,3,3,3,2,2,2,1,1,
			1,1,2,2,2,3,3,3,3,3,3,2,2,2,1,1,
			1,1,2,2,2,2,2,2,2,2,2,2,2,2,1,1,
			1,1,2,2,2,2,2,2,2,2,2,2,2,2,1,1,
			1,1,2,2,2,2,2,2,2,2,2,2,2,2,1,1,
			1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
			1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
	};
	
	private int[] ORIGIN = new int[2];
	
	public Block(int[] origin)
	{
		ORIGIN[0] = origin[0]; 
		ORIGIN[1] = origin[1];
	}
		
	public Color[] getColors()
	{return sprite_palette;}
	
	public Color[] getReactColors()
	{return null;}
	
	public int[] getOrigin()
	{return ORIGIN;}

	public byte[] getSprite()
	{return SPRITE;}
	
	public byte[] getReactSprite()
	{return null;}
	
	public boolean isAlive()
	{return true;}
	
	public boolean isReacting()
	{return false;}
	
	public void hit() {}

	public int getHittableID() 
	{return HITTABLE_ID;}
}