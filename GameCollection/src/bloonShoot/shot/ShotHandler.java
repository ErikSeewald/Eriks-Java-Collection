package bloonShoot.shot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import bloonShoot.BlS_Panel;
import bloonShoot.hittable.*;
import bloonShoot.level.LevelHandler;

public class ShotHandler implements ActionListener
{
	public Timer shot;
	public Projectile projectile = new Projectile();
	public Slingshot slingshot = new Slingshot();
	private BlS_Panel panel;
	
	public ShotHandler(BlS_Panel panel)
	{
		this.panel = panel;
		shot = new Timer(15,this);	
		slingshot.initialize(panel.getDimensions()[0], panel.getDimensions()[1], panel.slingshotPixelSize);
		projectile.initialize(slingshot.getPullPoint(), panel.pixelSize);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{	
		if (slingshot.moveSling()) //MOVES SLINGSHOT WHILE ALSO CHECKING IF THE SLINGSHOT HAS STOPPED MOVING
		{projectile.setNewOrigin(slingshot.getPullPoint(), panel.pixelSize);}
		
		else
		{
			if (!projectile.fly(panel.getDimensions()[0])) //MOVES PROJECTILE WHILE ALSO CHECKING IF THE PROJECTILE HAS HIT THE FLOOR
			{shot.stop(); projectile.initialize(slingshot.getPullPoint(), panel.pixelSize);} //RESET PROJECTILE
			
			Hittable[] level = panel.levelHandler.level;
			
			//COLLISION DETECTION
			int[] gridEdges = getGridEdges(projectile.getOrigin()); //gets the grid indices of the 4 edges of the projectile
			
			if (gridEdges != null)
			{
				for (int i = 0; i < gridEdges.length; i++)
				{
					if (isHittable(gridEdges[i], level))
					{
						level[gridEdges[i]].hit(); 
						
						if (level[gridEdges[i]].getHittableID() == 5) //BoomBalloon
						{
							boomHit(gridEdges[i]);
						}
						
						//projectile does hit calculation and returns whether or not projectile is still alive
						if (!projectile.hitReaction(level[gridEdges[i]].getHittableID()))
						{shot.stop(); projectile.initialize(slingshot.getPullPoint(), panel.pixelSize);} //RESET PROJECTILE
					}
				}
			}
		} 
		panel.repaint();
	}
	
	private void boomHit(int gridEdge)
	{
		int[] edges = BoomBalloon.getHitEdges(gridEdge);
		Hittable[] level = panel.levelHandler.level;
		
		for (int edge : edges)
		{
			if (level[edge] != null)
			{
				if (level[edge].getHittableID() == 5 && !level[edge].isReacting())
				{level[edge].hit(); boomHit(edge);}
				level[edge].hit();
			}		
		}
		
	}
	
	private int[] getGridEdges(int[] origin)
	{
		if (origin[0] < 0 || origin[0] > panel.getDimensions()[0] || origin[1] < 0 || origin[1] > panel.getDimensions()[1]) {return null;}
		
		int[] edges = new int[4];
		
		int CELL_SIZE = panel.CELL_SIZE, CELL_COUNT_X = LevelHandler.CELL_COUNT_X;
		int column = (origin[0]-CELL_SIZE) / CELL_SIZE, row = (origin[1]-CELL_SIZE) / CELL_SIZE;
		
		edges[0] = row*CELL_COUNT_X + column; 				//  0_____1
		edges[1] = row*CELL_COUNT_X + column+1;  			//  |	  |
		edges[2] = (row+1)*CELL_COUNT_X + column;			//  |_____|
		edges[3] = (row+1)*CELL_COUNT_X + column+1; 		//  2	  3
														
		return edges;
	}
	
	private boolean isHittable(int gridNum, Hittable[] level)
	{	
		if (gridNum >= 1008 || gridNum < 0) {return false;}
		if (level[gridNum] == null) {return false;}
		
		return level[gridNum].isAlive();
	}
}
