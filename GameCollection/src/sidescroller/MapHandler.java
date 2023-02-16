package sidescroller;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class MapHandler 
{
	private Random random;
	private LinkedList<Element> elements;
	
	private int scrollingSpeed;
	private int scrollAmount;
	
	MapHandler(LinkedList<Element> elements) 
	{this.elements = elements; random = new Random(); reset();}
	
	public void setSeed(long seed)
	{random.setSeed(seed);}
	
	public void reset()
	{scrollingSpeed = 1; scrollAmount = 0;}
	
	public int getScrollAmount() {return scrollAmount;}
	
	public void scroll()
	{
		if (elements.getLast().slot > 50) {scrollingSpeed = 2;}
		if (elements.getLast().slot > 300) {scrollingSpeed = 3;}
		
		scrollAmount-=scrollingSpeed;
	}
	
	public byte[] makeMap(int maplength)
	{
		byte[] map = new byte[maplength];
		
		for (int i = 1; i < maplength; i++)
		{
			int rand = random.nextInt(100);
			
			if (map[i-1] > 4 && random.nextBoolean()) {map[i] = (byte) (random.nextInt(2)+4);}
			//if the last one was floating, there is a 50% change the next one will be too	
			
			for (byte j = 0; j < 7; j++)
			{
				if (rand < Layouts.layoutChances[j]) 
				{map[i] = j; break;}
			}
		}
		
		return map;
	}
	
	public void updateElements(byte[] map)
	{		
		if (scrollAmount > -30) {return;}
		
		if (scrollAmount % 30 == 0)
		{
			elements.removeFirst();
			
			int slot = elements.getLast().slot + 1;
			if (slot < map.length) {elements.add(new Element(map[slot], slot));}
		}
	}
	
	public Element xCheck(int x, int y)
	{
		Iterator<Element> e = elements.iterator();
		while (e.hasNext())
		{
			Element element = e.next();
			if (x - Layouts.cubeSize > element.x || x + Layouts.cubeSize < element.x)
			{continue;} //skip all elements that aren't close
			
			if (hitboxCheck(element.layout.hitBox1, x, y)) {return element;}
			if (hitboxCheck(element.layout.hitBox2, x ,y)) {return element;}	
		}
		
		return null;
	}
	
	public int[] yCheck(int x, int y)
	{
		Iterator<Element> e = elements.iterator();
		while (e.hasNext())
		{
			Element element = e.next();
			if (x - Layouts.cubeSize +2 > element.x || x + Layouts.cubeSize < element.x)
			{continue;} //skip all elements that aren't close
			
			if (hitboxCheck(element.layout.hitBox1, x, y)) {return element.layout.hitBox1;}
			if (hitboxCheck(element.layout.hitBox2, x, y)) {return element.layout.hitBox2;}	
		}
		
		return null;
	}
	
	public boolean hitboxCheck(int[] hitbox, int x, int y)
	{
		if (hitbox == null) {return false;}
		
		return 		y >= hitbox[0] 				&& 		y < (hitbox[0] + hitbox[1])
				|| (y+Layouts.cubeSize) > hitbox[0] 	&& 		(y+Layouts.cubeSize) <= (hitbox[0] + hitbox[1]);		
	}
}