package randBattle;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

import ejcMain.EJC_Game;
import ejcMain.EJC_WindowEventHandler;

public class EJC_RandBattle extends JFrame implements EJC_Game
{
	private static final long serialVersionUID = 411705531616331949L;
	
	private RB_Panel panel;
	
	@Override
	public void start(EJC_WindowEventHandler eventHandler)
	{
		this.addWindowListener(eventHandler);
		this.setTitle("Rand Battle");
		panel = new RB_Panel();
		this.add(panel);
		this.pack();
		
		this.addKeyListener(new KeyListener() 
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{
				int code = e.getKeyCode();
				
				if (code == KeyEvent.VK_S) {panel.showStats();}
				if (code == KeyEvent.VK_H) {panel.onlyShowHealth();}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyTyped(KeyEvent e) {}
		});
		
		this.setResizable(false);
		this.setVisible(true);
	}
	
	@Override
	public void stop() {panel.stop(); panel = null;}
}