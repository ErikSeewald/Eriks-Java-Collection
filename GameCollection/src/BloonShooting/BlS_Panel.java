package BloonShooting;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPanel;
import javax.swing.Timer;

public class BlS_Panel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 5219711456361037203L;
	
	private int PANEL_WIDTH = 1320;
	private int PANEL_HEIGHT = (int) (PANEL_WIDTH *0.6);
	
	private final Color BACKGROUND = new Color(50,50,60);
	
	//SLINGSHOT
	private Slingshot slingshot = new Slingshot();
	private Color[] slingshotColors = {Slingshot.Color1,Slingshot.Color2, Slingshot.Color3};
	private int slingshotPixelSize = PANEL_WIDTH/250;
	private boolean shiftMove = false; //IS THE SLINGSHOT ITSELF BEING MOVED -> SHIFT HELD DOWN
	
	//PROJECTILE
	private Projectile projectile = new Projectile();
	private Color[] projectileColors = {Projectile.Color1,Projectile.Color2,Projectile.Color3,Projectile.Color4,Projectile.Color5};
	private int projectilePixelSize = PANEL_WIDTH/450;
	
	//GRID
	private boolean gridVisible = false;
	
	private final int LINE_COUNT_X = 44; //for calculations and drawing the grid
	private final int LINE_COUNT_Y = 26;
	
	private final int CELL_COUNT_X = 42; //for the indices of the cells
	private final int CELL_COUNT_Y = 24;
	private final int CELL_COUNT = CELL_COUNT_X * CELL_COUNT_Y;
	
	private int CELL_SIZE = PANEL_WIDTH/LINE_COUNT_X;
	
	//coordinates of where the cells end on screen
	private int CELL_END_X = CELL_SIZE * (LINE_COUNT_Y-1); 
	private int CELL_END_Y = CELL_SIZE * (LINE_COUNT_X-1);
	
	//LEVEL
	int levelNum = 1;
	private byte[] levelRAW = new byte[CELL_COUNT]; //array containing the id of the element at each position
	
	private Hittable[] level = new Hittable[CELL_COUNT_X*CELL_COUNT_Y]; //array containing the actual element at each position
	private int levelPixelSize = PANEL_WIDTH/450;
	
	//SIMULATION
	private final Timer shot = new Timer(15,this);

	BlS_Panel()
	{
		this.setPreferredSize(new Dimension( PANEL_WIDTH, PANEL_HEIGHT));
		
		ClickListener clickListener = new ClickListener();
		DragListener dragListener = new DragListener();
		ReleaseListener releaseListener = new ReleaseListener();
		
		this.addMouseListener(clickListener);
		this.addMouseMotionListener(dragListener);
		this.addMouseListener(releaseListener);
		
		slingshot.initialize(PANEL_WIDTH, PANEL_HEIGHT, slingshotPixelSize);
		projectile.setPixelSize(projectilePixelSize); projectile.initialize(slingshot.getPullPoint());
		
		loadLevel(levelNum);
	}
	
	public void loadLevel(int levelNum)
	{	
		if(shot.isRunning()) {return;}
		
		byte[] levelRAWTEMP = Levels_Databox.loadLevel(levelNum);

		if (levelRAWTEMP == null) {return;}
		
		levelRAW = levelRAWTEMP;
		
		this.levelNum = levelNum;
		
		level = new Hittable[CELL_COUNT_X*CELL_COUNT_Y];
		
		int column = 1, row = 1;
		
		for (int i = 0; i < CELL_COUNT; i++)
		{
			if (column > CELL_COUNT_X) {row++; column = 1;} //go to the next row, reset to the first column
			
			switch (levelRAW[i])
			{
				case 1: level[i] = new Balloon(new int[] {column*CELL_SIZE,row*CELL_SIZE}, levelPixelSize);;
				break;
				case 2: level[i] = new Block(new int[] {column*CELL_SIZE,row*CELL_SIZE}, levelPixelSize);;
				break;
				case 3: level[i] = new BounceBlock(new int[] {column*CELL_SIZE,row*CELL_SIZE}, levelPixelSize);;
				break;
			}
			column++;
		}
		System.gc();
		repaint();
	}
	
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		super.paint(g);
		
		//BACKGROUND
		g2D.setPaint(BACKGROUND);
		g2D.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
		
		
		//SURROUNDING RECTANGLE
		g2D.setPaint(Color.LIGHT_GRAY);
		g2D.drawRect(CELL_SIZE, CELL_SIZE, CELL_END_Y-CELL_SIZE, CELL_END_X-CELL_SIZE); 
		
		//LEVEl
		for (int i = 0; i < CELL_COUNT; i++)
		{
			if (levelRAW[i] != 0)
			{
				if (level[i].isAlive())
				{
					if (level[i].isReacting())
					{paintSprite(level[i].getReactColors(), level[i].getReactSprite(), level[i].getOrigin(), levelPixelSize, g2D);}
					else
					{paintSprite(level[i].getColors(), level[i].getSprite(), level[i].getOrigin(), levelPixelSize, g2D);}
				}
			}
		}
		
		//GRID
		if (gridVisible)
		{
			g2D.setPaint(Color.LIGHT_GRAY);
			for (int i = 0; i < LINE_COUNT_X; i++)
			{g2D.drawLine(i*CELL_SIZE, CELL_SIZE, i*CELL_SIZE, CELL_END_X);}
			for (int i = 0; i < LINE_COUNT_Y; i++)
			{g2D.drawLine(CELL_SIZE, i*CELL_SIZE, CELL_END_Y, i*CELL_SIZE);}
		}
				
		//SLINGSHOT
		paintSprite(slingshotColors, Slingshot.SPRITE, slingshot.getOrigin(), slingshotPixelSize, g2D);
				
		//SLINGSHOT BAND
		g2D.setStroke(new BasicStroke(PANEL_WIDTH/150));
		g2D.setPaint(Slingshot.SlingColor);
		int[] paintOrigin = slingshot.getPaintOrigin();
		int[] pullPoint = slingshot.getPullPoint();
		g2D.drawLine(paintOrigin[0], paintOrigin[1], pullPoint[0], pullPoint[1]);
		g2D.drawLine(paintOrigin[2], paintOrigin[1], pullPoint[0], pullPoint[1]);
		
		//PROJECTILE
		paintSprite(projectileColors, Projectile.SPRITE, projectile.getOrigin(), projectilePixelSize, g2D);
	}
	
	private void paintSprite(Color[] colors, byte[] sprite, int [] origin, int pixelSize, Graphics2D g2D)
	{
		int row = 0, column = 0;
		
		for (int i = 0; i < 256; i++)
		{
			if (column == 16) {row++; column = 0;}
			
			if (sprite[i] != 0) //0 -> transparent
			{
				g2D.setPaint(colors[sprite[i]-1]);
				g2D.fillRect(origin[0] + column*pixelSize, origin[1]+ row*pixelSize, pixelSize, pixelSize);
			}
			column++;
		}
	}
	
	private class ClickListener extends MouseAdapter
	{
		public void mousePressed(MouseEvent e) 
		{
			if(!shot.isRunning())
			slingshot.setDragValid(e.getX(), e.getY()); //CHECK IF MOUSE IS INSIDE DRAG AREA
		}
	}
	   
	private class DragListener extends MouseMotionAdapter
	{
		public void mouseDragged(MouseEvent e) 
		{
			//DON'T MOVE IF NOT INSIDE DRAG AREA OR IF A SHOT IS STILL OCCURRING
			if (!slingshot.isDragValid() || shot.isRunning()) {return;} 
			
			//MOVE WHOLE SLINGSHOT
			if (e.isShiftDown()) 
			{slingshot.initOnNewCoords(e.getX()- slingshotPixelSize*8, e.getY()- slingshotPixelSize*8); shiftMove = true;}
			
			//MOVE SLINGSHOT BAND
			else 
			{slingshot.setPullPoint(e.getX(), e.getY());}
			
			projectile.setNewOrigin(slingshot.getPullPoint());
			repaint();
		}
	}
	
	private class ReleaseListener extends MouseAdapter
	{
		public void mouseReleased(MouseEvent e) 
		{
			//DON'T DO IF NOT INSIDE DRAG AREA OR IF A SHOT IS STILL OCCURRING
			if (!slingshot.isDragValid() || shot.isRunning()) {return;}
			if (shiftMove) {shiftMove = false; return;} //IF WHOLE SLING IS BEING MOVED, DON'T RELEASE PROJECTILE, JUST LET GO OF SLINGSHOT
			
			slingshot.setReturnVect();
			projectile.setSpeed(slingshot.getReturnVect());
			shot.start();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{	
		if (slingshot.moveSling()) //MOVES SLINGSHOT WHILE ALSO CHECKING IF THE SLINGSHOT HAS STOPPED MOVING
		{projectile.setNewOrigin(slingshot.getPullPoint());}
		
		else
		{
			if (!projectile.fly(PANEL_HEIGHT)) //MOVES PROJECTILE WHILE ALSO CHECKING IF THE PROJECTILE HAS HIT THE FLOOR
			{shot.stop(); projectile.initialize(slingshot.getPullPoint());} //RESET PROJECTILE
			
			
			//COLLISION DETECTION
			int[] gridEdges = getGridEdges(projectile.getOrigin()); //gets the grid indices of the 4 edges of the projectile
			
			if (gridEdges != null)
			{
				for (int i = 0; i < gridEdges.length; i++)
				{
					if (isHittable(gridEdges[i]))
					{
						level[gridEdges[i]].hit(); 
						
						//projectile does hit calculation and returns whether or not projectile is still alive
						if (!projectile.hitReaction(level[gridEdges[i]].getHittableID()))
						{shot.stop(); projectile.initialize(slingshot.getPullPoint());} //RESET PROJECTILE
					}
				}
			}
		} 
		repaint();
	}
	
	private int[] getGridEdges(int[] origin)
	{
		if (origin[0] < 0 || origin[0] > PANEL_WIDTH || origin[1] < 0 || origin[1] > PANEL_HEIGHT) {return null;}
		
		int[] edges = new int[4];
		
		int column = (origin[0]-CELL_SIZE) / CELL_SIZE, row = (origin[1]-CELL_SIZE) / CELL_SIZE;
		
		edges[0] = row*CELL_COUNT_X + column; 				//  0_____1
		edges[1] = row*CELL_COUNT_X + column+1;  			//  |	  |
		edges[2] = (row+1)*CELL_COUNT_X + column;			//  |_____|
		edges[3] = (row+1)*CELL_COUNT_X + column+1; 		//  2	  3
														
		return edges;
	}
	
	private boolean isHittable(int gridNum)
	{
		if (gridNum >= 1008 || gridNum < 0) {return false;}
		if (level[gridNum] == null) {return false;}
		
		return level[gridNum].isAlive();
	}
	
	public void changeGridVisibility()
	{gridVisible = !gridVisible; repaint();}
	
	public void changeSize(int amount)
	{
		//PANEL
		PANEL_WIDTH+=amount;
		PANEL_HEIGHT = (int) (PANEL_WIDTH *0.6);
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		
		//GRID
		CELL_SIZE = PANEL_WIDTH/LINE_COUNT_X;
		CELL_END_X = CELL_SIZE * (LINE_COUNT_Y-1);
		CELL_END_Y = CELL_SIZE * (LINE_COUNT_X-1);
		
		//OBJECTS
		slingshotPixelSize = PANEL_WIDTH/250;
		slingshot.initialize(PANEL_WIDTH, PANEL_HEIGHT, slingshotPixelSize);
		
		projectilePixelSize = PANEL_WIDTH/450;
		projectile.setPixelSize(projectilePixelSize); projectile.initialize(slingshot.getPullPoint());
		
		//LEVEL
		levelPixelSize = PANEL_WIDTH/450;
		loadLevel(levelNum);
		
		repaint();
	}

}