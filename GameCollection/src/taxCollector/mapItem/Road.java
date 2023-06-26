package taxCollector.mapItem;

public class Road extends MapItem
{
	public Road(int i, int j) 
	{
		super(i, j);
	}

	// SUBCLASS USED AS THE GUIDING RAIL FOR CARS -> ALWAYS AS THE CENTER OF THE ROAD
	public static class RoadRail extends Road
	{
		public RoadRail(int i, int j) {super(i, j);}
	}
}
