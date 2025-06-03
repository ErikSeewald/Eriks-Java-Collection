package taxCollector;

import taxCollector.mapItem.House;

public class TaxCollector 
{
	public static final int collect_tile_range = 2;
	private double collected;
	
	int i, j;
	int size;
	
	private int collectAnimation;
	private int emptyAnimation;
	private int damageAnimation;
	
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
		if (add > 0) {collectAnimation = 20;}
	}
	
	public double emptyCollected()
	{
		double temp = collected;
		collected = 0;
		if (temp > 0) {emptyAnimation = 15;}
		return temp;
	}
	
	public void getHit() 
	{
		collected *= 0.8;
		damageAnimation = 10;
	}
	
	public void update()
	{
		if (collectAnimation > 0) {collectAnimation--;}
		if (emptyAnimation > 0) {emptyAnimation--;}
		if (damageAnimation > 0) {damageAnimation--;}
	}
	
	//COMMUNICATION
	public double getCollected()
	{return collected;}
	
	public boolean collectAnimation()
	{return collectAnimation > 0;}
	
	public boolean emptyAnimation()
	{return emptyAnimation > 0;}
	
	public boolean damageAnimation()
	{return damageAnimation > 0;}
}