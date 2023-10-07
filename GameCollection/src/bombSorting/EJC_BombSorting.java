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
			public void keyTyped(KeyEvent e) 
			{	
				char key = e.getKeyChar();
				
				if (key == '+') {panel.changeSize(10); pack();}
				else if (key == '-') {panel.changeSize(-10); pack();}
				
			}
			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}		
		});
	}
	
	@Override
	public void stop()
	{panel.stop(); panel = null;}
}