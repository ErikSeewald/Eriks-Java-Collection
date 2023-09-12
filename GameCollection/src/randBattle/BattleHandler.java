package randBattle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;

public class BattleHandler implements ActionListener
{	
	private static final int FIGHTER_COUNT = 30;
	private ArrayList<Fighter> fighters;
	private boolean finished;
	
	private Timer timer;
	private Random random;
	private RB_Panel panel;
	private FighterBreeder breeder;
	
	BattleHandler(RB_Panel panel, int PANEL_WIDTH, int PANEL_HEIGHT)
	{
		this.panel = panel;
		random = new Random();
		breeder = new FighterBreeder(panel);
		
		fighters = new ArrayList<>();
		for (int i = 0; i < FIGHTER_COUNT; i++)
		{
			fighters.add(breeder.breedParentless());
		}
	}
	
	public void restart()
	{
		//FIGHTERS
		if (fighters.size() != FIGHTER_COUNT) // not first battle -> breed winners
		{
			ArrayList<Fighter> oldFighters = new ArrayList<>(fighters);
			fighters.removeAll(oldFighters);
			
			for (int i = 0; i < FIGHTER_COUNT; i++)
			{
				Fighter p1 = oldFighters.get(random.nextInt(fighters.size()));
				Fighter p2 = oldFighters.get(random.nextInt(fighters.size()));
				fighters.add(breeder.breedParents(p1, p2));
			}
		}
		
		for (Fighter f : fighters)
		{
			setNewTarget(f);
		}
		
		//TIMER
		if (timer != null) {timer.stop();}
		timer = new Timer(16, this);
		timer.start();
		
		finished = false;
	}
	
	private void setNewTarget(Fighter fighter)
	{
		Fighter target;
		do {target = fighters.get(random.nextInt(fighters.size()));}
		while (target == fighter);
		fighter.setTarget(target);
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
		
		for (Fighter f : fighters)
		{
			if (!f.isAlive) {continue;}
			
			if (!f.getTarget().isAlive) 
			{setNewTarget(f);}

			f.move();
			f.shoot();

			Fighter hitFighter = getHitFighter(f);
			if (hitFighter != null)
			{hitFighter.takeDamage(f.damage);}		
		}
		
		//remove dead fighters except for last 10
		//(can be less if more are eliminated in a single call - but there is no strict need
		// for it to be exactly 10)
		if (fighters.size() > 10)
		{
			fighters.removeIf(f -> !f.isAlive);
		}
		
	}
		
	private Fighter getHitFighter(Fighter fighter)
	{	
		for (Fighter hit : fighters)
		{
			if (hit == fighter) {continue;}
			
			if (fighter.projectile_x > hit.x && fighter.projectile_x < hit.x+hit.size
				&& fighter.projectile_y > hit.y && fighter.projectile_y < hit.y+hit.size)
			{return hit;}
		}
		return null;
	}
	
	private boolean finishCheck()
	{
		int aliveCount = 0;
		for (Fighter f : fighters)
		{
			if (f.isAlive)
			{++aliveCount;}
		}
		
		finished = aliveCount < 2;		
		return finished;
	}
	
	public void stopTimer() {timer.stop();}
	
	public ArrayList<Fighter> getNPCs() {return fighters;}
	
	public boolean hasFinished() {return finished;}
}