package clothSim;

public class Simulation 
{
	int pointAmount = 0;	//HOW MANY OF THE 200 PLACES IN THE ARRAY ARE IN USE
	Point[] points = new Point[200];
	
	int connectorAmount = 0;
	Connector[] connectors = new Connector[300];
	
	int pointSize = 10;
	
	int prevSelectedPoint;
	int selectedPoint;
	
	int sizeBuffer = 10; //PREVENT SPAWNING POINTS TOO CLOSE TOGETHER
	
	private ClothSimPanel panel;
	
	Simulation(ClothSimPanel panel)
	{this.panel = panel;}
	
	public class Point
	{
		float positionX;
		float positionY;
		
		float prevPositionX;
		float prevPositionY;
		
		boolean isLocked;
		
		int index;
		
		Point(int index)
		{this.index = index;}
	}
	
	public class Connector
	{
		Point pointA;
		Point pointB;
		
		float length;
		
		boolean isAlive = true;
	}
	
	public void simulation()
	{
		for (int i = 0; i < pointAmount; i++)
		{
			if (!points[i].isLocked)
			{	
				float positionXbefore = points[i].positionX;
				float positionYbefore = points[i].positionY;
				
				//VELOCITY
				points[i].positionX+= points[i].positionX - points[i].prevPositionX;
				points[i].positionY+= points[i].positionY - points[i].prevPositionY;
				
				points[i].positionY+= 1; //GRAVITY
				
				points[i].prevPositionX = positionXbefore;
				points[i].prevPositionY = positionYbefore;
				
				//FLOOR COLLISION
				if (points[i].positionY > panel.getHeight()-pointSize/2) {points[i].positionY = panel.getHeight()-pointSize/2;}
			}
		}
	
		for (int i = 0; i < connectorAmount; i++)
		{	
			if (connectors[i].isAlive)
			{
				float[] connectorCenter = {(connectors[i].pointA.positionX + connectors[i].pointB.positionX) / 2, 
										  (connectors[i].pointA.positionY + connectors[i].pointB.positionY) / 2};
					
				float vectorX = connectors[i].pointA.positionX - connectors[i].pointB.positionX;
				float vectorY = connectors[i].pointA.positionY - connectors[i].pointB.positionY;
						
				float[] connectorVector = normalize(vectorX, vectorY);
					
				//KEEP UP THE CONNECTORS LENGTH BY MOVING THE POINTS HALFWAY IT'S LENGTH IN THE CORRECT DIRECTION FROM IT'S CENTER	
				if (!connectors[i].pointA.isLocked)
				{
					connectors[i].pointA.positionX =  connectorCenter[0] + connectorVector[0] * connectors[i].length/2;
					connectors[i].pointA.positionY =  connectorCenter[1] + connectorVector[1] * connectors[i].length/2;
				}
						
				if (!connectors[i].pointB.isLocked)
				{
					connectors[i].pointB.positionX =  connectorCenter[0] - connectorVector[0] * connectors[i].length/2;
					connectors[i].pointB.positionY =  connectorCenter[1] - connectorVector[1] * connectors[i].length/2;
				}	
			}
		}
	}
	
	public float[] normalize(float x , float y)
	{	
		float[] result = new float[2];
		float length = (float)Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2));
		
		result[0] = x/length;
		result[1] = y/length;
		
		return result;
	}
	
	public void connect()
	{
		if (selectedPoint == -1 || prevSelectedPoint == -1 || pointAmount < 1 || selectedPoint == prevSelectedPoint) {return;}
		
		connectorAmount++;
		
		int cIndex = connectorAmount-1;
    	connectors[cIndex] = new Connector();
		
		connectors[cIndex].pointA = points[prevSelectedPoint];
		connectors[cIndex].pointB = points[selectedPoint];
		
		connectors[cIndex].length = (float)Math.sqrt
		(Math.pow(connectors[cIndex].pointA.positionX - connectors[cIndex].pointB.positionX, 2) 
		+ Math.pow(connectors[cIndex].pointA.positionY - connectors[cIndex].pointB.positionY, 2));
	}
	
	public void newPoint(int index)
	{points[index] = new Point(index);}
	
	public void removeLastConnector()
	{connectorAmount--;}
	
	public void lockPoint()
	{
		if (selectedPoint == -1 || pointAmount < 1) {return;}
		points[selectedPoint].isLocked = !points[selectedPoint].isLocked;
	}
	
	public int getPointIndex(int x, int y)
	{
		int index = 0;
		
		x+= pointSize/2;
		y+= pointSize/2;
		
		for (int i = 0; i < pointAmount; i++)
		{
			if (x > points[i].positionX-sizeBuffer && x < points[i].positionX+pointSize+sizeBuffer &&
				y > points[i].positionY-sizeBuffer && y < points[i].positionY+pointSize+sizeBuffer)
			{return index;}
			index++;
		}
		return -1; //NO POINT FOUND
	}
	
	public void restart()
	{
		pointAmount = 0;
		connectorAmount = 0;
		prevSelectedPoint = -1;
		selectedPoint = -1;
		
		System.gc();
	}
}