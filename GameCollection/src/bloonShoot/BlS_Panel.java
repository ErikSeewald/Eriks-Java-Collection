package bloonShoot;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import bloonShoot.hittable.Hittable;
import bloonShoot.level.LevelHandler;
import bloonShoot.shot.*;

public class BlS_Panel extends JPanel
{
	public static final long serialVersionUID = 5219711456361037203L;
	
	private int PANEL_WIDTH = 1320;
	private int PANEL_HEIGHT = (int) (PANEL_WIDTH *0.6);
	
	public MouseHandler mouseHandler;
	public ShotHandler shotHandler;
	public LevelHandler levelHandler;

	BlS_Panel()
	{
		this.setPreferredSize(new Dimension( PANEL_WIDTH, PANEL_HEIGHT));
		
		shotHandler = new ShotHandler(this);
		mouseHandler = new MouseHandler(shotHandler, this);
		this.addMouseListener(mouseHandler.new ClickListener());
		this.addMouseMotionListener(mouseHandler.new DragListener());
		this.addMouseListener(mouseHandler.new ReleaseListener());
		
		levelHandler = new LevelHandler(this);
		levelHandler.loadLevel(1);
		repaint();
	}
	
	public int[] getDimensions()
	{return new int[] {PANEL_WIDTH, PANEL_HEIGHT};}
	
	public int getCellSize() {return CELL_SIZE;}
	
	public void changeLevel(int change) 
	{
		if (shotHandler.isRunning()) {return;}
		levelHandler.changeLevel(change); repaint();
	}
	
	public void changeGridVisibility()
	{gridVisible = !gridVisible; repaint();}
	
	private static final int[] validWidths = {705, 1250, 1320,2155};
	private int widthIndex = 2;
	
	public void changeSize(int amount)
	{
		widthIndex = (widthIndex + amount) % validWidths.length;
		if (widthIndex < 0) {widthIndex = validWidths.length -1;}
		
		PANEL_WIDTH = validWidths[widthIndex];
		PANEL_HEIGHT = (int) (PANEL_WIDTH *0.6);
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		
		CELL_SIZE = PANEL_WIDTH/LINE_COUNT_X;
		
		slingshotPixelSize = (PANEL_WIDTH+PANEL_HEIGHT)/400;
		pixelSize = (PANEL_WIDTH+PANEL_HEIGHT)/1000;
		shotHandler.initialize();
		
		levelHandler.changeLevel(0);;
		repaint();
	}
	
	//---------------------------------------PAINT---------------------------------------
	
	private static final Color background_col = new Color(50,50,60);
	
	//PIXEL SIZES
	public int slingshotPixelSize = (PANEL_WIDTH+PANEL_HEIGHT)/400;
	public int pixelSize = (PANEL_WIDTH+PANEL_HEIGHT)/1000;
	
	//GRID
	private boolean gridVisible = false;
	private static final int LINE_COUNT_X = 44;
	private static final int LINE_COUNT_Y = 26;
	private int CELL_SIZE = PANEL_WIDTH/LINE_COUNT_X;
	
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		
		//BACKGROUND
		g2D.setPaint(background_col);
		g2D.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
		
		//SURROUNDING RECTANGLE
		g2D.setPaint(Color.LIGHT_GRAY);
		g2D.drawRect(CELL_SIZE, CELL_SIZE, CELL_SIZE * (LINE_COUNT_X-2), CELL_SIZE * (LINE_COUNT_Y-2)); 
		
		//GRID
		if (gridVisible)
		{
			for (int i = 0; i < LINE_COUNT_X; i++)
			{g2D.drawLine(i*CELL_SIZE, CELL_SIZE, i*CELL_SIZE, CELL_SIZE * (LINE_COUNT_Y-1));}
			for (int i = 0; i < LINE_COUNT_Y; i++)
			{g2D.drawLine(CELL_SIZE, i*CELL_SIZE, CELL_SIZE * (LINE_COUNT_X-1), i*CELL_SIZE);}
		}
		
		//HITTABLE
		Hittable[] level = levelHandler.getLevel();
		for (int i = 0; i < LevelHandler.CELL_COUNT; i++)
		{
			if (!LevelHandler.isHittable(i, level))
			{continue;}
			
			if (level[i].isReacting())
			{paintSprite(level[i].getReactColors(), level[i].getReactSprite(), level[i].getOrigin(), pixelSize, g2D);}
			else
			{paintSprite(level[i].getColors(), level[i].getSprite(), level[i].getOrigin(), pixelSize, g2D);}
		}
				
		//SLINGSHOT
		paintSprite(Slingshot.sprite_palette, Slingshot.SPRITE, shotHandler.getSlingshotOrigin(), slingshotPixelSize, g2D);
				
		//SLINGSHOT BAND
		g2D.setStroke(new BasicStroke(PANEL_WIDTH/150));
		g2D.setPaint(Slingshot.rubber_col);
		int[] slingEdges = shotHandler.getSlingEdges(), pullPoint = shotHandler.getPullPoint();
		g2D.drawLine(slingEdges[0], slingEdges[1], pullPoint[0], pullPoint[1]);
		g2D.drawLine(slingEdges[2], slingEdges[1], pullPoint[0], pullPoint[1]);
		
		//PROJECTILE
		paintSprite(Projectile.sprite_palette, Projectile.SPRITE, shotHandler.getProjectileOrigin(), pixelSize, g2D);
	}
	
	private void paintSprite(Color[] colors, byte[] sprite, int [] origin, int pixelSize, Graphics2D g2D)
	{
		short row = 0, column = 0;
		for (short i = 0; i < 256; i++)
		{
			if (column == 16) {row++; column = 0;}
			if (sprite[i] != 0)
			{
				short startCol = column;
				while (column < 15) //extend further while color is the same to draw a wide rectangle instead of multiple single squares
				{
					if (sprite[i] == sprite[i+1]) {i++; column++;}
					else {break;}
				}
				
				g2D.setPaint(colors[sprite[i]-1]);
				g2D.fillRect(origin[0] + startCol*pixelSize, origin[1]+ row*pixelSize, pixelSize * (column - startCol + 1), pixelSize);
			}
			column++;
		}
	}
}