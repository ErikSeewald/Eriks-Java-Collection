package randGrowth;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import Main.EJC_Interface;
import Main.WindowEventHandler;

public class EJC_RandGrowth extends JFrame implements EJC_Interface
{
	private static final long serialVersionUID = -7593694436385018187L;
	private static final int index = 14;
	
	private RandGrowthPanel panel;
	private RG_GUI_Panel GUIpanel;
	
	public EJC_RandGrowth()
	{
		panel = new RandGrowthPanel();
		GUIpanel = new RG_GUI_Panel(panel.getGrowthHandler());

		this.setTitle("Rand Growth");
		
		this.setLayout(new BorderLayout());
		this.add(GUIpanel, BorderLayout.EAST);
		this.add(panel, BorderLayout.WEST);
		this.pack();
		
		this.setResizable(false);
		this.setVisible(true);	
	}
	
	@Override
	public void start(WindowEventHandler eventHandler)
	{this.addWindowListener(eventHandler);}
	
	@Override
	public void stop()
	{panel.stop(); panel = null;}
	
	@Override
	public int getIndex() {return index;}
}