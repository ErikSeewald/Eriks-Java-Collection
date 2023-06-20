package taxCollector;

public class IRS 
{
	private double funds;
	
	public IRS()
	{
		funds = 1000;
	}
	
	public double getFunds()
	{return funds;}
	
	public void updateFunds()
	{funds--;}
	
	public void addFunds(double amount)
	{funds += amount;}
}
