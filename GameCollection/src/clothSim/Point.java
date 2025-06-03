package clothSim;

public class Point
{
	public static final int size = 10;

	float x, y;
	float prevX, prevY;
	
	boolean isLocked;
	
	Point(float x, float y)
	{
		this.x = x;
		this.y = y;
		this.removeAllVelocity();
	}
	
	public void lock()
	{
		this.isLocked = !this.isLocked;
		this.removeAllVelocity();
	}
	
	public void removeAllVelocity()
	{
		this.prevX = this.x;
		this.prevY = this.y;
	}
	
	public void removeXVelocity()
	{this.prevX = this.x;}
}