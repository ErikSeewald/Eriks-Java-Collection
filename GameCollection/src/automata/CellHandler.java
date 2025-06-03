package automata;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Handles the simulation of the automata cells.
 */
public class CellHandler 
{
	private float[][] cells; //cell state between 0 and 1
	private float[][] newCells; // Next cell states, for concurrent threads to write to
	private int num_cells_x = 375;
	private int num_cells_y = 225;
	boolean pixelsSmaller = true;
	
	private static final int numSensors = 8;
	private static final int numPotentialStates = numSensors / 2;
	private static final float dt = 0.048f;
	
	/**
	 * Representation of a ring of influence around a conceptual cell.
	 * Contains the rules which govern how the the influence is exerted.
	 */
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
	
	/**
	 * Creates a {@link CellHandler} object.
	 */
	CellHandler()
	{
		cells = new float[num_cells_x][num_cells_y];
		newCells = new float[num_cells_x][num_cells_y];
		random = new Random();
		generateRules();
		generateCells();
	}
	
	
	//RULES
	/**
	 * Creates new {@link Ring}s with randomly generated rules to govern the behavior of the automata cells.
	 */
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
	
	/**
	 * Sets the minimum and maximum radius of the given {@link Ring} to a random value within a certain range.
	 * 
	 * @param ring the {@link Ring} to be modified
	 */
	private void setRandomRadius(Ring ring)
	{
		int max = 10;
		int radiusA = random.nextInt(max);
		int radiusB = random.nextInt(max);
		ring.radiusMinMax[0] = (radiusA < radiusB) ? radiusA : radiusB;
		ring.radiusMinMax[1] = (radiusA > radiusB) ? radiusA : radiusB;
	}
	
	/**
	 * Creates a new randomly generated pair of values with the requirement that the value at index 0 is less than or
	 * equal to the value at index 1.
	 * 
	 * @return the {@link float[]} value pair
	 */
	private float[] minMaxPair()
	{
		float a = random.nextFloat();
		float b = random.nextFloat();
		if (a > b)
		{return new float[] {b, a};}
		return new float[] {a, b};
	}
	
	
	//CELLS
	/**
	 * Generates new cell values and fills this objects cells[][] array with them.
	 * The values are generated from a function f(x): [0.0, 1.0] -> x^4 
	 */
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
	
	/**
	 * Handles a call to 'draw' (i.e. spawn new cells with value 1) on a position (x,y).
	 * 
	 * @param x the x position
	 * @param y the y position
	 */
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
	
	/**
	 * Switches between the two possible pixel/cell sizes. Thereby it generates a new set of cells and rules.
	 */
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
	
	/**
	 * Helper method for {@link EJC_Automata}s stop method.
	 * Frees memory.
	 */
	public void stop()
	{
		cells = null;
	}
	
	/**
	 * Returns a clone of this objects cells array.
	 * @return {@link float[][]} cells array
	 */
	public float[][] getCells() {return cells.clone();}
	
	
	//SIMULATION
	/**
	 * Updates the simulation by one step.
	 */
	public void update()
	{	
		// RUN SEPARATE UPDATE THREADS FOR EACH COLUMN OF THE CELL ARRAY (6x performance increase)
		ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		
		for (int x = 0; x < num_cells_x; x++) 
		{
		    final int finalX = x; // Variables in lambdas need to be final
		    executor.submit(() -> 
		    {
		    	// UPDATE THE COLUMN
		        for (int y = 0; y < num_cells_y; y++) 
		        {
		            updateCell(finalX, y);
		        }
		    });
		}

		// TERMINATE THREADS
		executor.shutdown();
		try 
		{
		    executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {e.printStackTrace();}
		
		// COPY THE NEW STATES TO THE MAIN CELL STATE ARRAY
		for (int x = 0; x < num_cells_x; x++)
		{
			cells[x] = Arrays.copyOf(newCells[x], num_cells_y);
		}
	}
	
	/**
	 * Updates the cell at the given coordinates by one step based on specific rules.
	 * Assumes that a cell at the given coordinate actually exists.
	 * 
	 * @param x the x coordinate of the cell
	 * @param y the y coordinate of the cell
	 */
	private void updateCell(int x, int y)
	{
		float[] sensorData = new float[numSensors];
		float[] potentialStates = new float[numPotentialStates];
		
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

		newCells[x][y] = potentialStates[selectedStateIndex];
		if (newCells[x][y] < 0) {newCells[x][y] = 0;}
		else if (newCells[x][y] > 1) {newCells[x][y] = 1;}
	}
	
	/**
	 * Calculates the average value of all cells inside the sensor radius.
	 * 
	 * @param x the x coordinate of the cell
	 * @param y the y coordinate of the cell
	 * @param radiusMin the minimum radius of cells to check
	 * @param radiusMax the maximum radius of cells to check
	 * @return the average value of all cells inside the sensor radius
	 */
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
