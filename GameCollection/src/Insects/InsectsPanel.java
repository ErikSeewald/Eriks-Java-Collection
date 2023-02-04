package Insects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class InsectsPanel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 4974460083826443803L;
	
	private Timer timer; 	//movement
	private Timer survive;	//increasing or decreasing amount of ants
	
	//RESOURCES
	private int food = 0;	//how many are in which area
	private int water = 0;
	
	//ANTS
	private class Ant
	{
		int loc[] = {325,325};
		int size;
		Color color;
		
		Ant(int size)
		{this.size = size;}
	}
	
	int antAmount = 1000; //amount of ants at start
	private static final int maxAntAmount = 100000;
	private Ant[] ants = new Ant[maxAntAmount];
	private double lengthvar = 100 / (double)antAmount;
	
	private Color antColor;
	int changeAmount = 10;	//amount by which antAmount is changed in changeAntAmount()
	
	//WALLS
	private static class Wall
	{
		int topX, topY;
		int bottomX, bottomY;
		
		Wall(int tX, int tY, int bX, int bY)
		{this.topX = tX; this.topY = tY; this.bottomX = bX; this.bottomY = bY;}
	}
	
	private static final Wall[] walls = 
	{
			new Wall(245, 250, 245, 300), new Wall(245, 0, 245, 220), new Wall(0, 305, 245, 305), new Wall(200, 310, 200, 500),
			new Wall(200, 530, 500, 530), new Wall(250, 130, 500, 130), new Wall(530, 60, 530, 500), new Wall(100, 150, 100, 600),
			new Wall(100, 630, 600, 630), new Wall(600, 160, 600, 630), new Wall(50, 80, 450, 80)
	};
	
	//CONTROL
	private Random random = new Random();
	private Insects_GUI GUI;
	
	InsectsPanel()
	{
		this.setPreferredSize(new Dimension(650,650));
		this.setLayout(null);
		
		timer = new Timer(50,this);
		survive = new Timer(2500,this);
		survive.setInitialDelay(10000000/antAmount);	
	}
	
	//CONTROL
	public void addGUI(Insects_GUI GUI)
	{this.GUI = GUI;}
	
	public void stop()
	{timer.stop(); survive.stop(); ants = null;}
	
	public void start()
	{
		antAmount = 1000;

		ants = null;
		System.gc();
		ants = new Ant[maxAntAmount];
		
		antColor = new Color(random.nextInt(135)+120,random.nextInt(135)+120,random.nextInt(135)+120);
		
		for (int i = 0; i < ants.length; i++) 
		{
			ants[i] = new Ant(random.nextInt(3)+3);
		}
		GUI.setAntAmountCounter(antAmount);
		setColors();
		
		survive.start();
		timer.start();
	}
	
	public void paint(Graphics g) 
	{
		Graphics2D g2D = (Graphics2D) g;
		
		//BACKGROUND
		g2D.setPaint(new Color (60,60,75));
		g2D.fillRect(0, 0, 650, 650);
		
		//FOOD
		g2D.setPaint(new Color (100,70,70));
		g2D.fillRect(0, 145, 100, 460);
	
		//WATER
		g2D.setPaint(new Color (80,80,110));
		g2D.fillRect(600, 155, 50, 480);
		g2D.fillRect(45, 0, 200, 80);
		
		//SPAWN AREA
		g2D.setPaint(new Color (80,80,90));
		g2D.fillRect(250, 135, 278, 175);
		g2D.fillRect(205, 310,320, 215); 
		
		//WALLS
		g2D.setPaint(new Color (40,40,40));	
		g2D.setStroke(new BasicStroke(10));
		
		for (Wall wall : walls)
		{
			g2D.drawLine(wall.topX,wall.topY, wall.bottomX, wall.bottomY);
		}
		
		//ANTS
		for (int i = 0; i <=antAmount-1; i++) //drawing each ant
		{ 
			g2D.setPaint(ants[i].color);
			g2D.fillRect(ants[i].loc[0], ants[i].loc[1], ants[i].size, ants[i].size);
		}
	}	
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (antAmount == 0) {repaint(); return;}
		
		if (e.getSource()==timer) 
		{antMovement();}
	
		else if (e.getSource()==survive) 
		{survivalCalculation();}
	}
	
	//ANTS
	private void antMovement()
	{
		for (int i = antAmount-1; i >=0; i--) 
		{
			int locX = ants[i].loc[0], locY = ants[i].loc[1];
			
			switch (random.nextInt(5)) //choose from 5 different kinds of movements, at 10 different speeds
			{ 
				case 0: break;
				case 1: locX+=random.nextInt(10); break;
				case 2: locX-=random.nextInt(10); break;
				case 3: locY+=random.nextInt(10); break;
				case 4: locY-=random.nextInt(10); break;
			}

			//COLLISION CHECK
			if (locX > 0 && locX < 650 && locY > 0 && locY < 650)  // Is inside panel
			{
				if (!collisionCheck(locX, locY, ants[i].size)) //Not intersecting any wall
				{ants[i].loc[0] = locX; ants[i].loc[1] = locY;} 
			}
		}
		repaint();
	}
	
	private void survivalCalculation()
	{
		//check how many ants are in each resource area
		for (int i = 0; i < antAmount-1; i++)
		{
			int locX = ants[i].loc[0], locY = ants[i].loc[1];
			
			if ((locX > 0 && locX < 100 && locY > 145 && locY < 605)) 
			{food++;}
			
			else if ((locX > 45 && locX < 245 && locY > 0 && locY < 80)
					||locX > 600 && locX < 650 && locY > 155 && locY < 635) 
			{water++;}
		}

		double tempAntAmount = (antAmount - (0.001*(((antAmount-food-water)/(antAmount/10)) - (food/2 + water))));
		if (tempAntAmount < ants.length)
		{
			GUI.setChangeRate(tempAntAmount - antAmount);
			antAmount = (int) tempAntAmount;
		}
		
		GUI.setAntAmountCounter(antAmount);
		setColors();
	}
	
	private void setColors()
	{
		lengthvar = 100 / (double)antAmount;
		
		for (int i = 0; i < antAmount; i++) 
		{
			ants[i].color = new Color
			((int) (antColor.getRed()-(lengthvar*i)),(int) (antColor.getGreen()-(lengthvar*i)),(int) (antColor.getBlue()-(lengthvar*i)));
		}
	}
	
	private boolean collisionCheck(int x, int y, int size)
	{
		for (Wall wall : walls)
		{
			if 
			((x > wall.topX-6 &&  x < wall.bottomX+6 && y > wall.topY-6 && y < wall.bottomY+6) 
			|| (x+size > wall.topX-5 &&  x+size < wall.bottomX+5 && y+size > wall.topY-5 &&  y+size < wall.bottomY+5))
			
			{return true;}
		}
		return false;
	}
	
	public void changeAntAmount(boolean increase)
	{
		if (increase)
		{antAmount+=changeAmount;}
		else
		{antAmount-=changeAmount;}
		
		if (antAmount < 0) {antAmount = 0;}
		else if (antAmount > maxAntAmount) {antAmount = maxAntAmount;}
		GUI.setAntAmountCounter(antAmount);
		setColors();
	}
}