package taxCollector;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;

import javax.swing.JFrame;
import javax.swing.Timer;

import Main.EJC_Interface;
import Main.WindowEventHandler;
import infdungeons.player.Player.Direction;
import taxCollector.MapHandler.Directions;

public class EJC_TaxCollector extends JFrame implements EJC_Interface
{
	private static final long serialVersionUID = -5389438519379536388L;
	private static final int index = 19;
	
	private TC_Panel panel;
	private HashSet<Integer> pressedKeys = new HashSet<>();
	private Timer timer;
	
	@Override
	public void start(WindowEventHandler eventHandler) 
	{
		this.addWindowListener(eventHandler);
		this.setTitle("Tax Collector");
		this.setResizable(false);
		
		panel = new TC_Panel();
		this.add(panel);
		this.pack();
		pressedKeys = new HashSet<>();
		
		this.addKeyListener(new KeyListener() 
		{
			@Override
			public void keyTyped(KeyEvent e)
			{}
			@Override
			public void keyPressed(KeyEvent e) 
			{
				int code = e.getKeyCode();
				pressedKeys.add(code);
				
				char c = e.getKeyChar();
				if (c == 'g') {panel.switchGridBool();}
				else if (c == 'c') {panel.collectAction();}
			}
			@Override
			public void keyReleased(KeyEvent e)
			{pressedKeys.remove(e.getKeyCode());}
		});

		this.setVisible(true);

		timer = new Timer(25, new ActionListener()
		{	@Override
			public void actionPerformed(ActionEvent e) 
			{
				if (pressedKeys.contains(68)) {panel.move(Directions.EAST);} 	//D
				else if (pressedKeys.contains(65)) {panel.move(Directions.WEST);} //A
				else if (pressedKeys.contains(87)) {panel.move(Directions.NORTH);} //W
				else if (pressedKeys.contains(83)) {panel.move(Directions.SOUTH);} 	//S

				panel.advanceFrame();
			}	
		});

		timer.setInitialDelay(100);
		timer.start();
	}

	@Override
	public void stop() 
	{
		timer.stop(); panel = null;
	}

	@Override
	public int getIndex() 
	{return index;}
}