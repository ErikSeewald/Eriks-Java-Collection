package mouseDodge;

import java.util.Random;

public class Enemy
{
	double X, Y;
	private double goalX, goalY;
	private double vecX, vecY;

	private int stepsToGoal;
	private int steps;
	
	int size;
	int sizeDiv; //divider that decides the ratio of screen size to enemy size
	
	boolean isFilled;
	private double speed;
	private MouseDodgePanel panel;
	
	Enemy(Random random, double speed, MouseDodgePanel panel)
	{
		this.panel = panel;
		this.initialize(random, speed);
	}
	
	public void initialize(Random random, double speed)
	{
		int height = panel.getPanelSize(), width = (int) (panel.getPanelSize()*1.7);
		
		this.isFilled = panel.random.nextInt(4) == 1;
		this.speed = speed;

		this.sizeDiv = panel.random.nextInt(15)+15;
		this.size = height / this.sizeDiv;
		
		this.X = random.nextInt(width);
		this.Y = random.nextInt(height);
		
		this.goalX = random.nextInt(width);
		this.goalY = random.nextInt(height);
		
		generateVectors(speed);
	}
	
	public void generateVectors(double speed)
	{
		this.vecX = this.goalX - this.X;
		this.vecY = this.goalY - this.Y;
		
		double movelength = Math.sqrt(Math.pow(this.vecX, 2)+Math.pow(this.vecY, 2));
		this.vecX /= movelength*speed; 
		this.vecY /= movelength*speed;
		
		this.stepsToGoal = (int) (movelength*speed);
		this.steps = 0;
	}
	
	public void move(Random random)
	{
		if (this.steps > this.stepsToGoal) 
		{
			this.goalX = random.nextInt((int) (panel.getHeight()*1.5));
			this.goalY = random.nextInt(panel.getHeight());
			this.generateVectors(speed);
		}

		this.X+= this.vecX;
		this.Y+= this.vecY;

		this.steps++;
	}
}