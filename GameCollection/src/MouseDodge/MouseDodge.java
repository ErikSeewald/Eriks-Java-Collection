package MouseDodge;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

import Main.MainMenu;
import Main.WindowEventHandler;

public class MouseDodge
{
	private MouseDodgePanel panel;
	
	public void start(WindowEventHandler eventHandler) 
	{
		JFrame frame = new JFrame("Mouse Dodge");
		panel = new MouseDodgePanel();

		frame.addWindowListener(eventHandler);
		frame.setIconImage(MainMenu.img.getImage());
		frame.setResizable(false);
		
		frame.addKeyListener(new KeyListener() 
		{
			@Override
			public void keyTyped(KeyEvent e) 
			{	
				char key = e.getKeyChar();
				
				if (key == '+') {panel.changeSize(10); frame.pack();}
				else if (key == '-') {panel.changeSize(-10); frame.pack();}
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
		
		frame.add(panel);
		frame.pack();
		
		frame.setVisible(true);
	}
}
