package bloonShoot;

import java.awt.Color;

public class Slingshot 
{
	//SPRITE
	final static Color Color1 = new Color(110,125,170);
	final static Color Color2 = new Color(95,110,160);
	final static Color Color3 = new Color(132,148,188);
	final static Color SlingColor = new Color(75,75,110);
	
	private int PIXEL_SIZE;
	
	static final byte[] SPRITE = 
	{
			1,2,2,0,0,0,0,0,0,0,0,0,0,0,1,1,
			3,1,1,2,0,0,0,0,0,0,0,0,0,1,1,2,
			0,3,1,1,2,0,0,0,0,0,0,0,1,1,2,2,
			0,0,3,1,1,2,0,0,0,0,0,1,1,2,2,0,
			0,0,0,3,1,1,2,0,0,0,0,1,2,2,0,0,
			0,0,0,0,3,1,2,2,0,0,1,1,2,0,0,0,
			0,0,0,0,3,1,2,2,0,0,1,1,2,0,0,0,
			0,0,0,0,0,3,1,1,2,1,1,2,2,0,0,0,
			0,0,0,0,0,0,3,1,2,1,1,2,0,0,0,0,
			0,0,0,0,0,0,3,1,1,1,2,2,0,0,0,0,
			0,0,0,0,0,0,0,3,1,1,2,0,0,0,0,0,
			0,0,0,0,0,0,0,3,1,1,2,0,0,0,0,0,
			0,0,0,0,0,0,0,3,1,1,2,0,0,0,0,0,
			0,0,0,0,0,0,3,1,1,1,2,0,0,0,0,0,
			0,0,0,0,0,3,1,1,1,1,1,2,0,0,0,0,
			0,0,0,0,3,2,2,2,2,2,2,2,2,0,0,0,
	};
		
	//COORDINATES
	private int[] ORIGIN = new int[2];
	
	private int[] PULLPOINT = new int[2]; //WHERE IS THE PULLPOINT CURRENTLY
	private int[] PULLPOINT_ORIGIN = new int[2]; //WHERE WAS IT ORIGINALLY
	
	private float[] returnVect = new float[2];
	
	//CONDITIONS
	private boolean dragValid = false;
	
	int slingReturnRounds = 0;

	
	//INITIALIZATION
	public void initialize(int PANEL_WIDTH, int PANEL_HEIGHT, int size)
	{setPixelSize(size); initOnNewCoords(PANEL_WIDTH/10, PANEL_HEIGHT - (PANEL_HEIGHT/4));}
		
	public void initOnNewCoords(int x, int y)
	{
		ORIGIN[0] = x; ORIGIN[1] = y;
			
		PULLPOINT_ORIGIN[0] = ORIGIN[0] + PIXEL_SIZE*8;
		PULLPOINT_ORIGIN[1] = ORIGIN[1] + PIXEL_SIZE*8;
			
		PULLPOINT[0] = PULLPOINT_ORIGIN[0];
		PULLPOINT[1] = PULLPOINT_ORIGIN[1];
	}
	
	//MOVEMENT
	public boolean moveSling()
	{
		if (slingReturnRounds > 4) {return false;} //SLING HAS REACHED ORIGIN -> RETURN NO LONGER MOVING
		
		PULLPOINT[0]+= returnVect[0]/5;
		PULLPOINT[1]+= returnVect[1]/5;
		
		slingReturnRounds++;
		
		return true; //-> STILL MOVING
	}
	
	//RETURN VECTOR
	public void setReturnVect()
	{
		returnVect[0] = PULLPOINT_ORIGIN[0] - PULLPOINT[0]; 
		returnVect[1] = PULLPOINT_ORIGIN[1] - PULLPOINT[1]; 
		slingReturnRounds = 0;
	}
	
	public float[] getReturnVect()
	{return returnVect;}
	
	
	//PULL POINT
	public void setPullPoint(int x, int y)
	{PULLPOINT[0] = x; PULLPOINT[1] = y;}
	
	public int[] getPullPoint()
	{return PULLPOINT;}
	
	//DRAG VALID
	public void setDragValid(int x, int y)
	{dragValid = (x > PULLPOINT[0]-10 && x < PULLPOINT[0]+10 && y > PULLPOINT[1]-10 && y < PULLPOINT[1]+10);}
	
	public boolean isDragValid()
	{return dragValid;}
	
	//SIZE
	public void setPixelSize(int size)
	{PIXEL_SIZE = size;}
	
	//ORIGIN
	public int[] getOrigin()
	{return ORIGIN;}
	
	public int[] getPaintOrigin() //third element in the array is where the second band of the sling starts from
	{return new int[] {ORIGIN[0] + PIXEL_SIZE, ORIGIN[1] + PIXEL_SIZE, ORIGIN[0] + PIXEL_SIZE*14};}
}