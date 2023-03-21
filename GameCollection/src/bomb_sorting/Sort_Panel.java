package bomb_sorting;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Sort_Panel extends JPanel
{
	private static final long serialVersionUID = -7304963195064748685L;
	private int PANEL_HEIGHT = 700;
	private int PANEL_WIDTH = (int) (PANEL_HEIGHT * 1.5);
	
	// PLATES
	private int plate_width;
	private int plate_height;
	private int plate_x_left;
	private int plate_x_right;
	private int plate_y;
	
	Sort_Panel()
	{
		this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
		this.init_sizes();
	}
	
	public void init_sizes()
	{
		plate_width = (int) (PANEL_WIDTH / 3.5);
		plate_height = PANEL_HEIGHT / 2;
		plate_x_left = PANEL_HEIGHT / 40;
		plate_x_right = PANEL_WIDTH - plate_width - plate_x_left;
		plate_y = (int) (PANEL_HEIGHT / 2.6);
	}
	
	public void stop()
	{}
	
	//---------------------------------------PAINT---------------------------------------
	
	private static final Color background_col = new Color(50, 50, 60);
	private static final Color border_col = new Color(80, 80, 90);
	private static final Color left_plate_col = new Color(205, 70, 75);
	private static final Color right_plate_col = new Color(30, 30, 35);
	
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		
		//BACKGROUND
		g2D.setPaint(background_col);
		g2D.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
		
		
		//PLATES
		g2D.setPaint(left_plate_col);
		g2D.fillRect(plate_x_left, plate_y, plate_width, plate_height);
		

		g2D.setPaint(right_plate_col);
		g2D.fillRect(plate_x_right, plate_y, plate_width, plate_height);
		
		//PLATE BORDERS
		g2D.setStroke(new BasicStroke(PANEL_HEIGHT / 60));
		g2D.setPaint(border_col);
		g2D.drawRect(plate_x_left, plate_y, plate_width, plate_height);
		g2D.drawRect(plate_x_right, plate_y, plate_width, plate_height);
		
	}
}
