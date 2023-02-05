package snakesAndLadders;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import Main.EJC_Interface;
import Main.WindowEventHandler;

public class SnakesAndLadders extends JFrame implements EJC_Interface
{
	private static final long serialVersionUID = 7284705718024953236L;
	private static final int index = 15;
	
	private SnL_Panel panel;
	private SnL_GUI GUI;
	
	public SnakesAndLadders()
	{
		GUI = new SnL_GUI();
		panel = new SnL_Panel(GUI);
		GUI.addPanel(panel);
		
		this.add(panel, BorderLayout.WEST);
		this.add(GUI, BorderLayout.EAST);
		this.pack();
		
		this.setTitle("Snakes and Ladders");
		this.setResizable(false);
		this.setVisible(true);
	}
	
	@Override
	public void start(WindowEventHandler eventHandler)
	{this.addWindowListener(eventHandler);}
	
	@Override
	public void stop()
	{panel.stopAllTimers(); panel = null;}
	
	@Override
	public int getIndex() {return index;}
}
