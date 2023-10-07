package automata;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.Timer;
import ejcMain.EJC_Game;
import ejcMain.EJC_WindowEventHandler;

public class EJC_Automata extends JFrame implements EJC_Game
{
	private static final long serialVersionUID = -53838519379536388L;

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
		
		this.addKeyListener(new KeyListener()
		{
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) 
			{
				int code = e.getKeyCode();
				if (code == 82) {panel.randomSwitch();} //R
				else if (code == 83) {panel.switchPixelSize();} //S
			}

			@Override
			public void keyReleased(KeyEvent e) {}
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
}