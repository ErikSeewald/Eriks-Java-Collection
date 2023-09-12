package randBattle;

import java.util.Random;

public class FighterBreeder 
{
	private Random random;
	
	public FighterBreeder(RB_Panel panel)
	{
		this.random = new Random();
	}
	
	private Fighter constructChildBase()
	{
		Fighter child = new Fighter(random);
		
		child.projectile_x = child.x = random.nextInt(RB_Panel.PANEL_WIDTH);
		child.projectile_y = child.y = random.nextInt(RB_Panel.PANEL_HEIGHT);
		
		child.goalX = (int) child.x;
		child.goalY = (int) child.y;
		
		return child;
	}
	
	public Fighter breedParentless()
	{
		Fighter child = constructChildBase();
		
		child.moveSpeed = random.nextDouble(2.0)+0.2;
		child.projectileSpeed = random.nextDouble(5)+5;
		child.damage = random.nextInt(40)+10;
		child.startingHealth = child.health = random.nextInt(600)+100;
		
		return child;
	}
	
	public Fighter breedParents(Fighter p1, Fighter p2)
	{
		Fighter child = constructChildBase();
		
		//MOVE SPEED
		double moveSpeed = mixAttributes(p1.moveSpeed, p2.moveSpeed);
		child.moveSpeed = mutate(moveSpeed);
		
		//PROJECTILE SPEED
		double projectileSpeed = mixAttributes(p1.projectileSpeed, p2.projectileSpeed);
		child.projectileSpeed = mutate(projectileSpeed);
		
		//DAMAGE
		int damage = (int) mixAttributes(p1.damage, p2.damage);
		child.damage = (int) mutate(damage);
		
		//HEALTH
		int health = (int) mixAttributes(p1.startingHealth, p2.startingHealth);
		child.startingHealth = child.health = (int) mutate(health);
		
		return child;
	}
	
	private double mutate(double a)
	{
		double m = a + random.nextDouble(a/8) - a/16;
		if (m <= 0) {return a;}
		return m;
	}
	
	private double mixAttributes(double a1, double a2)
	{
		double factor = random.nextDouble();
		return a1 * factor + a2 * (1-factor);
	}
}
