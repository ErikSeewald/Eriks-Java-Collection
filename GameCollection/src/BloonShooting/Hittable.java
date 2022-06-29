package BloonShooting;

import java.awt.Color;

public interface Hittable 
{
	public byte[] getSprite();
	
	public Color[] getColors();
	
	public int[] getOrigin();
}
