package insects;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class InsectsPanel extends JPanel
{
	private static final long serialVersionUID = 4974460083826443803L;
	public static final int PANEL_SIZE = 650;
	
	GUI GUI;	
	Simulation simulation;
	
	InsectsPanel()
	{
		this.setPreferredSize(new Dimension(PANEL_SIZE,PANEL_SIZE));
		this.setLayout(null);
	}
	
	public void addGUI(GUI GUI)
	{this.GUI = GUI;}
	
	public void stop()
	{if (!(simulation == null)) {simulation.stop();} simulation = null;}
	
	public void start()
	{	
		int antAmount = Simulation.toValidAntAmount(1000);
		GUI.setAntAmountCounter(antAmount);
		
		//SIMULATION
		this.stop(); System.gc();
		simulation = new Simulation(antAmount, this);
	}
	
	public void paint(Graphics g) 
	{
		Graphics2D g2D = (Graphics2D) g;
		
		//BACKGROUND
		g2D.setPaint(new Color (60,60,75));
		g2D.fillRect(0, 0, 650, 650);
		
		//FOOD
		g2D.setPaint(new Color (100,70,70));
		g2D.fillRect(Map.food[0], Map.food[1], Map.food[2], Map.food[3]);
	
		//WATER
		g2D.setPaint(new Color (80,80,110));
		g2D.fillRect(Map.water_1[0], Map.water_1[1], Map.water_1[2], Map.water_1[3]);
		g2D.fillRect(Map.water_2[0], Map.water_2[1], Map.water_2[2], Map.water_2[3]);
		
		//SPAWN AREA
		g2D.setPaint(new Color (80,80,90));
		g2D.fillRect(250, 135, 278, 175);
		g2D.fillRect(205, 310,320, 215); 
		
		//WALLS
		g2D.setPaint(new Color (40,40,40));	
		g2D.setStroke(new BasicStroke(10));
		
		for (Map.Wall wall : Map.walls)
		{g2D.drawLine(wall.topX,wall.topY, wall.bottomX, wall.bottomY);}
		
		//ANTS
		for (int i = 0; i < simulation.getAntAmount(); i++)
		{
			g2D.setPaint(simulation.getAntColor(i));
			
			int[] ant = simulation.getXYandSize(i);
			g2D.fillRect(ant[0], ant[1], ant[2], ant[2]);
		}
	}
}