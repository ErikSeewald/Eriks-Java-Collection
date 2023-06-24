package taxCollector;

public class Lake extends MapItem
{
	public static final int size_tiles = 1;
	
	Lake(int i, int j)
	{
		super(i, j);
	}

	@Override
	public int getTileSize() {return size_tiles;}
}
