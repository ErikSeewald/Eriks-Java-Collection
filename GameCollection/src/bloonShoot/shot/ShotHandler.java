package bloonShoot.shot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import bloonShoot.BlS_Panel;
import bloonShoot.hittable.*;
import bloonShoot.level.LevelHandler;

public class ShotHandler implements ActionListener
{
	private Timer shot;
	private Projectile projectile = new Projectile();
	private Slingshot slingshot = new Slingshot();
	private BlS_Panel panel;
	
	public ShotHandler(BlS_Panel panel)
	{
		this.panel = panel;
		shot = new Timer(15,this);
		this.initialize();
	}
	
	public void initialize()
	{
		slingshot.initialize(panel.getDimensions()[0], panel.getDimensions()[1], panel.slingshotPixelSize);
		projectile.initialize(slingshot.getPullPoint(), panel.pixelSize);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{	
		handleShot();
		panel.repaint();
	}
	
	//SHOT HANDLER
	private void handleShot()
	{
		//SLING
		slingshot.moveSling();
		if(slingshot.isMoving()) 
		{
			projectile.setNewOrigin(slingshot.getPullPoint(), panel.pixelSize);
			return; //projectile is still attached -> no calculations yet
		}
		
		//PROJECTILE
		projectile.fly(panel.getDimensions()[1]);
		
		if (!projectile.isFlying()) //hit floor
		{stop(); return;}

		//COLLISION
		Hittable[] level = panel.levelHandler.getLevel();
		int[] gridEdges = getGridEdges(projectile.getOrigin());
		if (gridEdges== null) {return;}
		
		for (int i = 0; i < gridEdges.length; i++)
		{
			if (!LevelHandler.isHittable(gridEdges[i], level))
			{continue;}
			
			level[gridEdges[i]].hit();
			
			if (level[gridEdges[i]].getHittableID() == HittableIDs.boomballoon)
			{panel.levelHandler.boomCalc(gridEdges[i]);}

			projectile.hitReaction(level[gridEdges[i]].getHittableID());
			
			if (!projectile.isAlive())
			{stop(); return;}
		}
	}
	
	public void stop()
	{shot.stop(); projectile.initialize(slingshot.getPullPoint(), panel.pixelSize);}
	
	private int[] getGridEdges(int[] origin)
	{
		if (origin[0] < 0 || origin[0] > panel.getDimensions()[0] || origin[1] < 0 || origin[1] > panel.getDimensions()[1]) {return null;}
		
		int[] edges = new int[4];
		
		int CELL_SIZE = panel.getCellSize();
		int column = (origin[0]-CELL_SIZE) / CELL_SIZE, row = (origin[1]-CELL_SIZE) / CELL_SIZE;
		
		edges[0] = row * LevelHandler.CELL_COUNT_X + column; 			//  0_____1
		edges[1] = edges[0] + 1;								  		//  |	  |
		edges[2] = (row+1)* LevelHandler.CELL_COUNT_X+ column;			//  |_____|
		edges[3] = edges[2] + 1;								 		//  2	  3
														
		return edges;
	}
	
	//MOUSE HANDLER INTERACTION
	public void setDragValid(int x, int y) 
	{
		if (this.isRunning()) {return;}
		slingshot.setDragValid(x, y);
	}
	
	public void moveSling(int x, int y) 
	{
		if (!slingshot.isDragValid() || this.isRunning()) {return;}
		slingshot.setPullPoint(x, y);
		projectile.setNewOrigin(slingshot.getPullPoint(), panel.pixelSize);
	}
	
	public void releaseSling()
	{
		if (!slingshot.isDragValid() || this.isRunning()) {return;}
		slingshot.setReturnVect();
		projectile.setSpeed(slingshot.getReturnVect());
		shot.start();
	}
	
	//OTHER GETTERS
	public int[] getSlingshotOrigin() {return slingshot.getOrigin();}
	
	public int[] getSlingEdges() {return slingshot.getSlingEdges();}
	
	public int[] getPullPoint() {return slingshot.getPullPoint();}
	
	public int[] getProjectileOrigin() {return projectile.getOrigin();}
	
	public boolean isRunning() {return shot.isRunning();}
}