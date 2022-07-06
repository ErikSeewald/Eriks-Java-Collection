package BloonShooting;

import java.awt.Color;

public interface Hittable 
{
	public byte[] getSprite();
	
	public byte[] getReactSprite();
	
	public Color[] getColors();
	
	public Color[] getReactColors();
	
	public int[] getOrigin();
	
	public boolean isAlive();
	
	public boolean isReacting();
	
	public void hit();
	
	public int getHittableID();
}