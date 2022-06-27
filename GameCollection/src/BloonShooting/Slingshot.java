package BloonShooting;

import java.awt.Color;

public class Slingshot 
{
	final static Color LIGHTcol = new Color(110,125,170);
	final static Color DARKcol = new Color(95,110,160);
	final static Color BANDcol = new Color(75,75,110);
	
	private int[] ORIGIN = new int[2];
	
	private int[] PULLPOINT = new int[2];
	private int[] PULLPOINT_GOAL = new int[2];
	
	private boolean dragValid = false;
	
	Slingshot()
	{
		
	}
	
	public void initPoints(int PANEL_WIDTH, int PANEL_HEIGHT)
	{
		ORIGIN[0] = PANEL_WIDTH/10;
		ORIGIN[1] = PANEL_HEIGHT - (PANEL_HEIGHT/4);
		
		PULLPOINT_GOAL[0] = (int) (PANEL_WIDTH/7.3);
		PULLPOINT_GOAL[1] = PANEL_HEIGHT - (PANEL_HEIGHT/5);
		
		PULLPOINT[0] = PULLPOINT_GOAL[0];
		PULLPOINT[1] = PULLPOINT_GOAL[1];
	}
	
	public int[] getReturnVect()
	{return new int[] {PULLPOINT_GOAL[0] - PULLPOINT[0], PULLPOINT_GOAL[1] - PULLPOINT[1]};}
	
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
