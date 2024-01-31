package juicePoet.juice;

import juicePoet.poem.Poem;

public class Juicer
{	
	private Juice juice; //the juice currently held inside the juicer
	
	//BOTTOM STAND OF THE JUICER
	public static final int BOTTOM_WIDTH = 150;
	public static final int BOTTOM_HEIGHT = 75;
	
	//GLASS THAT HOLDS THE JUICE AFTER JUICING
	public static final int GLASS_WIDTH = 120;
	public static final int GLASS_HEIGHT = 150;
	
	//COORDINATES
	private int[] coordinates = new int[2];
	
	/**
	 * Creates a {@link Juicer} at the given coordinates.
	 * 
	 * @param x the x coordinate of the {@link Juicer}
	 * @param y the y coordinate of the {@link Juicer}
	 */
	public Juicer(int x, int y) 
	{
		this.coordinates[0] = x;
		this.coordinates[1] = y;
	}

	/**
	 * Juices the given poem. Then it keeps the {@link Juice} until it is extracted or a new poem is juiced.
	 * 
	 * @param poem the poem to juice.
	 */
	public void juice(Poem poem)
	{
		this.juice = JuiceAlgorithm.juice(poem);
	}
	
	/**
	 * Extracts the {@link Juice} currently held inside the {@link Juicer} and returns it.
	 * This leaves the {@link Juicer} empty.
	 * @return the extracted {@link Juice} or {@literal null} if the {@link Juicer} is empty.
	 */
	public Juice extractJuice() 
	{
		Juice extracted = this.juice;
		this.juice = null;
		return extracted;
	}
	
	/**
	 * Returns the {@link Juice} currently held inside the {@link Juicer} without extracting it.
	 * @return the {@link Juice} or {@literal null} if the {@link Juicer} is empty.
	 */
	public Juice getJuice()
	{
		return juice;
	}
	
	/**
	 * Returns whether the {@link Juicer} currently holds any {@link Juice}.
	 * 
	 * @return whether the {@link Juicer} currently holds any {@link Juice}
	 */
	public boolean isEmpty()
	{
		return this.juice == null;
	}
	
	public int[] getCoordinates() {return coordinates;}
}
