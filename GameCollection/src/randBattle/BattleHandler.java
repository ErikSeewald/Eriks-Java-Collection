package randBattle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.Timer;

public class BattleHandler implements ActionListener
{	
	private int NPC_COUNT = 30;
	private NPC[] NPCs;
	private boolean finished;
	
	private Timer timer;
	private Random random;
	private RB_Panel panel;
	
	BattleHandler(RB_Panel panel, int PANEL_WIDTH, int PANEL_HEIGHT)
	{
		this.panel = panel;
		random = new Random();
		
		NPCs = new NPC[NPC_COUNT];
		for (int i = 0; i < NPC_COUNT; i++)
		{NPCs[i] = new NPC(PANEL_WIDTH,PANEL_HEIGHT, random);}
	}
	
	public void start()
	{
		//NPCS
		for (NPC npc : NPCs)
		{
			npc.reset();
			setNewTarget(npc);
		}
		
		//TIMER
		if (timer != null) {timer.stop();}
		timer = new Timer(16, this);
		timer.start();
		
		finished = false;
	}
	
	private void setNewTarget(NPC npc)
	{
		NPC target;
		do {target = NPCs[random.nextInt(NPC_COUNT)];}
		while (target == npc);
		npc.setTarget(target);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		manageBattle();
		panel.repaint();
	}
	
	private void manageBattle()
	{
		if (finishCheck()) {timer.stop(); return;}
		
		for (NPC npc : NPCs)
		{
			if (!npc.isAlive) {continue;}
			
			if (!npc.getTarget().isAlive) 
			{setNewTarget(npc);}

			npc.move();
			npc.shoot();

			NPC hitNPC = getHitNPC(npc);
			if (hitNPC != null)
			{hitNPC.takeDamage(npc.DAMAGE);}		
		}	
	}
		
	private NPC getHitNPC(NPC npc)
	{	
		for (NPC hitNPC : NPCs)
		{
			if (hitNPC == npc) {continue;}
			
			if (npc.PROJECTILE_X > hitNPC.x && npc.PROJECTILE_X < hitNPC.x+hitNPC.SIZE
				&& npc.PROJECTILE_Y > hitNPC.y && npc.PROJECTILE_Y < hitNPC.y+hitNPC.SIZE)
			{return hitNPC;}
		}
		return null;
	}
	
	private boolean finishCheck()
	{
		int aliveCount = 0;
		for (NPC npc : NPCs)
		{
			if (npc.isAlive)
			{++aliveCount;}
		}
		
		finished = aliveCount < 2;		
		return finished;
	}
	
	public void stopTimer() {timer.stop();}
	
	public NPC[] getNPCs() {return NPCs;}
	
	public boolean hasFinished() {return finished;}
}