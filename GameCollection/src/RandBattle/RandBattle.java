package RandBattle;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import Main.EJC_Interface;
import Main.MainMenu;
import Main.WindowEventHandler;

public class RandBattle extends JFrame implements EJC_Interface
{
	private static final long serialVersionUID = 411705531616331949L;
	private static final int index = 13;
	
	RB_Panel panel;
	
	public RandBattle()
	{
		panel = new RB_Panel();
		this.setIconImage(MainMenu.img.getImage());
		this.add(panel);
		this.pack();
		this.setTitle("Rand Battle");
		
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
	public void start(WindowEventHandler eventHandler)
	{this.addWindowListener(eventHandler);}
	
	@Override
	public void stop() {panel.stopTimer(); panel = null;}
	
	@Override
	public int getIndex() {return index;}
}
