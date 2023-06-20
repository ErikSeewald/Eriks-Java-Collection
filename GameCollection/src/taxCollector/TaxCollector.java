package taxCollector;

public class TaxCollector 
{
	private double collected;
	
	int x,y;
	
	int size;
	double speed;
	
	public static final int collect_tile_range = 2;
	
	TaxCollector(int x, int y, int size)
	{
		this.x = x;
		this.y = y;
		this.size = size;
		this.speed = size / 3;
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
