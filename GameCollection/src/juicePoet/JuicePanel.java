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
import javax.swing.JPanel;

import juicePoet.glass.Glass;
import juicePoet.glass.GlassHandler;
import juicePoet.juice.Juice;
import juicePoet.juice.Juicer;
import juicePoet.poem.Poem;
import juicePoet.poem.PoemHandler;

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
		this.juicer = new Juicer(510, 400);
		
		this.poemHandler = new PoemHandler();
		this.glassHandler = new GlassHandler(juicer);
		this.mouseHandler = new MouseHandler(poemHandler, glassHandler, juicer, this);
		
		this.addMouseListener(mouseHandler.new ClickListener());
		this.addMouseMotionListener(mouseHandler.new DragListener());
		this.addMouseListener(mouseHandler.new ReleaseListener());
	}
	
	public void addGlass()
	{
		glassHandler.addGlass();
		repaint();
	}
	
	public void addPoem()
	{
		poemHandler.addPoem();
		repaint();
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
	
	public static void drawPoem(Graphics2D g2D, Poem poem)
	{
		g2D.setPaint(poem_col);
		g2D.fillRect(poem.x-60, poem.y-40, 120, 80);
		
		g2D.setPaint(poem_col_darker);
		g2D.fillPolygon(
				new int[] {poem.x-60, poem.x, poem.x+60}, new int[] {poem.y-40, poem.y - 5, poem.y- 40}, 3);
	}
	
	public static void drawGlass(Graphics2D g2D, Glass glass)
	{		
		//JUICE
		if (!glass.isEmpty())
		{
			g2D.setPaint(getJuiceTexturePaint(glass.getJuice()));
			g2D.fillRect(glass.x - 50, glass.y - 100, 100, 150);
		}
		
		//GLASS
		g2D.setPaint(glass_col);
		g2D.setStroke(juicer_stroke);
		g2D.drawLine(glass.x - 50, glass.y + 50, glass.x - 50, glass.y - 100);
		g2D.drawLine(glass.x - 50, glass.y + 50, glass.x + 50, glass.y + 50);
		g2D.drawLine(glass.x + 50, glass.y + 50, glass.x + 50, glass.y - 100);
	}
	
	public static void drawJuicer(Graphics2D g2D, Juicer juicer)
	{
		int[] coords = juicer.getCoordinates();
		int x = coords[0];
		int y = coords[1];
		
		//BOTTOM
		g2D.setPaint(juicer_col);
		g2D.fillRect(x, y, 150, 75);
		
		//TEXT
		g2D.setPaint(text_col);
		g2D.setFont(textFont);
		
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.drawString("Juicer", x + 30, y + 50);
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		
		//JUICE
		if (!juicer.isEmpty())
		{
			Juice juice = juicer.getJuice();
			g2D.setPaint(getJuiceTexturePaint(juice));
			g2D.fillRect(x+15, y-130, 120, 130);
		}
		
		//GLASS
		g2D.setPaint(juicer_col);
		g2D.setStroke(juicer_stroke);
		g2D.drawLine(x+15, y, x+15, y-150);
		g2D.drawLine(x+135, y, x+135, y-150);
		
		//SLICER
		g2D.drawLine(x+75, y, x+75, y-30);
		g2D.drawLine(x+50, y-25, x+100, y-25);
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
		
		return new TexturePaint(juice.getTexture(), new Rectangle(0, 0, Juice.texture_size, Juice.texture_size));
	}
}
