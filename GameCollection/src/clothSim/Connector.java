package clothSim;

public class Connector
{
	Point pointA;
	Point pointB;
	
	float length;
	boolean isAlive = true;
	
	Connector(Point a, Point b)
	{
		this.pointA = a; this.pointB = b;
		this.setLength();
	}
	
	public void setLength()
	{
		this.length = (float)Math.sqrt
				(Math.pow(this.pointA.x - this.pointB.x, 2) 
				+ Math.pow(this.pointA.y - this.pointB.y, 2));
	}
}