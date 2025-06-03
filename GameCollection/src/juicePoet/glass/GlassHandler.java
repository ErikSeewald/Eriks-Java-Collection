package juicePoet.glass;

import java.util.ArrayList;
import java.util.List;

import juicePoet.juice.Juicer;

/**
 * Holds a list of all {@link Glass} objects and manages them.
 *
 */
public class GlassHandler
{
	private List<Glass> glasses;
	private Juicer juicer;
	
	public GlassHandler(Juicer juicer)
	{
		glasses = new ArrayList<>();
		this.juicer = juicer;
	}
	
	public void deleteGlass(Glass glass)
	{
		glasses.remove(glass);
	}
	
	/**
	 * Creates a new glass next to the {@link Juicer} and adds it to the {@link GlassHandler}'s list.
	 */
	public void addGlass() 
	{
		int[] juicerCoords = juicer.getCoordinates();	
		glasses.add(new Glass(juicerCoords[0] - 100, juicerCoords[1]));
	}
	
	public List<Glass> getGlasses() {return glasses;}
}