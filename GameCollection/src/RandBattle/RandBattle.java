package RandBattle;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import Main.MainMenu;
import Main.WindowEventHandler;

public class RandBattle extends JFrame
{
	private static final long serialVersionUID = 411705531616331949L;
	
	static RB_Panel panel;
	
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
			}
			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyTyped(KeyEvent e) {}
		});
		
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public static void stop()
	{panel.stopTimer();}
	
	public void start(WindowEventHandler eventHandler)
	{this.addWindowListener(eventHandler);}
}
