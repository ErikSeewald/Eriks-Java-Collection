package automata;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.Timer;
import ejcMain.EJC_Game;
import ejcMain.EJC_WindowEventHandler;

public class EJC_Automata extends JFrame implements EJC_Game
{
	private static final long serialVersionUID = -53838519379536388L;
	private static final int index = 20;

	private CellPanel panel;
	private Timer timer;
	
	@Override
	public void start(EJC_WindowEventHandler eventHandler) 
	{
		this.addWindowListener(eventHandler);
		this.setTitle("Cellular Automata");
		this.setResizable(false);
		
		panel = new CellPanel();
		this.add(panel);
		this.pack();
		
		timer = new Timer(25, new ActionListener()
		{	@Override
			public void actionPerformed(ActionEvent e) 
			{
				panel.update();
			}	
		});

		timer.setInitialDelay(100);
		timer.start();
		
		this.setVisible(true);
	}

	@Override
	public void stop() 
	{
		timer.stop();
		timer = null;
		
		panel.stop(); 
		panel = null;
	}

	@Override
	public int getIndex() 
	{return index;}
}