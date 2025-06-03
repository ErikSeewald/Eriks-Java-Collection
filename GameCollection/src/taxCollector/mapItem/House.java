package taxCollector.mapItem;

import java.util.Random;

public class House extends MapItem
{
	public static final int size_tiles = 2; // 2x2 tiles
	
	private double tax;
	
	private int timeUntilNextTax;
	private int cooldown;
	
	public House(int i, int j, Random random)
	{
		super(i, j);
		timeUntilNextTax = 0;
		
		initValues(random);
	}
	
	private void initValues(Random random)
	{
		tax = random.nextDouble(200) + 10;
		cooldown = random.nextInt(90000) + 10000;
	}
	
	public double collectTax()
	{
		if (timeUntilNextTax > 0) {return 0;}
		
		timeUntilNextTax = cooldown;
		return tax;
	}
	
	public boolean taxDue() {return timeUntilNextTax == 0;}
	
	@Override
	public void update() 
	{
		if (timeUntilNextTax > 0)
		{timeUntilNextTax--;}
	}
}
