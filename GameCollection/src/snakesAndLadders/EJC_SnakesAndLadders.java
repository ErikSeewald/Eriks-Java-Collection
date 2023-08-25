package snakesAndLadders;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import Main.EJC_Interface;
import Main.menu.WindowEventHandler;
import snakesAndLadders.gui.SnL_GUI;

public class EJC_SnakesAndLadders extends JFrame implements EJC_Interface
{
	private static final long serialVersionUID = 7284705718024953236L;
	private static final int index = 15;
	
	private SnL_Panel panel;
	private SnL_GUI GUI;
	
	@Override
	public void start(WindowEventHandler eventHandler)
	{
		this.addWindowListener(eventHandler);
		this.setTitle("Snakes and Ladders");
		this.setResizable(false);
		
		GUI = new SnL_GUI();
		panel = new SnL_Panel(GUI);
		
		this.add(panel, BorderLayout.WEST);
		this.add(GUI, BorderLayout.EAST);
		this.pack();
		
		this.setVisible(true);
	}
	
	@Override
	public void stop()
	{panel.stop(); panel = null;}
	
	@Override
	public int getIndex() {return index;}
}