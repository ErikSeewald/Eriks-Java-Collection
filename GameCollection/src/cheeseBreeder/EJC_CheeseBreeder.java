package cheeseBreeder;

import javax.swing.JFrame;
import Main.EJC_Interface;
import Main.WindowEventHandler;

public class EJC_CheeseBreeder extends JFrame implements EJC_Interface
{
	private static final long serialVersionUID = 4927061210031934469L;
	private static final int index = 18;

	private BreederPanel panel;
	
	@Override
	public void start(WindowEventHandler eventHandler) 
	{
		this.addWindowListener(eventHandler);
		this.setTitle("Cheese Breeder");
		this.setResizable(false);
		
		panel = new BreederPanel();
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