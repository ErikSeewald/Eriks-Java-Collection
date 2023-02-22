package bloonShoot;

import java.awt.Color;

import bloonShoot.hittable.HittableIDs;

public class Projectile 
{
	
	//SPRITE
	static final Color[] sprite_palette = 
	{new Color(145,170,234), new Color(138,154,194), new Color(94,111,157), new Color(89,101,133), new Color(212,224,255)};

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
	
	private int[] ORIGIN = new int[2];
	private float[] SPEED = new float[2];
	
	private short timeSinceLastBounce; 
	private short timeSinceHitBlock;
	
	public void initialize(int[] origin, int PIXEL_SIZE)
	{
		ORIGIN[0] = origin[0] - PIXEL_SIZE*8; 
		ORIGIN[1] = origin[1] - PIXEL_SIZE*8; 
		timeSinceHitBlock = 0;
		timeSinceLastBounce = 100;
	}
	
	public boolean fly(int PANEL_HEIGHT)
	{
		if (ORIGIN[1] > PANEL_HEIGHT) {return false;} //HAS HIT FLOOR
		
		ORIGIN[0]+= SPEED[0]; 
		ORIGIN[1]+= SPEED[1]; 
		SPEED[1]+= 0.5; //GRAVITY
		
		timeSinceLastBounce++;
		return true;
	}
	
	public boolean hitReaction(int hittableID)
	{
		boolean isAlive = true;
		
		switch (hittableID)
		{
			case HittableIDs.block: if (timeSinceHitBlock > 50) {isAlive = false;} else {timeSinceHitBlock++; SPEED[0] = 0; SPEED[1] = 0;}
			break;
			case HittableIDs.bounceblock: if (timeSinceLastBounce > 20) {bounce(2);}
			break;
			case HittableIDs.woodblock: if (timeSinceLastBounce > 20) {bounce(4);}
			break;
		}
		
		return isAlive;
	}
	
	private void bounce(int factor)
	{
		SPEED[0] = -(SPEED[0]/factor); 
		SPEED[1] = -(SPEED[1]/factor);
		timeSinceLastBounce = 0;
	}
	
	public void setSpeed(float[] vector)
	{SPEED[0] = vector[0]/5; SPEED[1] = vector[1]/5;}
	
	public int[] getOrigin()
	{return ORIGIN;}
	
	public void setNewOrigin(int[] origin, int PIXEL_SIZE)
	{ORIGIN[0] = origin[0] - PIXEL_SIZE*8; ORIGIN[1] = origin[1] - PIXEL_SIZE*8;}
}