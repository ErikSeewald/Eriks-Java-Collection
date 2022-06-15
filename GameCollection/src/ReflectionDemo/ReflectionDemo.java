package ReflectionDemo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

import Main.WindowEventHandler;

public class ReflectionDemo extends JFrame
{
	private static final long serialVersionUID = 1345146613461L;
	
	ReflectionPanel panel;
	
	public ReflectionDemo()
	{
		panel = new ReflectionPanel();
		
		this.setTitle("Reflection Demo");
		this.setResizable(false);
		this.add(panel);
		this.pack();
		
		this.addKeyListener(new KeyListener() 
		{
			@Override
			public void keyTyped(KeyEvent e) 
			{	
				char key = e.getKeyChar();
				
				if (key == '1') {panel.changeReflectCount(false);}
				else if (key == '2') {panel.changeReflectCount(true);}
				else if (key == '3') {panel.changeRotationSpeed(false);}
				else if (key == '4') {panel.changeRotationSpeed(true);}
				else if (key == '5') {panel.accuracy = 500;}
				else if (key == '6') {panel.accuracy = 1000;}
				else if (key == '7') {panel.accuracy = 5000;}
				
				else if (key == '+') {panel.changeSize(10); pack();}
				else if (key == '-') {panel.changeSize(-10); pack();}
			}
			@Override
			public void keyPressed(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {}		
		});
		this.setVisible(true);	
	}
	public void start(WindowEventHandler eventHandler)
	{this.addWindowListener(eventHandler);}
}
