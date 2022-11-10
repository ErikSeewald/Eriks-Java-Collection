package SnakesAndLadders;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import Main.MainMenu;
import Main.WindowEventHandler;

public class SnakesAndLadders extends JFrame 
{
	private static final long serialVersionUID = 7284705718024953236L;
	
	private static SnL_Panel panel;
	private SnL_GUI GUI;
	
	public SnakesAndLadders()
	{
		GUI = new SnL_GUI();
		panel = new SnL_Panel(GUI);
		GUI.addPanel(panel);
		
		this.add(panel, BorderLayout.WEST);
		this.add(GUI, BorderLayout.EAST);
		this.pack();
		
		this.setIconImage(MainMenu.img.getImage());
		this.setTitle("Snakes and Ladders");
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public void start(WindowEventHandler eventHandler)
	{this.addWindowListener(eventHandler);}
	
	public static void stop()
	{panel.stopAllTimers();}
}
