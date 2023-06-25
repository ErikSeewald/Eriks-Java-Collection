package taxCollector.mapItem;

public class Lake extends MapItem
{
	public static final int size_tiles = 1;
	
	public Lake(int i, int j)
	{
		super(i, j);
	}

	@Override
	public int getTileSize() {return size_tiles;}
}
