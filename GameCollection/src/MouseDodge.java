//import java.awt.event.ComponentAdapter;
//import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

public class MouseDodge
{
	MouseDodgePanel panel;
//	int prevHeight; 
	
	public void start(WindowEventHandler eventHandler) 
	{
		
		JFrame frame = new JFrame("Mouse Dodge");
		panel = new MouseDodgePanel();

		frame.addWindowListener(eventHandler);
		
		frame.addKeyListener(new KeyListener() 
		{
			@Override
			public void keyTyped(KeyEvent e) 
			{	
				char key = e.getKeyChar();
				
				if (key == '+') {panel.changeSize(panel.PANEL_SIZE,10); frame.pack();}
				else if (key == '-') {panel.changeSize(panel.PANEL_SIZE,-10); frame.pack();}
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
		
//		prevHeight = frame.getHeight();
		
//		frame.addComponentListener(new ComponentAdapter() 
//		{  
//			public void componentResized(ComponentEvent evt) 
//			{
//				int height = frame.getHeight();
//				frame.setSize((int)(height*1.7), height);
//				if (prevHeight != height) 
//				{
//					panel.changeSize(height, 0);
//					prevHeight = height;
//				}	
//			}
//		});
		
		frame.add(panel);
		frame.pack();
		
		frame.setVisible(true);
	}
}
