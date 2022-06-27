package BloonShooting;

import java.awt.Color;

public class Slingshot 
{
	final static Color Color1 = new Color(110,125,170);
	final static Color Color2 = new Color(95,110,160);
	final static Color SlingColor = new Color(75,75,110);
	
	private int[] ORIGIN = new int[2];
	
	private int[] PULLPOINT = new int[2];
	private int[] PULLPOINT_GOAL = new int[2];
	
	private int[] returnVect = new int[2];
	
	private boolean dragValid = false;
	
	int slingReturnRounds = 0;

	static final byte[] SPRITE = 
	{
			1,2,2,0,0,0,0,0,0,0,0,0,0,0,1,1,
			1,1,1,2,0,0,0,0,0,0,0,0,0,1,1,2,
			0,1,1,1,2,0,0,0,0,0,0,0,1,1,2,2,
			0,0,1,1,1,2,0,0,0,0,0,1,1,2,2,0,
			0,0,0,1,1,1,2,0,0,0,0,1,2,2,0,0,
			0,0,0,0,1,1,2,2,0,0,1,1,2,0,0,0,
			0,0,0,0,1,1,2,2,0,0,1,1,2,0,0,0,
			0,0,0,0,0,1,1,1,2,1,1,2,2,0,0,0,
			0,0,0,0,0,0,1,1,2,1,1,2,0,0,0,0,
			0,0,0,0,0,0,1,1,1,1,2,2,0,0,0,0,
			0,0,0,0,0,0,0,1,1,1,2,0,0,0,0,0,
			0,0,0,0,0,0,0,1,1,1,2,0,0,0,0,0,
			0,0,0,0,0,0,0,1,1,1,2,0,0,0,0,0,
			0,0,0,0,0,0,2,1,1,1,2,0,0,0,0,0,
			0,0,0,0,0,2,1,1,1,1,1,2,0,0,0,0,
			0,0,0,0,2,2,2,2,2,2,2,2,2,0,0,0,
	};
	
	public void moveSling()
	{
		if (slingReturnRounds == 5) {return;}
		
		PULLPOINT[0]+= returnVect[0]/5;
		PULLPOINT[1]+= returnVect[1]/5;
		
		slingReturnRounds++;
	}
	
	public void setReturnVect()
	{returnVect[0] = PULLPOINT_GOAL[0] - PULLPOINT[0]; returnVect[1] = PULLPOINT_GOAL[1] - PULLPOINT[1];}
	
	public void initPoints(int PANEL_WIDTH, int PANEL_HEIGHT)
	{
		ORIGIN[0] = PANEL_WIDTH/10;
		ORIGIN[1] = PANEL_HEIGHT - (PANEL_HEIGHT/4);
		
		PULLPOINT_GOAL[0] = (int) (PANEL_WIDTH/7.3);
		PULLPOINT_GOAL[1] = PANEL_HEIGHT - (PANEL_HEIGHT/5);
		
		PULLPOINT[0] = PULLPOINT_GOAL[0];
		PULLPOINT[1] = PULLPOINT_GOAL[1];
	}
	
	//PULL POINT
	public void setPullPoint(int x, int y)
	{PULLPOINT[0] = x;PULLPOINT[1] = y;}
	
	public int[] getPullPoint()
	{return PULLPOINT;}
	
	//DRAG VALID
	public void setDragValid(int x, int y)
	{dragValid = (x > PULLPOINT[0]-10 && x < PULLPOINT[0]+10 && y > PULLPOINT[1]-10 && y < PULLPOINT[1]+10);}
	
	public boolean isDragValid()
	{return dragValid;}
	
	//ORIGIN
	public int[] getOrigin()
	{return ORIGIN;}
	
	public void setOrigin(int x, int y)
	{ORIGIN[0] = x;ORIGIN[1] = y;}
	
	public int[] getPaintOrigin(int PIXEL_SIZE)
	{return new int[] {ORIGIN[0] + PIXEL_SIZE, ORIGIN[1] + PIXEL_SIZE};}
}
