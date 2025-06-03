package bombSorting;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import ejcMain.EJC_Game;
import ejcMain.EJC_WindowEventHandler;

public class EJC_BombSorting extends JFrame implements EJC_Game
{
	private static final long serialVersionUID = 7284705718024953236L;
	
	private Sort_Panel panel;
	
	@Override
	public void start(EJC_WindowEventHandler eventHandler)
	{
		this.addWindowListener(eventHandler);
		this.setTitle("Bomb Sorting");
		this.setResizable(false);
		
		panel = new Sort_Panel();
		this.add(panel);
		this.pack();
		this.setVisible(true);
		
		this.addKeyListener(new KeyListener() 
		{			
			@Override
			public void keyPressed(KeyEvent e) 
			{
				int code = e.getKeyCode();
				
				if (code == KeyEvent.VK_PLUS) {panel.changeSize(10); pack();}
				else if (code == KeyEvent.VK_MINUS) {panel.changeSize(-10); pack();}	
			}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyTyped(KeyEvent e) {}
		});
	}
	
	@Override
	public void stop()
	{panel.stop(); panel = null;}
}