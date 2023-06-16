package taxCollector;

public class TaxCollector 
{
	private double collected;
	
	int x,y;
	
	TaxCollector(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.collected = 0;
	}
	
	public double getCollected()
	{return collected;}
	
	public void collect(House house)
	{
		if (house == null) {throw new NullPointerException("House cannot be null");}
		collected += house.collectTax();
	}
	
	public void getHit() 
	{
		collected *= 0.8;
	}
	
	
}
