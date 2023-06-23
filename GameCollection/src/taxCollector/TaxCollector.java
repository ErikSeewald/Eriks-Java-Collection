package taxCollector;

public class TaxCollector 
{
	private double collected;
	
	int x,y;
	
	int size;
	double speed;
	
	public static final int collect_tile_range = 2;
	
	private int collect_animation;
	private int empty_animation;
	
	TaxCollector(int x, int y, int size, int speed)
	{
		this.x = x;
		this.y = y;
		this.size = size;
		this.speed = speed;
		this.collected = 0;
	}
	
	public double getCollected()
	{return collected;}
	
	public void collect(House house)
	{
		if (house == null) {throw new NullPointerException("House cannot be null");}
		double add = house.collectTax();
		collected += add;
		if (add > 0) {collect_animation = 20;}
	}
	
	public double emptyCollected()
	{
		double temp = collected;
		collected = 0;
		if (temp > 0) {empty_animation = 15;}
		return temp;
	}
	
	public void getHit() 
	{
		collected *= 0.8;
		empty_animation = 20;
	}
	
	public void update()
	{
		if (collect_animation > 0) {collect_animation--;}
		if (empty_animation > 0) {empty_animation--;}
	}
	
	public boolean collectAnimation()
	{return collect_animation > 0;}
	
	public boolean emptyAnimation()
	{return empty_animation > 0;}
}