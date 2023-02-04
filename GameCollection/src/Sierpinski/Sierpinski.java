package Sierpinski;
import javax.swing.JFrame;

import Main.EJC_Interface;
import Main.MainMenu;
import Main.WindowEventHandler;

public class Sierpinski extends JFrame implements EJC_Interface
{
	private static final long serialVersionUID = 5764966202240396499L;
	private static final int index = 2;
	
	private int speed = 0;
	private SierpinskiPanel panel;
	
	private WindowEventHandler eventHandler;
	
	public Sierpinski(WindowEventHandler eventHandler, int speed)
	{
		this.speed = speed;
		this.eventHandler = eventHandler;
		
		start();
	}	
	
	private void start()
	{
		this.setTitle("Sierpinski");
		this.setIconImage(MainMenu.img.getImage());
		this.addWindowListener(eventHandler);
		
		panel = new SierpinskiPanel(speed);
		this.add(panel);
		
		this.pack();
		this.setResizable(false);
		this.setVisible(true);
	}

	@Override
	public void start(WindowEventHandler eventHandler) {}

	@Override
	public void stop() {panel.stop();}
	
	@Override
	public int getIndex() {return index;}
}