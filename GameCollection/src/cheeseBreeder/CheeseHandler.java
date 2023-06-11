package cheeseBreeder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import cheeseBreeder.cheese.Cheese;
import cheeseBreeder.cheese.ElementalCheeses.BlueCheese;
import cheeseBreeder.cheese.ElementalCheeses.Brownie;
import cheeseBreeder.cheese.ElementalCheeses.Camembert;
import cheeseBreeder.cheese.ElementalCheeses.Cheddar;
import cheeseBreeder.cheese.ElementalCheeses.Emmentaler;
import cheeseBreeder.cheese.ElementalCheeses.Gouda;
import cheeseBreeder.cheese.ElementalCheeses.Mozarella;
import cheeseBreeder.cheese.ElementalCheeses.PinkCheese;
import cheeseBreeder.cheese.ElementalCheeses.Pommier;

public class CheeseHandler 
{
	private Cheese prevSelectedCheese;
	private Cheese selectedCheese;
	private boolean dragging;
	
	private BreederPanel panel;
	private ArrayList<Cheese> cheeses;
	private Breedery breedery;
	
	private int prevX, prevY;
	
	CheeseHandler(BreederPanel panel)
	{
		this.panel = panel;
		
		cheeses = new ArrayList<>();
		breedery = new Breedery();		
	}
	
	public void spawnElementalCheese(String id)
	{
		if (id == null) {return;}
		
 		switch (id)
		{
			case "Emmentaler": cheeses.add(new Emmentaler(100, 100)); break;
			case "Gouda": cheeses.add(new Gouda(450, 100)); break;
			case "Camembert": cheeses.add(new Camembert(800, 100)); break;
			case "Cheddar": cheeses.add(new Cheddar(100, 400)); break;
			case "Mozarella": cheeses.add(new Mozarella(450, 400)); break;
			case "Blue Cheese": cheeses.add(new BlueCheese(800, 400)); break;
			case "Pink Cheese": cheeses.add(new PinkCheese(150, 150)); break;
			case "Pommier": cheeses.add(new Pommier(500, 150)); break;
			case "Brownie": cheeses.add(new Brownie(850, 150)); break;
		}
	}
	
	public void breed() 
	{
		if (prevSelectedCheese == null || selectedCheese == null) {return;}
		
		cheeses.add(breedery.breed(prevSelectedCheese, selectedCheese));
		
		cheeses.remove(prevSelectedCheese);
		prevSelectedCheese = null;
		
		cheeses.remove(selectedCheese);
		selectedCheese = null;
	}
	
	public void reset()
	{cheeses.clear();}
	
	public ArrayList<Cheese> getCheeses() {return cheeses;}

	public class ClickListener extends MouseAdapter
	{
		public void mousePressed(MouseEvent e) 
		{
			int x = e.getX(), y = e.getY();
			prevX = x;
			prevY = y;
			
			for (Cheese cheese : cheeses)
			{	
				if (x > cheese.x && x < cheese.x + Cheese.size && y > cheese.y && y < cheese.y + Cheese.size)
				{
					dragging = true;
					
					if (!cheese.equals(selectedCheese))
					{
						if (prevSelectedCheese != null) 
						{prevSelectedCheese.setSelected(false);}
						prevSelectedCheese = selectedCheese;
						
						selectedCheese = cheese;
						selectedCheese.setSelected(true);
					}
					
					return;
				}
			}
		}
	}

	public class DragListener extends MouseMotionAdapter
	{
		public void mouseDragged(MouseEvent e) 
		{
			if (selectedCheese == null || !dragging) {return;}

			int x = e.getX(), y = e.getY();
			
			selectedCheese.x += x - prevX;
			selectedCheese.y += y - prevY;
			
			prevX = x;
			prevY = y;
			panel.repaint();
		}
	}

	public class ReleaseListener extends MouseAdapter
	{
		public void mouseReleased(MouseEvent e) 
		{
			dragging = false;
		}
	}
}
