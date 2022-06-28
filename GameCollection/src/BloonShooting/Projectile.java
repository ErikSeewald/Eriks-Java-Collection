package BloonShooting;

import java.awt.Color;

public class Projectile 
{
	final static Color Color1 = new Color(145,170,234);
	final static Color Color2 = new Color(138,154,194);
	final static Color Color3 = new Color(94,111,157);
	final static Color Color4 = new Color(89,101,133);

	static final byte[] SPRITE = 
	{
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,3,3,0,0,0,0,0,0,0,
			0,0,0,0,0,0,2,2,2,2,0,0,0,0,0,0,
			0,0,0,3,2,0,2,4,4,2,0,2,3,0,0,0,
			0,0,0,2,2,2,2,3,3,2,2,2,2,0,0,0,
			0,0,0,0,2,2,1,3,3,1,2,2,0,0,0,0,
			0,0,2,2,2,1,3,3,3,3,1,2,2,2,0,0,
			0,3,2,4,3,3,3,4,4,3,3,3,4,2,3,0,
			0,3,2,4,3,3,3,4,4,3,3,3,4,2,3,0,
			0,0,2,2,2,1,3,3,3,3,1,2,2,2,0,0,
			0,0,0,0,2,2,1,3,3,1,2,2,0,0,0,0,
			0,0,0,2,2,2,2,3,3,2,2,2,2,0,0,0,
			0,0,0,3,2,0,2,4,4,2,0,2,3,0,0,0,
			0,0,0,0,0,0,2,2,2,2,0,0,0,0,0,0,
			0,0,0,0,0,0,0,3,3,0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
	};
	
	private int[] ORIGIN = new int[2];
	
	private int PIXEL_SIZE;
	
	private float[] SPEED = new float[2];
	
	public void attachedMove(int[] origin)
	{
		ORIGIN[0] = origin[0] - PIXEL_SIZE*8;
		ORIGIN[1] = origin[1] - PIXEL_SIZE*8;
	}
	
	public boolean detachedMove(int PANEL_HEIGHT)
	{
		if (ORIGIN[1] > PANEL_HEIGHT) {return false;}
		
		ORIGIN[0]+= SPEED[0]; ORIGIN[1]+= SPEED[1];
		
		if (SPEED[0] > 0) {SPEED[0]-= 0.1;}
		
		SPEED[1]+= 0.5; //gravity
		
		return true;
	}
	
	public void setSpeed(float[] vector)
	{SPEED[0] = vector[0]/5; SPEED[1] = vector[1]/5;}
	
	public void initialize(int[] origin, int size)
	{
		PIXEL_SIZE = size;
		ORIGIN[0] = origin[0] - PIXEL_SIZE*8;
		ORIGIN[1] = origin[1] - PIXEL_SIZE*8;	
	}
	
	//ORIGIN
	public int[] getOrigin()
	{return ORIGIN;}
	
	public void setOrigin(int x, int y)
	{ORIGIN[0] = x;ORIGIN[1] = y;}
	
	//SIZE
	public void setPixelSize(int size)
	{PIXEL_SIZE = size;}
		
	public int getPixelSize()
	{return PIXEL_SIZE;}
}
