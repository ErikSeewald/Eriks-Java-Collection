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
	
	public abstract int getTileSize();
	
	// TEMP TILE TO MARK SPOTS ON THE MAP FOR A TASK TO BE DONE LATEE
	public static class TempTile extends MapItem
	{
		public TempTile(int i, int j) {super(i, j);}

		@Override
		public int getTileSize() {return 0;}
	}
}
