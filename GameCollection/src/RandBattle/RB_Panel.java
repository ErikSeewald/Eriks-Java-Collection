package RandBattle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class RB_Panel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = -8706000019243206523L;
	
	private int PANEL_WIDTH = 1320;
	private int PANEL_HEIGHT = (int) (PANEL_WIDTH *0.6);
	
	private final Color BACKGROUND = new Color(50,50,60);
	
	private int NPC_COUNT = 30;
	private NPC[] NPCs;
	private boolean showStats = false;
	private boolean onlyShowHealth = false;
	
	Timer timer;
	
	private Random random;
	
	RB_Panel()
	{
		this.setPreferredSize(new Dimension( PANEL_WIDTH, PANEL_HEIGHT));
		random = new Random();
		start();
	}
	
	public void start()
	{
		//init NPCs
		NPCs = null;
		System.gc();
		NPCs = new NPC[NPC_COUNT];
		
		for (int i = 0; i < NPC_COUNT; i++)
		{NPCs[i] = new NPC(PANEL_WIDTH,PANEL_HEIGHT);}
		
		//set targets
		for (int i = 0; i < NPC_COUNT; i++)
		{
			int target = i;
			while (i == target) {target = random.nextInt(NPC_COUNT);}
			NPCs[i].setTarget(NPCs[target]);
		}
		
		//timer
		if (timer != null)
		{if (timer.isRunning()) {timer.stop();}}
		timer = new Timer(16, this);
		timer.start();
		
		repaint();
	}
	
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		
		//Background
		g2D.setPaint(BACKGROUND);
		g2D.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
		
		//NPCs
		for (int i = 0; i < NPC_COUNT; i++)
		{
			if (NPCs[i].isAlive)
			{
				if (NPCs[i].damageAnimation == 0) {g2D.setColor(NPCs[i].getColor());}
				else {g2D.setColor(new Color (220, 100, 100));}
				
				int size = NPCs[i].getSize();
				g2D.fillRect((int)NPCs[i].getX(), (int)NPCs[i].getY(), size, size);
			}
		}
		
		//PROJECTILES
		for (int i = 0; i < NPC_COUNT; i++)
		{
			if (NPCs[i].isAlive)
			{
				g2D.setColor(Color.red);
				g2D.fillRect((int)NPCs[i].getProjectileX()+2, (int)NPCs[i].getProjectileY()+2, 4, 4);
			}
		}
		
		//STATS
		if (!showStats && !onlyShowHealth) {return;}
		
		g2D.setPaint(Color.white);
		g2D.setFont(new Font("", Font.BOLD, 9));
		for (int i = 0; i <NPC_COUNT; i++)
		{
			if (NPCs[i].isAlive)
			{
				g2D.drawString(NPCs[i].getHealth()+ "hp", (int)NPCs[i].getX(), (int)NPCs[i].getY());
				
				if (!onlyShowHealth)
				{
					g2D.drawString(NPCs[i].getDamage()+ "dmg", (int)NPCs[i].getX(), (int)NPCs[i].getY()-10);
					g2D.drawString(round(NPCs[i].getMoveSpeed(),2)+ "speed", (int)NPCs[i].getX(), (int)NPCs[i].getY()-20);
					g2D.drawString(round(NPCs[i].getProjectileSpeed(),2)+ "dmg speed", (int)NPCs[i].getX(), (int)NPCs[i].getY()-30);
				}
			}
		}	
		
	}
	
	public static double round(double value, int places) 
	{
	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		for (int i = 0; i < NPC_COUNT; i++)
		{
			if (NPCs[i].isAlive)
			{
				NPCs[i].move();
				NPCs[i].shoot();
				
				int hitNum = getHitNum(i);
				if (hitNum != -1)
				{NPCs[hitNum].takeDamage(NPCs[i].getDamage());}
				
				//new target if old one died
				if (!NPCs[i].getTarget().isAlive) 
				{
					int target = i, controlSum = 0;
					while (i == target || !NPCs[target].isAlive) 
					{
						target = random.nextInt(NPC_COUNT);
						controlSum++;
						if (controlSum > 50) 
						{timer.stop(); break;}
					}
					NPCs[i].setTarget(NPCs[target]);
				}
			}
		}
		
		repaint();
	}
	
	private int getHitNum(int NPCnum)
	{
		double PROJECTILE_X = NPCs[NPCnum].getProjectileX();
		double PROJECTILE_Y = NPCs[NPCnum].getProjectileY();
		
		for (int i = 0; i < NPC_COUNT; i++)
		{
			if (i != NPCnum)
			{
				if (PROJECTILE_X > NPCs[i].getX() && PROJECTILE_X < NPCs[i].getX()+NPCs[i].getSize()
					&& PROJECTILE_Y > NPCs[i].getY() && PROJECTILE_Y < NPCs[i].getY()+NPCs[i].getSize())
				{return i;}
			}
		}
		return -1;
	}
	
	public void stopTimer()
	{timer.stop();}
	
	public void showStats()
	{showStats = !showStats;}
	public void onlyShowHealth()
	{onlyShowHealth = !onlyShowHealth;}
}
