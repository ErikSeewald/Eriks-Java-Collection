package snakesAndLadders;

import java.awt.BorderLayout;
import javax.swing.JFrame;

import ejcMain.EJC_Game;
import ejcMain.EJC_WindowEventHandler;
import snakesAndLadders.gui.SnL_GUI;

public class EJC_SnakesAndLadders extends JFrame implements EJC_Game
{
	private static final long serialVersionUID = 7284705718024953236L;
	
	private SnL_Panel panel;
	private SnL_GUI GUI;
	
	@Override
	public void start(EJC_WindowEventHandler eventHandler)
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
}