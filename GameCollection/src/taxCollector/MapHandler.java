package taxCollector;

public class MapHandler 
{
	private int[][] map;
	
	private TaxCollector taxCollector;
	private TC_Panel panel;
	
	MapHandler(TC_Panel panel)
	{
		this.panel = panel;
		
		map = new int[1000][1000];
		
		taxCollector = new TaxCollector(panel.getHeight() / 2, panel.getWidth() / 2);
	}
	
	public TaxCollector getTaxCollector() {return taxCollector;}
}
