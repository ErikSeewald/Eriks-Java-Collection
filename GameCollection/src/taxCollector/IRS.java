package taxCollector;

public class IRS extends MapItem
{
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
}
