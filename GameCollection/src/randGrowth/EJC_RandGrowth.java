package randGrowth;
import java.awt.BorderLayout;
import javax.swing.JFrame;

import Main.menu.WindowEventHandler;
import Main.EJC_Interface;

public class EJC_RandGrowth extends JFrame implements EJC_Interface
{
	private static final long serialVersionUID = -7593694436385018187L;
	private static final int index = 14;
	
	private RandGrowthPanel panel;
	private RG_GUI_Panel GUIpanel;
	
	@Override
	public void start(WindowEventHandler eventHandler)
	{
		this.addWindowListener(eventHandler);
		this.setTitle("Rand Growth");
		
		panel = new RandGrowthPanel();
		GUIpanel = new RG_GUI_Panel(panel.getGrowthHandler());
		this.setLayout(new BorderLayout());
		this.add(GUIpanel, BorderLayout.EAST);
		this.add(panel, BorderLayout.WEST);
		this.pack();
		
		this.setResizable(false);
		this.setVisible(true);	
	}
	
	@Override
	public void stop()
	{panel.stop(); panel = null;}
	
	@Override
	public int getIndex() {return index;}
}