package bomb_sorting;

import javax.swing.JFrame;
import Main.EJC_Interface;
import Main.WindowEventHandler;

public class EJC_Bomb_Sorting extends JFrame implements EJC_Interface
{
	private static final long serialVersionUID = 7284705718024953236L;
	private static final int index = 16;
	
	private Sort_Panel panel;
	
	@Override
	public void start(WindowEventHandler eventHandler)
	{
		this.addWindowListener(eventHandler);
		this.setTitle("Bomb Sorting");
		this.setResizable(false);
		
		panel = new Sort_Panel();
		this.add(panel);
		this.pack();
		this.setVisible(true);
	}
	
	@Override
	public void stop()
	{panel.stop(); panel = null;}
	
	@Override
	public int getIndex() {return index;}
}