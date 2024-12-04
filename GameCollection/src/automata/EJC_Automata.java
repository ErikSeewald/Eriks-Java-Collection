package automata;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.Timer;
import ejcMain.EJC_Game;
import ejcMain.EJC_WindowEventHandler;

/**
 * EJC_Game implementation class for the automata project.
 * Extends {@link JFrame} and implements {@link EJC_Game}
 */
public class EJC_Automata extends JFrame implements EJC_Game
{
	private static final long serialVersionUID = -53838519379536388L;

	private CellPanel panel;
	private Timer timer;
	
	/**
	 * Starts this implementation of EJC_Game with the given {@link EJC_WindowEventHandler}.
	 * Creates a new window and sets up the necessary timers and key listeners.
	 * 
	 * @param eventHandler the {@link EJC_WindowEventHandler} to handle this instance
	 */
	@Override
	public void start(EJC_WindowEventHandler eventHandler) 
	{
		this.addWindowListener(eventHandler);
		this.setTitle("Cellular Automata");
		this.setResizable(false);
		
		panel = new CellPanel();
		this.add(panel);
		this.pack();
		
		timer = new Timer(30, new ActionListener()
		{	@Override
			public void actionPerformed(ActionEvent e) 
			{
				panel.update();
			}	
		});
		
		this.addKeyListener(new KeyListener()
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{
				int code = e.getKeyCode();
				if (code == KeyEvent.VK_R) {panel.randomSwitch();}
				else if (code == KeyEvent.VK_S) {panel.switchPixelSize();}
			}

			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyTyped(KeyEvent e) {}
		});

		timer.setInitialDelay(100);
		timer.start();
		
		this.setVisible(true);
	}

	/**
	 * {@link EJC_Automata} specific implementation of the {@link EJC_Game} stop method.
	 * Halts all timers and frees memory.
	 */
	@Override
	public void stop() 
	{
		timer.stop();
		timer = null;
		
		panel.stop(); 
		panel = null;
	}
}