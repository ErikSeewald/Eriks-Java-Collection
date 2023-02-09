package insects;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;

public class Simulation implements ActionListener
{	
	private InsectsPanel panel;
	
	private Ant[] ants = new Ant[Simulation.maxAntAmount];
	private Color antColor;
	
	private boolean isRunning = false;
	
	Simulation(int antAmount, InsectsPanel panel)
	{
		this.antAmount = antAmount; this.panel = panel;
	}
	
	public void start()
	{
		if (this.isRunning) {return;}
		
		for (int i = 0; i < ants.length; i++) 
		{ants[i] = new Ant(random.nextInt(3)+3);}
		
		antColor = new Color(random.nextInt(135)+120,random.nextInt(135)+120,random.nextInt(135)+120);
		setColors();
		
		survival_timer.start();
		movement_timer.start();
		
		this.isRunning = true;
	}
	
	public void stop()
	{ants = null; panel = null; movement_timer.stop(); survival_timer.stop(); movement_timer = null; survival_timer = null; isRunning = false;}
	
	public int getAntAmount() {return antAmount;}
	
	public Ant getAntCopy(int i) 
	{
		Ant ant = new Ant(ants[i].size); 
		ant.loc = ants[i].loc;
		ant.color = ants[i].color;
		return ant;
	}
	
	public void setColors()
	{
		double lengthvar = 100 / (double)antAmount;
		for (int i = 0; i < antAmount; i++) 
		{
			ants[i].color = new Color
					((int) (antColor.getRed()-(lengthvar*i)),(int) (antColor.getGreen()-(lengthvar*i)),(int) (antColor.getBlue()-(lengthvar*i)));
		}
	}
	
	//---------------------------------------SIMULATION---------------------------------------
	
	private Random random = new Random();
	
	private int total_water = 0;
	private int total_food = 0;

	private int antAmount;
	public static final int maxAntAmount = 100000;
	
	private Timer movement_timer = new Timer(50,this);
	private Timer survival_timer = new Timer(2500,this);
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource()==movement_timer && antAmount > 0) 
		{antMovement();}
	
		else if (e.getSource()==survival_timer && antAmount > 0) 
		{survivalCalculation();}
		
		panel.repaint();
	}
	
	public static int toValidAntAmount(int amount)
	{
		if (amount < 0) {amount = 0;}
		else if (amount > maxAntAmount) {amount = maxAntAmount;}
		return amount;
	}
	
	public void changeAntAmount(int amount)
	{
		this.antAmount = Simulation.toValidAntAmount(antAmount + amount);
		
		panel.GUI.setAntAmountCounter(antAmount);
		if (amount > 0) {setColors();}
	}
	
	private void antMovement()
	{
		for (int i = antAmount-1; i >=0; i--) 
		{ants[i].move(random);}
	}

	private void survivalCalculation()
	{
		for (int i = 0; i < antAmount-1; i++)
		{
			total_food+= ants[i].gettingFood();
			total_water+= ants[i].gettingWater();
		}

		double tempAntAmount = (antAmount - (0.001*(((antAmount-total_food-total_water)/(antAmount/10)) - (total_food/2 + total_water))));
		if (tempAntAmount < ants.length)
		{
			panel.GUI.setChangeRate(tempAntAmount - antAmount);
			changeAntAmount((int) tempAntAmount - antAmount);
		}
	}
}