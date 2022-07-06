package BloonShooting;

import java.awt.Color;

public interface Hittable 
{
	public byte[] getSprite();
	
	public byte[] getPopSprite();
	
	public Color[] getColors();
	
	public Color[] getPopColors();
	
	public int[] getOrigin();
	
	public boolean isAlive();
	
	public boolean isPopping();
	
	public void hit();
	
	public int getHittableID();
}