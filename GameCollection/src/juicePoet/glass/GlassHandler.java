package juicePoet.glass;

import java.util.ArrayList;
import java.util.List;

import juicePoet.juice.Juicer;

public class GlassHandler
{
	private List<Glass> glasses;
	private Juicer juicer;
	
	public GlassHandler(Juicer juicer)
	{
		glasses= new ArrayList<>();
		this.juicer = juicer;
	}
	
	public void addGlass() 
	{
		int[] juicerCoords = juicer.getCoordinates();
		int juicerX = juicerCoords[0];
		int juicerY = juicerCoords[1];
		
		glasses.add(new Glass(juicerX + 300, juicerY));
	}
	
	public List<Glass> getGlasses() {return glasses;}
}
