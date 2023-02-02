package Insects;

import java.awt.BorderLayout;
import javax.swing.JFrame;

import Main.MainMenu;
import Main.WindowEventHandler;

public class Insects
{
	private InsectsPanel panel;
	private Insects_GUI GUI;
	private JFrame frame;
	
	public Insects()
	{	
		panel = new InsectsPanel();
		GUI = new Insects_GUI(panel);
		panel.addGUI(GUI);
		panel.start();
		
		frame = new JFrame("Insects");
		frame.setIconImage(MainMenu.img.getImage());
		
		frame.setResizable(false);
		frame.add(GUI, BorderLayout.EAST);
		frame.add(panel, BorderLayout.WEST);
		frame.pack();

		frame.setVisible(true);	
	}
	
	public void start(WindowEventHandler eventHandler)
	{frame.addWindowListener(eventHandler);}
}
