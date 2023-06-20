package taxCollector;

public class IRS 
{
	private double funds;
	int tile_x, tile_y;
	
	public IRS(int tile_x, int tile_y)
	{
		this.tile_x = tile_x;
		this.tile_y = tile_y;
		this.funds = 1000;
	}
	
	public double getFunds()
	{return funds;}
	
	public void updateFunds()
	{
		funds--;
		if (funds < 0) {funds = 0;}
	}
	
	public void addFunds(double amount)
	{funds += amount;}
	
	public boolean isBankrupt() {return funds <= 0;}
}
