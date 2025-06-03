package juicePoet;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.TexturePaint;
import java.awt.Toolkit;

import javax.swing.JPanel;

import juicePoet.glass.Glass;
import juicePoet.glass.GlassHandler;
import juicePoet.juice.Juice;
import juicePoet.juice.Juicer;
import juicePoet.poem.Poem;
import juicePoet.poem.PoemHandler;

/**
 * Class extending {@link JPanel}. Handles all visualization of {@link EJC_JuicePoet}.
 */
public class JuicePanel extends JPanel
{
	private static final long serialVersionUID = -3619021760663049272L;
	
	private int PANEL_HEIGHT = 750;
	private int PANEL_WIDTH = (int) (PANEL_HEIGHT * 1.6);
	
	private Juicer juicer;
	private PoemHandler poemHandler;
	private GlassHandler glassHandler;
	private MouseHandler mouseHandler;

	JuicePanel()
	{
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		this.juicer = new Juicer((int) (PANEL_WIDTH * 0.43), (int) (PANEL_HEIGHT * 0.55));
		
		this.poemHandler = new PoemHandler();
		this.glassHandler = new GlassHandler(juicer);
		
		this.mouseHandler = new MouseHandler(poemHandler, glassHandler, juicer, this);
		this.addMouseListener(mouseHandler.new ClickListener());
		this.addMouseMotionListener(mouseHandler.new DragListener());
		this.addMouseListener(mouseHandler.new ReleaseListener());
	}
	
	/**
	 * Calls the {@link GlassHandler}'s addGlass method and handles repainting.
	 */
	public void addGlass()
	{
		glassHandler.addGlass();
		repaint();
	}
	
	/**
	 * Calls the {@link GlassHandler}'s deleteGlass method with the {@link MouseHandler}'s 
	 * last selected object and handles repainting.
	 */
	public void deleteGlass()
	{
		if (mouseHandler.getLastSelectedObject() instanceof Glass glass)
		{
			glassHandler.deleteGlass(glass);
			repaint();
		}
	}
	
	/**
	 * Calls the {@link PoemHandler}'s addPoem method and handles repainting.
	 */
	public void addPoem()
	{
		poemHandler.addPoem();
		repaint();
	}
	
	/**
	 * Helper method for {@link EJC_JuicePoet} {@link EJC_Game} stop method.
	 */
	public void stop()
	{
		juicer = null;
		poemHandler = null;
		glassHandler = null;
		mouseHandler = null;
	}
	
	//---------------------------------------PAINT---------------------------------------

	private static final Color background_col = new Color(55, 55, 70);
	private static final Color juicer_col = new Color(80, 80, 100);
	private static final Color poem_col = new Color(170, 170, 190);
	private static final Color poem_col_darker = new Color(140, 140, 160);
	private static final Color text_col = new Color(180, 180, 200);
	private static final Color glass_col = new Color(175, 175, 210);
	private static final Font textFont = new Font("", Font.BOLD, 28);
	private static final Stroke juicer_stroke = new BasicStroke(5);
	
	@Override
	public void paint(Graphics g)
	{
		Toolkit.getDefaultToolkit().sync(); // Force flush (for X11)
		Graphics2D g2D = (Graphics2D) g;
		
		//BACKGROUND
		g2D.setPaint(background_col);
		g2D.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
		
		//JUICER
		drawJuicer(g2D, juicer);
		
		//GLASSES
		for (Glass glass : glassHandler.getGlasses())
		{
			drawGlass(g2D, glass);
		}
		
		//POEMS
		for (Poem poem : poemHandler.getPoems())
		{
			drawPoem(g2D, poem);
		}
	}
	
	/**
	 * Draws the given {@link Poem} to the given {@link Graphics2D} context.
	 * 
	 * @param g2D the {@link Graphics2D} context to draw to
	 * @param poem the {@link Poem} to draw
	 */
	public static void drawPoem(Graphics2D g2D, Poem poem)
	{
		int halfWidth = Poem.WIDTH/2;
		int halfHeight = Poem.HEIGHT/2;
		
		int x = poem.getX();
		int y = poem.getY();
		
		g2D.setPaint(poem_col);
		g2D.fillRect(x - halfWidth, y - halfHeight, Poem.WIDTH, Poem.HEIGHT);
		
		g2D.setPaint(poem_col_darker);
		g2D.fillPolygon(
				new int[] {x - halfWidth, x, x + halfWidth}, 
				new int[] {y - halfHeight, y - 5, y - halfHeight}, 
				3);
	}
	
	/**
	 * Draws the given {@link Glass} to the given {@link Graphics2D} context.
	 * 
	 * @param g2D the {@link Graphics2D} context to draw to
	 * @param glass the {@link Glass} to draw
	 */
	public static void drawGlass(Graphics2D g2D, Glass glass)
	{	
		final int halfWidth = Glass.WIDTH/2;
		final int halfHeight = Glass.HEIGHT/2;
		final int y_offset = 20;
		
		int x = glass.getX();
		int y = glass.getY();
		
		//JUICE
		if (!glass.isEmpty())
		{
			g2D.setPaint(getJuiceTexturePaint(glass.getJuice()));
			g2D.fillRect(x - halfWidth, y - halfHeight + 15, Glass.WIDTH, Glass.HEIGHT - y_offset - 15);
		}
		
		//GLASS
		g2D.setPaint(glass_col);
		g2D.setStroke(juicer_stroke);
		
		g2D.drawLine(x - halfWidth, y + halfHeight - y_offset, 
				x - halfWidth, y - halfHeight); // Bottom left to top left
		
		g2D.drawLine(x - halfWidth, y + halfHeight - y_offset, 
				x + halfWidth, y + halfHeight - y_offset); // Bottom left to bottom right
		
		g2D.drawLine(x + halfWidth, y + halfHeight - y_offset, 
				x + halfWidth, y - halfHeight); // Bottom right to top right
	}
	
	/**
	 * Draws the given {@link Juicer} to the given {@link Graphics2D} context.
	 * 
	 * @param g2D the {@link Graphics2D} context to draw to
	 * @param juicer the {@link Juicer} to draw
	 */
	public static void drawJuicer(Graphics2D g2D, Juicer juicer)
	{
		int[] coords = juicer.getCoordinates();
		int x = coords[0];
		int y = coords[1];
		
		//BOTTOM
		g2D.setPaint(juicer_col);
		g2D.fillRect(x, y, Juicer.BOTTOM_WIDTH, Juicer.BOTTOM_HEIGHT);
		
		//TEXT
		g2D.setPaint(text_col);
		g2D.setFont(textFont);
		
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.drawString("Juicer", x + (int) (Juicer.BOTTOM_WIDTH * 0.22), y + (int) (Juicer.BOTTOM_HEIGHT * 0.66));
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		
		//JUICE
		int glassOffset = 15;
		int juiceOffset = 20;
		if (!juicer.isEmpty())
		{
			Juice juice = juicer.getJuice();
			g2D.setPaint(getJuiceTexturePaint(juice));
			g2D.fillRect(x + glassOffset, y - Juicer.GLASS_HEIGHT + juiceOffset, 
					Juicer.GLASS_WIDTH, Juicer.GLASS_HEIGHT - juiceOffset);
		}
		
		//GLASS
		g2D.setPaint(juicer_col);
		g2D.setStroke(juicer_stroke);
		g2D.drawLine(x + glassOffset, y, x + glassOffset, y - Juicer.GLASS_HEIGHT); // Bottom left to top left
		g2D.drawLine(x + Juicer.BOTTOM_WIDTH - glassOffset, y, 
				x + Juicer.BOTTOM_WIDTH - glassOffset, y - Juicer.GLASS_HEIGHT); // Bottom right to top right
		
		//SLICER
		int halfBottomWidth = Juicer.BOTTOM_WIDTH / 2;
		g2D.drawLine(x + halfBottomWidth, y, x + halfBottomWidth, y - 30);
		g2D.drawLine(x + 50, y - 25, x + 100, y - 25);
	}
	
	/**
	 * Creates a {@link TexturePaint} from the given {@link Juice} and returns it.
	 * 
	 * @param juice the {@link Juice} to create the {@link TexturePaint} from
	 * @return the new {@link TexturePaint}
	 */
	private static TexturePaint getJuiceTexturePaint(Juice juice)
	{
		if (juice == null) 
		{throw new IllegalArgumentException("Cannot get TexturePaint from null juice");}
		
		return new TexturePaint(juice.getTexture(), new Rectangle(0, 0, Juice.TEXTURE_SIZE, Juice.TEXTURE_SIZE));
	}
}
