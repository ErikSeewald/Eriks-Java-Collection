package RandBattle;

import java.awt.Color;
import java.awt.Dimension;
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
	
	private int NPC_COUNT = 20;
	private NPC[] NPCs;
	
	Timer timer;
	
	
	RB_Panel()
	{
		this.setPreferredSize(new Dimension( PANEL_WIDTH, PANEL_HEIGHT));
		start();
	}
	
	public void start()
	{
		//init NPCs
		NPCs = null;
		System.gc();
		NPCs = new NPC[NPC_COUNT];
		
		Random random = new Random();
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
			g2D.setColor(NPCs[i].getColor());
			
			int size = NPCs[i].getSize();
			g2D.fillRect((int)NPCs[i].getX(), (int)NPCs[i].getY(), size, size);
		}
		
		//PROJECTILES
		for (int i = 0; i < NPC_COUNT; i++)
		{
			g2D.setColor(Color.red);
			g2D.fillRect((int)NPCs[i].getProjectileX(), (int)NPCs[i].getProjectileY(), 5, 5);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		for (int i = 0; i < NPC_COUNT; i++)
		{
			NPCs[i].move();
			NPCs[i].shoot();
		}
		
		repaint();
	}
	
	public void stopTimer()
	{timer.stop();}

}
