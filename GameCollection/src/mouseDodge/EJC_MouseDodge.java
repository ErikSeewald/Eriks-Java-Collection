package mouseDodge;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

import ejcMain.EJC_Game;
import ejcMain.EJC_WindowEventHandler;

public class EJC_MouseDodge extends JFrame implements EJC_Game
{
	private static final long serialVersionUID = 1453082636502752179L;
	
	private MouseDodgePanel panel;
	
	public void start(EJC_WindowEventHandler eventHandler) 
	{
		panel = new MouseDodgePanel();

		this.setTitle("Mouse Dodge");
		this.addWindowListener(eventHandler);
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
				else if (key == 'd') {panel.darkMode = !panel.darkMode; panel.repaint();}
				
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
}