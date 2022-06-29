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
	private Color[] slingshotColors = {Slingshot.Color1,Slingshot.Color2};
	private int slingshotPixelSize = PANEL_WIDTH/250;
	private boolean shiftMove = false;
	
	//PROJECTILE
	private Projectile projectile = new Projectile();
	private Color[] projectileColors = {Projectile.Color1,Projectile.Color2,Projectile.Color3,Projectile.Color4};
	private int projectilePixelSize = PANEL_WIDTH/450;
	
	//MAP & GRID
	private boolean gridVisible = false;
	
	private final int LINE_COUNT_X = 44; //for calculations and drawing the grid
	private final int LINE_COUNT_Y = 26;
	
	private final int CELL_COUNT_X = 42; //for the indices of the cells
	private final int CELL_COUNT_Y = 24;
	private final int CELL_COUNT = CELL_COUNT_X * CELL_COUNT_Y;
	
	private int CELL_SIZE = PANEL_WIDTH/LINE_COUNT_X;
	
	private int CELL_END_X = CELL_SIZE * (LINE_COUNT_Y-1);
	private int CELL_END_Y = CELL_SIZE * (LINE_COUNT_X-1);
	
	byte[] levelRAW = new byte[CELL_COUNT];
	
	private Hittable[] level = new Hittable[CELL_COUNT_X*CELL_COUNT_Y];
	private int levelPixelSize = PANEL_WIDTH/450;
	
	//SIMULATION
	private final Timer shootTimer = new Timer(15,this);

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
		
		loadLevel(1);
	}
	
	private void loadLevel(int levelNum)
	{	
		levelRAW = Levels_Databox.loadLevel(levelNum);
		
		int column = 1, row = 1;
		
		for (int i = 0; i < CELL_COUNT; i++)
		{
			if (column > CELL_COUNT_X) {row++; column = 1;}
			
			switch (levelRAW[i])
			{
				case 1: level[i] = new Balloon(new int[] {column*CELL_SIZE,row*CELL_SIZE}, levelPixelSize);;
				break;
			}
			column++;
		}
	}
	
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		super.paint(g);
		
		//BACKGROUND
		g2D.setPaint(BACKGROUND);
		g2D.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
		
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
		
		//LEVEl
		for (int i = 0; i < CELL_COUNT; i++)
		{
			if (levelRAW[i] != 0)
			{paintSprite(level[i].getColors(), level[i].getSprite(), level[i].getOrigin(), levelPixelSize, g2D);}
		}	
	}
	
	private void paintSprite(Color[] colors, byte[] sprite, int [] origin, int pixelSize, Graphics2D g2D)
	{
		int row = 0, column = 0;
		
		for (int i = 0; i < 256; i++)
		{
			if (column == 16) {row++; column = 0;}
			
			if (sprite[i] != 0)
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
			if(!shootTimer.isRunning())
			slingshot.setDragValid(e.getX(), e.getY()); //CHECK IF MOUSE IS INSIDE DRAG AREA
		}
	}
	   
	private class DragListener extends MouseMotionAdapter
	{
		public void mouseDragged(MouseEvent e) 
		{
			//DON'T MOVE IF NOT INSIDE DRAG AREA OR IF A SHOT IS STILL OCCURRING
			if (!slingshot.isDragValid() || shootTimer.isRunning()) {return;} 
			
			//MOVE WHOLE SLINGSHOT
			if (e.isShiftDown()) 
			{slingshot.initOnNewCoords(e.getX(), e.getY()); shiftMove = true;}
			
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
			if (!slingshot.isDragValid() || shootTimer.isRunning()) {return;}
			if (shiftMove) {shiftMove = false; return;} //IF WHOLE SLING IS BEING MOVED, DON'T RELEASE PROJECTILE
			
			slingshot.setReturnVect(); slingshot.slingReturnRounds = 0;
			projectile.setSpeed(slingshot.getReturnVect());
			shootTimer.start();
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
			{shootTimer.stop(); projectile.initialize(slingshot.getPullPoint());} //RESET PROJECTILE
		} 
		repaint();
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
		
		repaint();
	}

}