package parallelUniverses;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import javax.swing.JFrame;
import javax.swing.Timer;

import ejcMain.EJC_Game;
import ejcMain.EJC_WindowEventHandler;

public class EJC_ParallelUniverses extends JFrame implements EJC_Game
{
	private static final long serialVersionUID = -728908076775898206L;
	
	private PU_Panel panel;
	private HashSet<Integer> pressedKeys = new HashSet<>();
	private Timer timer;
	
	@Override
	public void start(EJC_WindowEventHandler eventHandler) 
	{
		this.addWindowListener(eventHandler);
		this.setTitle("Parallel Universes");
		panel = new PU_Panel();
		this.add(panel);
		this.setResizable(false);
		this.pack();
		
		this.addKeyListener(new KeyListener() 
		{	
			@Override
			public void keyPressed(KeyEvent e) 
			{
				int code = e.getKeyCode();
				pressedKeys.add(code);
				
				if (code == 72) {panel.hideUniverses();} //H
			
			}
			@Override
			public void keyReleased(KeyEvent e) 
			{pressedKeys.remove(e.getKeyCode());}
			
			@Override
			public void keyTyped(KeyEvent e) {}
		});	
		
		timer = new Timer(25, new ActionListener()
		{	@Override
			public void actionPerformed(ActionEvent e) 
			{
				int x = 0, y = 0, moveCount = 1;

				if (pressedKeys.contains(68)) {x = 1;} 	//D
				if (pressedKeys.contains(65)) {x = -1;} 	//A
				if (pressedKeys.contains(87)) {y = -1;} 	//W
				if (pressedKeys.contains(83)) {y = 1;} 	//S

				if (pressedKeys.contains(16)) {moveCount = 4;}	//SHIFT

				if(x == 0 && y == 0)
				{return;}

				panel.movePlayer(x, y, moveCount);
			}	
		});

		timer.start();
		this.setVisible(true);
	}
	
	@Override
	public void stop()
	{timer.stop(); panel = null; pressedKeys = null;}
}