package cheeseBreeder;

import java.awt.Color;
import java.util.Random;

import cheeseBreeder.cheese.Cheese;
import cheeseBreeder.cheese.CheeseChild;

public class Breedery 
{
	Random random;
	
	public Breedery()
	{
		this.random = new Random();
	}
	
	public Cheese breed(Cheese c1, Cheese c2)
	{
		CheeseChild child = new CheeseChild(c1.x, c2.y);
		child.setName(breedName(c1, c2));
		breedColors(c1, c2, child);
		child.setRindSize(breedRindSize(c1, c2));
		breedHoleNumbers(c1, c2, child);
		child.generateHoles();
		
		child.setGrownUp(true, this);
		return child;
	}
	
	// NAME
	private String breedName(Cheese c1, Cheese c2)
	{
		String name = new String();
		int length1 = c1.getName().length(), length2 = c2.getName().length();
		int length = (length1 + length2) / 2; // guide length, not maximum
		Cheese chosenParent;
		
		// Randomly choose a letter from either parent for each position based on 4 cases
		for (int i = 0; i < length; i++)
		{	
			switch (random.nextInt(4))
			{
				// 1 Letter from c1
				case 0: chosenParent = c1; break;
				
				// 2 Letters from c1
				case 1: chosenParent = c1; if (i < length1) {name += chosenParent.getName().charAt(i); i++;} break;
				
				// 1 Letter from c2
				case 2: chosenParent = c2; break;
				
				// 2 Letters from c2
				default: chosenParent = c2; if (i < length2) {name += chosenParent.getName().charAt(i); i++;} break;
			}
			
			if (chosenParent.getName().length() <= i) {break;}
			name += chosenParent.getName().charAt(i);
		}
		
		return name;
	}
	
	
	// COLORS
	private void breedColors(Cheese c1, Cheese c2, CheeseChild child)
	{
		Color coreColor = mix(c1.getCoreColor(), c2.getCoreColor());
		Color rindColor = mix(c1.getRindColor(), c2.getRindColor());
		Color holeColor = mix(c1.getHoleColor(), c2.getHoleColor());
		
		child.setColors(coreColor, rindColor, holeColor);
	}
	
	private Color mix(Color c1, Color c2)
	{
		int r = mixColorValue(c1.getRed(), c2.getRed());
		int g = mixColorValue(c1.getGreen(), c2.getGreen());
		int b = mixColorValue(c1.getBlue(), c2.getBlue());
		
		return new Color(r, g, b);
	}
	
	private int mixColorValue(double a, double b)
	{
		int value = mixValueRandomly(a, b);
		if (value > 255) {return 255;}
		
		return value;
	}
	
	// RIND
	private int breedRindSize(Cheese c1, Cheese c2)
	{
		return mixValueRandomly(c1.getRindSize(), c2.getRindSize());
	}
	
	// HOLES
	private void breedHoleNumbers(Cheese c1, Cheese c2, CheeseChild child)
	{
		int hole_count_min = mixValueRandomly(c1.getHoleCountMin(), c2.getHoleCountMin());
		int hole_count_max = mixValueRandomly(c1.getHoleCountMax(), c2.getHoleCountMax());
		if (hole_count_max <= hole_count_min) {hole_count_max = hole_count_min + 1;}
		
		int hole_size_min = mixValueRandomly(c1.getHoleSizeMin(), c2.getHoleSizeMin());
		int hole_size_max = mixValueRandomly(c1.getHoleSizeMax(), c2.getHoleSizeMax());
		if (hole_size_max <= hole_size_min) {hole_size_max = hole_size_min + 1;}
		
		child.setHoleNumbers(hole_count_min, hole_count_max, hole_size_min, hole_size_max);
	}
	
	private int mixValueRandomly(double a, double b)
	{
		double factor = random.nextDouble(1.5);
		if (factor < 0.4) {factor = 0.4;}
		double second_factor = factor <= 1 ? (1 - factor) : 0;
		return (int) ((a * factor + b * second_factor));
	}
}
