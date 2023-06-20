package taxCollector;

import java.util.Random;

public class House extends MapItem
{
	private double tax;
	
	private int time_until_next_tax;
	private int cooldown;
	
	House(int i, int j, Random random)
	{
		super(i, j);
		time_until_next_tax = 0;
		
		initValues(random);
	}
	
	private void initValues(Random random)
	{
		tax = random.nextDouble(500) + 10;
		cooldown = random.nextInt(9000) + 1000;
	}
	
	public double collectTax()
	{
		if (time_until_next_tax > 0) {return 0;}
		
		time_until_next_tax = cooldown;
		return tax;
	}
	
	public boolean taxDue() {return time_until_next_tax == 0;}
	
	@Override
	public void update() 
	{
		if (time_until_next_tax > 0)
		{time_until_next_tax--;}
	}
}
