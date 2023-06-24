package taxCollector;

public class TaxCollector 
{
	public static final int collect_tile_range = 2;
	private double collected;
	
	int i, j;
	int size;
	
	private int collect_animation;
	private int empty_animation;
	
	TaxCollector(int i, int j, int size)
	{
		this.i = i;
		this.j = j;
		this.size = size;
		this.collected = 0;
	}
	
	//GAMEPLAY
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
	
	//COMMUNICATION
	public double getCollected()
	{return collected;}
	
	public boolean collectAnimation()
	{return collect_animation > 0;}
	
	public boolean emptyAnimation()
	{return empty_animation > 0;}
}