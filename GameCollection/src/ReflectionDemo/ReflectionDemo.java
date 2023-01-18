package ReflectionDemo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

import Main.MainMenu;
import Main.WindowEventHandler;

public class ReflectionDemo extends JFrame
{
	private static final long serialVersionUID = 1345146613461L;
	
	ReflectionPanel panel;
	
	private class KeyOperations
	{
		static final boolean decrease = false;
		static final boolean increase = true;
	}
	
	public ReflectionDemo()
	{
		panel = new ReflectionPanel();
		
		this.setTitle("Reflection Demo");
		this.setIconImage(MainMenu.img.getImage());
		this.setResizable(false);
		this.add(panel);
		this.pack();
		
		this.addKeyListener(new KeyListener() 
		{
			@Override
			public void keyTyped(KeyEvent e) 
			{	
				char key = e.getKeyChar();
				
				if (key == '1') {panel.changeReflectCount(KeyOperations.decrease);}
				else if (key == '2') {panel.changeReflectCount(KeyOperations.increase);}
				else if (key == '3') {panel.changeRotationSpeed(KeyOperations.decrease);}
				else if (key == '4') {panel.changeRotationSpeed(KeyOperations.increase);}
				
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
