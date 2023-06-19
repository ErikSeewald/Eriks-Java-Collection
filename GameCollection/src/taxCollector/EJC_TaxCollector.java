package taxCollector;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import Main.EJC_Interface;
import Main.WindowEventHandler;
import taxCollector.MapHandler.Directions;

public class EJC_TaxCollector extends JFrame implements EJC_Interface
{
	private static final long serialVersionUID = -5389438519379536388L;
	private static final int index = 19;
	
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
		
		this.addKeyListener(new KeyListener() 
		{
			@Override
			public void keyTyped(KeyEvent e)
			{	
				char c = e.getKeyChar();
				
				if (c == 'g') {panel.switchGridBool();}
				
				else if (c == 'w') {panel.move(Directions.NORTH);}
				else if (c == 'a') {panel.move(Directions.WEST);}
				else if (c == 's') {panel.move(Directions.SOUTH);}
				else if (c == 'd') {panel.move(Directions.EAST);}
			}
			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}		
		});

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