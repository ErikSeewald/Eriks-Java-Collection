package gravityVectors;
import java.util.ArrayList;

import Main.EJC_Util;

public class Simulation 
{
	//ARROW VARIABLES
	final static int APOINT_SIZE = 6;
	final static int ARROW_LENGTH = 12;
	final static int APOINT_DISTANCE = APOINT_SIZE * 5; //distance between arrows
	int APOINT_COUNT_X = 30; //how many arrows on x axis
	int APOINT_COUNT_Y = APOINT_COUNT_X * 2/3; //how many arrows on y axis
	ArrowPoint[] arrowPoints;
	
	//PULL/GRAVITY POINT VARIABLES
	PullPoint selectedPoint;
	ArrayList<PullPoint> pullPoints;
	
	boolean validDrag = false;
	private boolean normalizeVector = true;
	
	Simulation()
	{pullPoints = new ArrayList<>(); pullPoints.add(new PullPoint());}
	
	public class ArrowPoint
	{
		ArrowPoint(int locX, int locY)
		{this.locX = locX; this.locY = locY; arrowX = locX + 15;  arrowY = locY;}
		
		//LOCATIONS OF THE ACTUAL POINTS
		int locX;
		int locY;
		
		//LOCATIONS OF THE ENDS OF THE ARROWS
		int arrowX;
		int arrowY;
	}
	
	public class PullPoint
	{
		public static final int size = 12;
		
		int locX = 30;
		int locY = 30;
	}
	
	private void simulate()
	{
		int ppoint_count = pullPoints.size();
		
		for (ArrowPoint arrow : arrowPoints)
		{
			float[] vectorsX = new float[ppoint_count];
			float[] vectorsY = new float[ppoint_count];
			
			for (int i = 0; i < ppoint_count; i++)
			{
				vectorsX[i] = pullPoints.get(i).locX - arrow.locX;
				vectorsY[i] = pullPoints.get(i).locY - arrow.locY;
			}
			
			float[] distances = new float[ppoint_count];
			
			for (int i = 0; i < ppoint_count; i++)
			{
				distances[i] = (float) Math.sqrt(Math.pow((vectorsX[i]),2) + Math.pow((vectorsY[i]),2));
			}
			
			//HAVE THE PPOINTS EFFECT THE ARROWS EXPONENTIALLY MORE, THE SMALLER THE DISTANCE BETWEEN THEM IS (0.99^distance)
			//-> HIGHER DISTANCE = SMALLER NUMBER WITH WHICH THE VECTOR WILL END UP BEING MULTIPLIED
			float[] strengths = new float[ppoint_count];
			for (int i = 0; i < ppoint_count; i++)
			{strengths[i] = (float) Math.pow(0.99, distances[i]);}
			
			//NORMALIZE VECTORS SO "STRENGTHS" CAN ACTUALLY AFFECT THE VECTORS PROPORTIONALLY
			for (int i = 0; i < ppoint_count; i++)
			{
				float[] temp = EJC_Util.normalize(vectorsX[i], vectorsY[i]);
				vectorsX[i] = temp[0];
				vectorsY[i] = temp[1];
			}
			
			float arrowVectX = 0;
			float arrowVectY = 0;
			
			//COMBINE ALL THE VECTORS MULTIPLIED BY THEIR STRENGTH
			for (int i = 0; i < ppoint_count; i++)
			{
				arrowVectX+= vectorsX[i] * strengths[i];
				arrowVectY+= vectorsY[i] * strengths[i];
			}
			
			//NORMALIZE ONE FINAL TIME TO MULTIPLY WITH ARROW_LENGTH
			if (normalizeVector)
			{
				float[] temp = EJC_Util.normalize(arrowVectX, arrowVectY);
				arrowVectX = temp[0];
				arrowVectY = temp[1];
			}
			
			arrow.arrowX = (int) (arrow.locX + arrowVectX * ARROW_LENGTH);
			arrow.arrowY = (int) (arrow.locY + arrowVectY * ARROW_LENGTH);
		}
	}
	
	//---------------------------------------CONTROL---------------------------------------
	
	public void initialize()
	{
		//CLEAR OLD ARRAY FROM MEMORY, SET UP NEW ONE WITH ADJUSTED SIZE
		arrowPoints = null;
		System.gc();
		arrowPoints = new ArrowPoint[APOINT_COUNT_X * APOINT_COUNT_Y];

		//ARROWPOINTS
		int index = 0;
		for (int i = 0; i < APOINT_COUNT_X; i++)
		{
			for (int j = 0; j < APOINT_COUNT_Y; j++)
			{
				arrowPoints[index] = new ArrowPoint(i*APOINT_DISTANCE+(APOINT_DISTANCE / 3), j*APOINT_DISTANCE+(APOINT_DISTANCE / 3));
				index++;
			}
		}

		simulate();
	}
	
	public void switchArrowNormalization()
	{
		normalizeVector = !normalizeVector;
		simulate();
	}
	
	public void selectGravityPoint(int X, int Y)
	{	
		validDrag = false;
		
		for (PullPoint point : pullPoints)
		{
			if 
			(
				X > point.locX-10 && X < point.locX+10 &&
				Y > point.locY-10 && Y < point.locY+10 
			)
			{selectedPoint = point; validDrag = true; break;}
		}
	}
	
	public void moveGravityPoint(int X, int Y)
	{
		if (validDrag)
		{
			selectedPoint.locX = X;
			selectedPoint.locY = Y;
		}
		
		simulate();
	}
	
	public void changePPOINT_COUNT(int amount)
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
		APOINT_COUNT_X++;
		APOINT_COUNT_Y = APOINT_COUNT_X * 2/3;
	}
}