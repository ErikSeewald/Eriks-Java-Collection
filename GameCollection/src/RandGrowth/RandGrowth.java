package RandGrowth;
import java.awt.BorderLayout;

import javax.swing.JFrame;

import Main.MainMenu;
import Main.WindowEventHandler;

public class RandGrowth extends JFrame
{
	private static final long serialVersionUID = -7593694436385018187L;
	
	static RandGrowthPanel panel;
	RG_GUI_Panel GUIpanel;
	
	public RandGrowth()
	{
		panel = new RandGrowthPanel();
		GUIpanel = new RG_GUI_Panel(panel);

		this.setIconImage(MainMenu.img.getImage());
		this.setTitle("Rand Growth");
		
		this.setLayout(new BorderLayout());
		this.add(GUIpanel, BorderLayout.EAST);
		this.add(panel, BorderLayout.WEST);
		this.pack();
		
		this.setResizable(false);
		this.setVisible(true);	
	}
	
	public static void stop()
	{panel.timer.stop();}
	
	public void start(WindowEventHandler eventHandler)
	{this.addWindowListener(eventHandler);}
}
