package taxCollector.mapItem;

public abstract class MapItem 
{
	public int i, j; //map indices
	
	public MapItem(int i, int j)
	{
		this.i = i;
		this.j = j;
	}
	
	public void update() {}
}
