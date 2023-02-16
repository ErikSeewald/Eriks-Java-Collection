package insects;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import Main.EJC_Interface;
import Main.WindowEventHandler;

public class EJC_Insects extends JFrame implements EJC_Interface
{
	private static final long serialVersionUID = -3887848730761835772L;
	private static final int index = 0;
	
	private InsectsPanel panel;
	private GUI GUI;
	
	@Override
	public void start(WindowEventHandler eventHandler)
	{
		this.addWindowListener(eventHandler);
		panel = new InsectsPanel();
		GUI = new GUI(panel);
		panel.addGUI(GUI);
		panel.start();
		
		this.add(GUI, BorderLayout.EAST);
		this.add(panel, BorderLayout.WEST);
		this.pack();

		this.setTitle("Insects");
		this.setResizable(false);
		this.setVisible(true);
	}

	@Override
	public void stop() {panel.stop(); panel = null; GUI = null;}
	
	@Override
	public int getIndex() {return index;}
}