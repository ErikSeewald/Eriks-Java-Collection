package taxCollector;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import Main.EJC_Interface;
import Main.WindowEventHandler;
import cheeseBreeder.BreederPanel;

public class EJC_TaxCollector extends JFrame implements EJC_Interface
{
	private static final long serialVersionUID = -5389438519379536388L;
	private static final int index = 18;
	
	private TC_Panel panel;
	
	@Override
	public void start(WindowEventHandler eventHandler) 
	{
		this.addWindowListener(eventHandler);
		this.setTitle("Tax Collector");
		this.setResizable(false);
		
		panel = new TC_Panel();
		this.add(panel);
		this.pack();

		this.setVisible(true);

	}

	@Override
	public void stop() 
	{
		
	}

	@Override
	public int getIndex() 
	{return index;}
}