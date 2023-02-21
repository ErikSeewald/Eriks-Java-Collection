package gravityVectors;
import java.util.ArrayList;

import Main.EJC_Util;

public class Simulation 
{
	//ARROWS
	private static final int ARROW_DISTANCE = 30; //distance between arrows
	private int arrow_countX = 30;
	private int arrow_countY = arrow_countX * 2/3;
	ArrowPoint[] arrowPoints;
	
	//PULLPOINTS
	PullPoint selectedPoint;
	ArrayList<PullPoint> pullPoints;
	
	private boolean validDrag = false;
	private boolean normalize = true;
	
	Simulation()
	{pullPoints = new ArrayList<>(); pullPoints.add(new PullPoint());}
	
	public class ArrowPoint
	{
		static final int length = 12;
		
		//LOCATIONS OF THE ACTUAL POINTS
		int locX, locY;

		//LOCATIONS OF THE ENDS OF THE ARROWS
		int arrowX, arrowY;
		
		ArrowPoint(int locX, int locY)
		{this.locX = locX; this.locY = locY; arrowX = locX + 15;  arrowY = locY;}
	}
	
	public class PullPoint
	{
		public static final int size = 12;
		int locX = 30, locY = 30;
	}
	
	private void simulate()
	{
		int point_count = pullPoints.size();
		
		for (ArrowPoint arrow : arrowPoints)
		{
			float[] vectorsX = new float[point_count], vectorsY = new float[point_count];
			for (int i = 0; i < point_count; i++)
			{
				vectorsX[i] = pullPoints.get(i).locX - arrow.locX;
				vectorsY[i] = pullPoints.get(i).locY - arrow.locY;
			}
			
			float[] distances = new float[point_count];
			for (int i = 0; i < point_count; i++)
			{
				distances[i] = (float) Math.sqrt(Math.pow((vectorsX[i]),2) + Math.pow((vectorsY[i]),2));
			}
			
			//HAVE THE POINTS EFFECT THE ARROWS EXPONENTIALLY MORE, THE SMALLER THE DISTANCE BETWEEN THEM IS (0.99^distance)
			//-> HIGHER DISTANCE = SMALLER NUMBER WITH WHICH THE VECTOR WILL END UP BEING MULTIPLIED
			float[] strengths = new float[point_count];
			for (int i = 0; i < point_count; i++)
			{strengths[i] = (float) Math.pow(0.99, distances[i]);}
			
			//NORMALIZE VECTORS SO "STRENGTHS" CAN ACTUALLY AFFECT THE VECTORS PROPORTIONALLY
			for (int i = 0; i < point_count; i++)
			{
				vectorsX[i]/= distances[i];
				vectorsY[i]/= distances[i];
			}
			
			float arrowVectX = 0, arrowVectY = 0;
			
			//COMBINE ALL THE VECTORS MULTIPLIED BY THEIR STRENGTH
			for (int i = 0; i < point_count; i++)
			{
				arrowVectX+= vectorsX[i] * strengths[i];
				arrowVectY+= vectorsY[i] * strengths[i];
			}
			
			if (normalize)
			{
				float[] temp = EJC_Util.normalize(arrowVectX, arrowVectY);
				arrowVectX = temp[0];
				arrowVectY = temp[1];
			}
			
			arrow.arrowX = (int) (arrow.locX + arrowVectX * ArrowPoint.length);
			arrow.arrowY = (int) (arrow.locY + arrowVectY * ArrowPoint.length);
		}
	}
	
	//---------------------------------------CONTROL---------------------------------------
	
	public void initialize()
	{
		arrowPoints = null; System.gc();
		arrowPoints = new ArrowPoint[arrow_countX * arrow_countY];
		
		int index = 0;
		for (int i = 0; i < arrow_countX; i++)
		{
			for (int j = 0; j < arrow_countY; j++)
			{
				arrowPoints[index] = new ArrowPoint(i*ARROW_DISTANCE+(ARROW_DISTANCE / 3), j*ARROW_DISTANCE+(ARROW_DISTANCE / 3));
				index++;
			}
		}

		simulate();
	}
	
	public void switchArrowNormalization()
	{
		normalize = !normalize;
		simulate();
	}
	
	public void selectPoint(int x, int y)
	{	
		validDrag = false;
		
		for (PullPoint point : pullPoints)
		{
			if 
			(
				x > point.locX-10 && x < point.locX+10 &&
				y > point.locY-10 && y < point.locY+10 
			)
			{selectedPoint = point; validDrag = true; return;}
		}
	}
	
	public void movePoint(int x, int y)
	{
		if (!validDrag)
		{return;}
		
		selectedPoint.locX = x;
		selectedPoint.locY = y;
		simulate();
	}
	
	public void changePointCount(int amount)
	{	
		if (pullPoints.size() + amount < 1) {return;}

		if (amount > 0)
		{pullPoints.add(new PullPoint());}
		else {pullPoints.remove(pullPoints.size() -1);}
		
		selectedPoint =	pullPoints.get(pullPoints.size() - 1);
		simulate();
	}
	
	public void increaseArrowCount()
	{
		arrow_countX++;
		arrow_countY = arrow_countX * 2/3;
		initialize();
	}
}