package randBattle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;

public class BattleHandler implements ActionListener
{	
	private static final int FIGHTER_COUNT = 30;
	private static final int SPAWN_TIME = 500;
	private int time_passed = 0;
	private ArrayList<Fighter> fighters;
	
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
		
		for (Fighter f : fighters)
		{setNewTarget(f);}
		timer = new Timer(16, this);
		timer.start();
	}
	
	private void spawnNewBatch()
	{
		ArrayList<Fighter> oldFighters = new ArrayList<>(fighters);
		
		for (int i = 0; i < FIGHTER_COUNT - oldFighters.size(); i++)
		{
			Fighter p1 = oldFighters.get(random.nextInt(oldFighters.size()));
			Fighter p2 = oldFighters.get(random.nextInt(oldFighters.size()));
			fighters.add(breeder.breedParents(p1, p2));
		}
		
		for (Fighter f : fighters)
		{setNewTarget(f);}
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
		time_passed++;
		if (time_passed >= SPAWN_TIME)
		{
			time_passed = 0;
			spawnNewBatch();
		}
		manageBattle();
		panel.repaint();
	}
	
	private void manageBattle()
	{	
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
		
		fighters.removeIf(f -> !f.isAlive);
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
	
	public void stop() {timer.stop();}
	
	public ArrayList<Fighter> getNPCs() {return fighters;}
}