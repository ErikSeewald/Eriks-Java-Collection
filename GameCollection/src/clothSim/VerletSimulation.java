package clothSim;

import java.util.ArrayList;

public class VerletSimulation 
{
	ArrayList<Point> points;
	ArrayList<Connector> connectors;
	
	Point prevSelectedPoint;
	Point selectedPoint;
	
	private ClothSimPanel panel;
	public boolean isRunning;
	
	VerletSimulation(ClothSimPanel panel)
	{
		this.panel = panel;
		points = new ArrayList<>();
		connectors = new ArrayList<>();
	}
	
	public void algorithm()
	{
		for (Point p : points)
		{
			if (p.isLocked) {continue;}
			
			float tempX = p.x,  tempY = p.y;

			//VELOCITY
			p.x += p.x - p.prevX;
			p.y += p.y - p.prevY;

			//GRAVITY
			p.y+= 1;

			p.prevX = tempX;
			p.prevY = tempY;

			//FLOOR COLLISION
			if (p.y > panel.getHeight()-Point.size/2) 
			{
				p.y = panel.getHeight()-Point.size/2;
				p.removeXVelocity();
			}	
		}
	
		for (Connector c : connectors)
		{	
			float[] center = {(c.pointA.x + c.pointB.x) / 2, (c.pointA.y + c.pointB.y) / 2};

			float vecX = c.pointA.x - c.pointB.x, vecY = c.pointA.y - c.pointB.y;
			float[] cVector = normalize(vecX, vecY);

			//KEEP THE CONNECTORS LENGTH BY MOVING THE POINTS HALFWAY IT'S LENGTH IN THE CORRECT DIRECTION FROM IT'S CENTER	
			if (!c.pointA.isLocked)
			{
				c.pointA.x =  center[0] + cVector[0] * c.length/2;
				c.pointA.y =  center[1] + cVector[1] * c.length/2;
			}

			if (!c.pointB.isLocked)
			{
				c.pointB.x =  center[0] - cVector[0] * c.length/2;
				c.pointB.y =  center[1] - cVector[1] * c.length/2;
			}	
		}
	}
	
	private float[] normalize(float x , float y)
	{	
		float length = (float) Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2));
		return new float[] {x/length, y/length};
	}
	
	//---------------------------------------SELECTED_POINT---------------------------------------
	
	public void connect()
	{
		if (selectedPoint == null || prevSelectedPoint == null || selectedPoint == prevSelectedPoint) {return;}
		connectors.add(new Connector(prevSelectedPoint, selectedPoint));
	}
	
	public void removeLastConnector()
	{connectors.remove(connectors.size() - 1);}
	
	public void lockSelectedPoint()
	{
		if (selectedPoint == null) {return;}
		selectedPoint.lock();
	}
	
	public void setSelectedPoint(int x, int y)
	{
		int index = getPointIndex(x,y);
		if (index == -1) {selectedPoint = null; return;}
		
		prevSelectedPoint = selectedPoint;
		selectedPoint = points.get(index);
	}
	
	private int getPointIndex(int x, int y)
	{
		x+= Point.size/2; y+= Point.size/2;
		int sizeBuffer = 10, i = 0;
	
		for (Point point : points)
		{
			if (x > point.x-sizeBuffer && x < point.x+Point.size+sizeBuffer &&
				y > point.y-sizeBuffer && y < point.y+Point.size+sizeBuffer)
			{return i;}
			i++;
		}
		return -1;
	}
	
	public void moveSelectedPoint(int x, int y)
	{
		selectedPoint.x = x;
		selectedPoint.y = y;
	}
	
	public void cutSelectedPoint()
	{	
		for (Connector connector : connectors)
		{
			if (connector.pointA == selectedPoint || connector.pointB == selectedPoint)
			{connector.isAlive = false;}
		}
		connectors.removeIf(c -> c.isAlive == false); //can't remove them in the loop, because we would modify the array that we iterate through
		
		points.remove(selectedPoint);
	}
	
	public void addPoint(int x, int y)
	{points.add(new Point(x, y));}
	
	public void restart()
	{
		prevSelectedPoint = null;
		selectedPoint = null;
		connectors.removeAll(connectors);
		points.removeAll(points);
		System.gc();
	}
}