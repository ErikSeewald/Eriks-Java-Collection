package bomb_sorting;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import Main.EJC_Interface;
import Main.WindowEventHandler;

public class EJC_Bomb_Sorting extends JFrame implements EJC_Interface
{
	private static final long serialVersionUID = 7284705718024953236L;
	private static final int index = 16;
	
	private Sort_Panel panel;
	
	@Override
	public void start(WindowEventHandler eventHandler)
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
				else if (key == 'r') {panel.start();}
				
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
	
	@Override
	public int getIndex() {return index;}
}