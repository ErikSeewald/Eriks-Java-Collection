package automata;

import java.util.Random;

public class CellHandler 
{
	private float[][] cells; //cell state between 0 and 1
	private int num_cells_x = 375;
	private int num_cells_y = 225;
	boolean pixelsSmaller = true;
	
	private static final int numSensors = 8;
	private static final int numPotentialStates = numSensors / 2;
	private static final float dt = 0.048f;
	private float sensorData[] = new float[numSensors];
	private float potentialStates[] = new float[numPotentialStates];
	
	private class Ring
	{
		int[] radiusMinMax;
		float[] aliveMinMax;
		float[] deadMinMax;
		
		Ring()
		{
			radiusMinMax = new int[2];
			aliveMinMax = new float[2];
			deadMinMax = new float[2];
		}
	}
	private Ring[] rings = new Ring[numSensors];
	
	private Random random;
	
	CellHandler()
	{
		cells = new float[num_cells_x][num_cells_y];
		random = new Random();
		generateRules();
		generateCells();
	}
	
	//RULES
	public void generateRules()
	{
		for (int i = 0; i < rings.length; i++)
		{
			rings[i] = new Ring();
			setRandomRadius(rings[i]);
			rings[i].aliveMinMax = minMaxPair();
			rings[i].deadMinMax = minMaxPair();
		}
	}
	
	private void setRandomRadius(Ring ring)
	{
		int max = 10;
		int radiusA = random.nextInt(max);
		int radiusB = random.nextInt(max);
		ring.radiusMinMax[0] = (radiusA < radiusB) ? radiusA : radiusB;
		ring.radiusMinMax[1] = (radiusA > radiusB) ? radiusA : radiusB;
	}
	
	private float[] minMaxPair()
	{
		float a = random.nextFloat();
		float b = random.nextFloat();
		if (a > b)
		{return new float[] {b, a};}
		return new float[] {a, b};
	}
	
	//CELLS
	private void generateCells()
	{
		for (int x = 0; x < num_cells_x; x++)
		{
			for (int y = 0; y < num_cells_y; y++)
			{
				cells[x][y] = (float) Math.pow(random.nextFloat(), 4);
			}
		}
	}
	
	public void draw(int x, int y)
	{
		int i = x / (CellPanel.PANEL_WIDTH / num_cells_x);
		int j = y / (CellPanel.PANEL_HEIGHT / num_cells_y);
		
		for (int k = 0; k < 10; k++)
		{
			for (int l = 0; l < 10; l++)
			{
				int a = i + k, b = j + l;
				if (a < 0 || a >= num_cells_x || b < 0 || b >= num_cells_y) {continue;}
				cells[a][b] = 1;
			}
		}
	}
	
	//CONTROL
	public void switchPixelSize()
	{
		if (pixelsSmaller)
		{
			num_cells_x = 150;
			num_cells_y = 90;
		}
		else
		{
			num_cells_x = 375;
			num_cells_y = 225;
		}
			
		pixelsSmaller = !pixelsSmaller;
		cells = new float[num_cells_x][num_cells_y];
		generateRules();
		generateCells();
	}
	
	public void stop()
	{
		cells = null;
	}
	
	public float[][] getCells() {return cells.clone();}
	
	//SIMULATION
	public void update()
	{
		for (int x = 0; x < num_cells_x; x++)
		{
			for (int y = 0; y < num_cells_y; y++)
			{
				updateCell(x,y);
			}
		}
	}
	
	private void updateCell(int x, int y)
	{
		// CALCULATE SENSOR DATA FOR EACH RING
		for (int i = 0; i < numSensors; i++) 
		{
			sensorData[i] = sensorCalc(x, y, rings[i].radiusMinMax[0], rings[i].radiusMinMax[1]);
		}

		// NEXT POTENTIAL STATES INITIALIZED TO PREV STATE
		for (int i = 0; i < numPotentialStates; i++) 
		{
			potentialStates[i] = cells[x][y];
		}

		// LOOP OVER SENSOR DATA
		// EACH STATE IS AFFECTED BY TWO CONSECUTIVE SENSORS
		for (int i = 0; i < numSensors; i++) 
		{
			float sensorValue = sensorData[i];

			if (sensorValue >= rings[i].aliveMinMax[0] && sensorValue <= rings[i].aliveMinMax[1]) 
			{potentialStates[i / 2] += dt;}
			
			if (sensorValue >= rings[i].deadMinMax[0] && sensorValue <= rings[i].deadMinMax[1]) 
			{potentialStates[i / 2] -= dt;}
		}

		// BLUR POTENTIAL STATES
		for (int i = 0; i < numPotentialStates; i++) 
		{
			float avgInSensors = (sensorData[i*2] + sensorData[i*2+1]) * 0.5f;
			potentialStates[i] = (potentialStates[i] + avgInSensors * dt) / (1 + dt);
		}

		// CHOOSE POTENTIAL STATE MOST DIFFERENT FROM PREV
		float maxDelta = 0;
		int selectedStateIndex = 0;

		for (int i = 0; i < numPotentialStates; i++ ) 
		{
			float delta = Math.abs(cells[x][y] - potentialStates[i]);
			if (delta > maxDelta) 
			{
				maxDelta = delta;
				selectedStateIndex = i;
			}
		}

		cells[x][y] = potentialStates[selectedStateIndex];
		if (cells[x][y] < 0) {cells[x][y] = 0;}
		else if (cells[x][y] > 1) {cells[x][y] = 1;}
	}
	
	// Calculate average value of all cells inside the sensor radius
	private float sensorCalc(int x, int y, int radiusMin, int radiusMax) 
	{
		int cellCount = 0;
		float value = 0;
		
		for (int offsetX = -radiusMax; offsetX <= radiusMax; offsetX += 1)
		{
			for (int offsetY = -radiusMax; offsetY <= radiusMax; offsetY += 1) 
			{
				int newX = x + offsetX, newY = y + offsetY;
				
				if (newX < 0 || newX >= num_cells_x || newY < 0 || newY >= num_cells_y)
				{continue;}
				
				double distance = Math.sqrt(offsetX*offsetX + offsetY*offsetY);
				
				if (distance <= radiusMax && distance > radiusMin) 
				{
					cellCount += 1;

					value += cells[newX][newY];
				}
			}
		}
		
		if (cellCount == 0) {return 0;}
		return value / cellCount;
	}
}
