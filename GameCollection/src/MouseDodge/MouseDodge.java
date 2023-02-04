package MouseDodge;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import Main.EJC_Interface;
import Main.MainMenu;
import Main.WindowEventHandler;

public class MouseDodge extends JFrame implements EJC_Interface
{
	private static final long serialVersionUID = 1453082636502752179L;
	private static final int index = 7;
	
	private MouseDodgePanel panel;
	
	public void start(WindowEventHandler eventHandler) 
	{
		panel = new MouseDodgePanel();

		this.setTitle("Mouse Dodge");
		this.addWindowListener(eventHandler);
		this.setIconImage(MainMenu.img.getImage());
		this.setResizable(false);
		
		this.addKeyListener(new KeyListener() 
		{
			@Override
			public void keyTyped(KeyEvent e) 
			{	
				char key = e.getKeyChar();
				
				if (key == '+') {panel.changeSize(10); pack();}
				else if (key == '-') {panel.changeSize(-10); pack();}
				else if (key == 'r') {panel.start();}
				else if (key == '') {panel.pause();} //ESC Key
				else if (key == 'd') {panel.debug = !panel.debug; panel.repaint();}
				else if (key == 'f') {panel.darkMode = !panel.darkMode; panel.repaint();}
				
			}
			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}		
		});
		
		this.add(panel);
		this.pack();
		
		this.setVisible(true);
	}

	@Override
	public void stop() {panel.stop(); panel = null;}
	
	@Override
	public int getIndex() {return index;}
}
