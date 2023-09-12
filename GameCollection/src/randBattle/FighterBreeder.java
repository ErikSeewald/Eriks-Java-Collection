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
		Fighter child = new Fighter();
		
		child.projectile_x = child.x = random.nextInt(RB_Panel.PANEL_WIDTH);
		child.projectile_y = child.y = random.nextInt(RB_Panel.PANEL_HEIGHT);
		
		child.goalX = (int) child.x;
		child.goalY = (int) child.y;
		
		return child;
	}
	
	public Fighter breedParentless()
	{
		Fighter child = constructChildBase();
		
		child.size = random.nextInt(20)+15;
		child.moveSpeed = random.nextDouble(2.0)+0.2;
		child.projectileSpeed = random.nextDouble(5)+5;
		child.damage = random.nextInt(90)+10;
		child.startingHealth = child.health = random.nextInt(900)+100;
		
		return child;
	}
	
	public Fighter breedParents(Fighter p1, Fighter p2)
	{
		Fighter child = constructChildBase();
		
		child.size = (int) mixAttributes(p1.size, p2.size);
		child.moveSpeed = mixAttributes(p1.moveSpeed, p2.moveSpeed);
		child.projectileSpeed = mixAttributes(p1.projectileSpeed, p2.projectileSpeed);
		child.damage = (int) mixAttributes(p1.damage, p2.damage);
		child.startingHealth = child.health = (int) mixAttributes(p1.health, p2.health);
		
		return child;
	}
	
	private double mixAttributes(double a1, double a2)
	{
		double factor = random.nextDouble();
		return a1 * factor + a2 * (1-factor);
	}
}
