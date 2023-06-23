package taxCollector;

public class IRS extends MapItem
{
	public static final int size_tiles = 3;
	
	private double funds;
	
	public IRS(int i, int j)
	{
		super(i,j);
		this.funds = 1000;
	}
	
	@Override
	public void update()
	{
		funds--;
		if (funds < 0) {funds = 0;}
	}
	
	public double getFunds()
	{return funds;}
	
	public void addFunds(double amount)
	{funds += amount;}
	
	public boolean isBankrupt() {return funds <= 0;}

	@Override
	public int getTileSize() {return size_tiles;}
}
