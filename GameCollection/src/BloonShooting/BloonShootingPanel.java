package BloonShooting;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class BloonShootingPanel extends JPanel
{
	private static final long serialVersionUID = 5219711456361037203L;
	
	int PANEL_WIDTH = 900;
	int PANEL_HEIGHT = 600;

	int PIXEL_SIZE = PANEL_WIDTH / 200;
	
	private final Color BACKGROUND = new Color(50,50,60);
	
	//SLINGSHOT
	private final Color SLING_LIGHT = new Color(110,125,170);
	private final Color SLING_DARK = new Color(95,110,160);
	private final Color SLING_BAND = new Color(55,75,110);
	
	private final byte[] SLING_SPRITE = 
	{
			1,2,2,0,0,0,0,0,0,0,0,0,0,0,1,1,
			1,1,1,2,0,0,0,0,0,0,0,0,0,1,1,2,
			0,1,1,1,2,0,0,0,0,0,0,0,1,1,2,2,
			0,0,1,1,1,2,0,0,0,0,0,1,1,2,2,0,
			0,0,0,1,1,1,2,0,0,0,0,1,2,2,0,0,
			0,0,0,0,1,1,2,2,0,0,1,1,2,0,0,0,
			0,0,0,0,1,1,2,2,0,0,1,1,2,0,0,0,
			0,0,0,0,0,1,1,1,2,1,1,2,2,0,0,0,
			0,0,0,0,0,0,1,1,2,1,1,2,0,0,0,0,
			0,0,0,0,0,0,1,1,1,1,2,2,0,0,0,0,
			0,0,0,0,0,0,0,1,1,1,2,0,0,0,0,0,
			0,0,0,0,0,0,0,1,1,1,2,0,0,0,0,0,
			0,0,0,0,0,0,0,1,1,1,2,0,0,0,0,0,
			0,0,0,0,0,0,2,1,1,1,2,0,0,0,0,0,
			0,0,0,0,0,2,1,1,1,1,1,2,0,0,0,0,
			0,0,0,0,2,2,2,2,2,2,2,2,2,0,0,0,
	};
	
	private int SLING_ORIGINX = PANEL_WIDTH/15;
	private int SLING_ORIGINY = PANEL_HEIGHT - (PANEL_HEIGHT/5);

	BloonShootingPanel()
	{
		this.setPreferredSize(new Dimension( PANEL_WIDTH, PANEL_HEIGHT));
	}
	
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		super.paint(g);
		
		//BACKGROUND
		g2D.setPaint(BACKGROUND);
		g2D.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
		
		//SLINGSHOT
		paintSprite(new Color[] {SLING_LIGHT,SLING_DARK}, SLING_SPRITE, g2D);
	}
	
	private void paintSprite(Color[] colors, byte[] sprite, Graphics2D g2D)
	{
		int row = 0;
		int column = 0;
		
		for (int i = 0; i < 16*16; i++)
		{
			if (column == 16) {row++; column = 0;}
			
			if (sprite[i] != 0)
			{
				g2D.setPaint(colors[sprite[i]-1]);
				g2D.fillRect(SLING_ORIGINX + column*PIXEL_SIZE, SLING_ORIGINY+ row*PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
			}
			
			column++;
		}
		
	}
}
