package bloonShoot;

import java.awt.Color;

public class Projectile 
{
	
	//SPRITE
	final static Color Color1 = new Color(145,170,234);
	final static Color Color2 = new Color(138,154,194);
	final static Color Color3 = new Color(94,111,157);
	final static Color Color4 = new Color(89,101,133);
	final static Color Color5 = new Color(212,224,255);
	
	private int PIXEL_SIZE;

	static final byte[] SPRITE = 
	{
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
			0,5,0,0,0,0,0,3,3,0,0,0,0,0,5,0,
			0,0,5,5,0,0,2,2,2,2,0,0,5,5,0,0,
			0,0,5,3,2,5,2,4,4,2,5,2,3,5,0,0,
			0,0,0,2,2,2,2,3,3,2,2,2,2,0,0,0,
			0,0,0,5,2,2,1,3,3,1,2,2,5,0,0,0,
			0,0,2,2,2,1,3,3,3,3,1,2,2,2,0,0,
			0,3,2,4,3,3,3,4,4,3,3,3,4,2,3,0,
			0,3,2,4,3,3,3,4,4,3,3,3,4,2,3,0,
			0,0,2,2,2,1,3,3,3,3,1,2,2,2,0,0,
			0,0,0,5,2,2,1,3,3,1,2,2,5,0,0,0,
			0,0,0,2,2,2,2,3,3,2,2,2,2,0,0,0,
			0,0,5,3,2,5,2,4,4,2,5,2,3,5,0,0,
			0,0,5,5,0,0,2,2,2,2,0,0,5,5,0,0,
			0,5,0,0,0,0,0,3,3,0,0,0,0,0,5,0,
			0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
	};
	
	
	//COORDINATES & VECTORS
	private int[] ORIGIN = new int[2];
	
	private float[] SPEED = new float[2];
	
	private short timeSinceLastBounce; 
	
	private short timeSinceHitBlock;
	
	//INITIALIZATION
	public void initialize(int[] origin)
	{
		ORIGIN[0] = origin[0] - PIXEL_SIZE*8; 
		ORIGIN[1] = origin[1] - PIXEL_SIZE*8; 
		timeSinceHitBlock = 0;
		timeSinceLastBounce = 100;
		System.gc();
	}
	
	//MOVEMENT
	public boolean fly(int PANEL_HEIGHT)
	{
		if (ORIGIN[1] > PANEL_HEIGHT) {return false;} //HAS HIT FLOOR
		
		ORIGIN[0]+= SPEED[0]; ORIGIN[1]+= SPEED[1]; 
		SPEED[1]+= 0.5; //GRAVITY
		
		timeSinceLastBounce++;
		
		return true;
	}
	
	public boolean hitReaction(int hittableID)
	{
		boolean isAlive = true;
		
		switch (hittableID)
		{
			case 2: if (timeSinceHitBlock > 50) {isAlive = false;} else {timeSinceHitBlock++; SPEED[0] = 0; SPEED[1] = 0;}
			break;
			case 3: if (timeSinceLastBounce > 20) {SPEED[0] = -(SPEED[0]/2); SPEED[1] = -(SPEED[1]/2); timeSinceLastBounce = 0;}
			break;
			case 4: if (timeSinceLastBounce > 20) {SPEED[0] = -(SPEED[0]/4); SPEED[1] = -(SPEED[1]/4); timeSinceLastBounce = 0;}
			break;
		}
		
		return isAlive;
	}
	
	public void setSpeed(float[] vector)
	{SPEED[0] = vector[0]/5; SPEED[1] = vector[1]/5;}
	
	//ORIGIN
	public int[] getOrigin()
	{return ORIGIN;}
	
	public void setNewOrigin(int[] origin)
	{ORIGIN[0] = origin[0] - PIXEL_SIZE*8; ORIGIN[1] = origin[1] - PIXEL_SIZE*8;}
	
	//SIZE
	public void setPixelSize(int size)
	{PIXEL_SIZE = size;}
}