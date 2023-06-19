package taxCollector;

public class House extends MapItem
{
	private double tax;
	
	private int time_until_next_tax;
	private int cooldown;
	
	House(int i, int j)
	{
		super(i, j);
		time_until_next_tax = 0;
	}
	
	public double collectTax()
	{
		if (time_until_next_tax > 0) {return 0;}
		
		time_until_next_tax = cooldown;
		return tax;
	}
	
	public boolean taxDue() {return time_until_next_tax == 0;}
}
