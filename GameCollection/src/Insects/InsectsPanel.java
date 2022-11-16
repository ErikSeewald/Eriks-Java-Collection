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
	
	private static Timer timer; 	//movement
	private static Timer survive;	//increasing or decreasing amount of ants
	
	private int food = 0;	//how many are in which area
	private int water = 0;
	
	private int antAmount = 1000; //amount of ants at start
	private double lengthvar = 100 / (double)antAmount;
	
	private int antBuffer = 50000;
	
	private int[] x = new int[antAmount+antBuffer];	//ants x and y numbers, the added number is how far the ants can increase
	private int[] y = new int[antAmount+antBuffer];
	private int[][] ants = {x, y};	//current ant locations
	
	private Color antColor;	
	private int[] Antsize = new int[antAmount+antBuffer];
	private int changeAmount = 10;		//amount by which antAmount is changed in changeAntAmount()
	
	private static final int wallamount = 11;
	private int[][] walls = new int [wallamount][4];
	
	private Random random;
	
	InsectsPanel()
	{
		this.setPreferredSize(new Dimension(650,650));
		this.setLayout(null);
		
		walls[0][0] = 245; walls[0][1] = 250; walls[0][2] = 245; walls[0][3] = 300;
		walls[1][0] = 245; walls[1][1] = 0; walls[1][2] = 245; walls[1][3] = 220;
		walls[2][0] = 0; walls[2][1] = 305; walls[2][2] = 245; walls[2][3] = 305;
		walls[3][0] = 200; walls[3][1] = 310; walls[3][2] = 200; walls[3][3] = 500;
		walls[4][0] = 200; walls[4][1] = 530; walls[4][2] = 500; walls[4][3] = 530;
		walls[5][0] = 250; walls[5][1] = 130; walls[5][2] = 500; walls[5][3] = 130;
		walls[6][0] = 530; walls[6][1] = 60; walls[6][2] = 530; walls[6][3] = 500;
		walls[7][0] = 100; walls[7][1] = 150; walls[7][2] = 100; walls[7][3] = 600;
		walls[8][0] = 100; walls[8][1] = 630; walls[8][2] = 600; walls[8][3] = 630;
		walls[9][0] = 600; walls[9][1] = 160; walls[9][2] = 600; walls[9][3] = 630;
		walls[10][0] = 50; walls[10][1] = 80; walls[10][2] = 450; walls[10][3] = 80;
		
		random = new Random();
		
		timer = new Timer(50,this); //movement
		timer.start();
		survive = new Timer(2500,this);
		survive.setInitialDelay(10000000/antAmount);
		survive.start();
		
		start();	
	}
	
	public static void stop()
	{timer.stop(); survive.stop();}
	
	public void start()
	{
		antAmount = 1000;
		
		for (int i = 0; i < x.length; i++) //setting the ants starting points
		{x[i]=325;y[i]=325;}

		antColor = new Color(random.nextInt(135)+120,random.nextInt(135)+120,random.nextInt(135)+120);
		
		for (int i = 0; i <antAmount+antBuffer; i++) 
		{Antsize[i] = random.nextInt(3)+3;}	
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
		
		for (int i = 0; i < wallamount; i++)
		{
			g2D.drawLine(walls[i][0],walls[i][1],walls[i][2],walls[i][3]);
		}
		
		//ANTS
		lengthvar = 100 / (double)antAmount;
		for (int i = 0; i <=antAmount-1; i++) //drawing each ant
		{ 
			g2D.setPaint(new Color 
			((int) (antColor.getRed()-(lengthvar*i)),(int) (antColor.getGreen()-(lengthvar*i)),(int) (antColor.getBlue()-(lengthvar*i))));
			
			g2D.fillRect(ants[0][i], ants[1][i], Antsize[i], Antsize[i]);
		}
		
		//COUNTER
		g2D.setPaint(Color.LIGHT_GRAY);
		g2D.fillRect(3, 4, 38, 15);
		
		g2D.setPaint(Color.BLACK);
		g2D.drawString(""+antAmount,5,15);
	}	
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource()==timer) 
		{
			for (int i = antAmount-1; i >=0; i--) 
			{
				int prevX = ants[0][i];
				int prevY = ants[1][i];	
				
				switch (random.nextInt(5)) //choose from 5 different kinds of movements, at 10 different speeds
				{ 
				case 0:
				break;
				case 1: ants[0][i]+=random.nextInt(10); 
				break;
				case 2: ants[0][i]-=random.nextInt(10);
				break;
				case 3: ants[1][i]+=random.nextInt(10);
				break;
				case 4: ants[1][i]-=random.nextInt(10);
				break;
				}
	
				//wall detection
				if (ants[0][i]>0 && ants[0][i]<650 && ants[1][i]>0 && ants[1][i]<650)  // Is inside panel
				{
					if (isInside(ants[0][i], ants[1][i], Antsize[i]))
					{ants[0][i] = prevX; ants[1][i] = prevY;} //resetting if at wall
				}
	
				else {ants[0][i] = prevX; ants[1][i] = prevY;} //resetting if outside frame
			}
			repaint();
		}
	
		else if (e.getSource()==survive) 
		{
			for (int i = 0; i < antAmount-1; i++)
			{
				//add number of ants in each area
				if ((ants[0][i] > 0 && ants[0][i] < 100 && ants[1][i] > 145 && ants[1][i] < 605)) 
				{food++;}
				
				else if ((ants[0][i] > 45 && ants[0][i] < 245 && ants[1][i] > 0 && ants[1][i] < 80)
						||ants[0][i] > 600 && ants[0][i] < 650 && ants[1][i] > 155 && ants[1][i] < 635) 
				{water++;}
			}
	
			int tempAntAmount = (int) (antAmount - (0.001*(((antAmount-food-water)/(antAmount/10)) - (food/2 + water))));
			if (tempAntAmount < x.length)
			{antAmount = tempAntAmount;}
			
			//decreases ant amount by 0.001 times the ants not in resource areas divided by the length/10 minus 1/2 the
			//amount of ants in food and in water
		}
	}
	
	private boolean isInside(int x, int y, int size)
	{
		for (int i = 0; i < wallamount; i++)
		{
			if (wallBounds(walls[i][0], walls[i][1], walls[i][2], walls[i][3],x,y,size)) {return true;}
		}
		return false;
	}
	
	private boolean wallBounds(int a, int b,  int a2,int b2,  int x, int y, int size)
	{
		return((x > a-6 &&  x < a2+6 && y > b-6 &&  y < b2+6) || (x+size > a-5 &&  x+size < a2+5 && y+size > b-5 &&  y+size < b2+5));
	}
	
	
	public void changeAntAmount(boolean increase)
	{
		if (increase && antAmount + changeAmount < x.length)
		{antAmount+=changeAmount;}
		else
		{antAmount-=changeAmount;}
	}
	
	public void changeAddAmount(boolean increase)
	{
		if (increase)
		{changeAmount*=2;}
		else if (changeAmount / 2 >= 1)
		{changeAmount/=2;}
	}
}