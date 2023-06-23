package taxCollector;

public abstract class MapItem 
{
	public int i, j; //map indices
	
	MapItem(int i, int j)
	{
		this.i = i;
		this.j = j;
	}
	
	public void update() {}
	
	public abstract int getTileSize();
}
