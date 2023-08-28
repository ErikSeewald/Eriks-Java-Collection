package randBattle;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

import Main.menu.WindowEventHandler;
import Main.EJC_Interface;

public class EJC_RandBattle extends JFrame implements EJC_Interface
{
	private static final long serialVersionUID = 411705531616331949L;
	private static final int index = 13;
	
	private RB_Panel panel;
	
	@Override
	public void start(WindowEventHandler eventHandler)
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
				
				if (code == 82) {panel.start();} //R
				if (code == 83) {panel.showStats();} //S
				if (code == 72) {panel.onlyShowHealth();} //H
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
	public void stop() {panel.stopTimer(); panel = null;}
	
	@Override
	public int getIndex() {return index;}
}