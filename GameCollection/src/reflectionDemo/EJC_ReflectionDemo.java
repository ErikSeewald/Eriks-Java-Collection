package reflectionDemo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

import ejcMain.EJC_Game;
import ejcMain.EJC_WindowEventHandler;

public class EJC_ReflectionDemo extends JFrame implements EJC_Game
{
	private static final long serialVersionUID = 1345146613461L;
	
	private ReflectionPanel panel;
	
	private class KeyOperations
	{
		static final boolean decrease = false;
		static final boolean increase = true;
	}

	@Override
	public void start(EJC_WindowEventHandler eventHandler)
	{
		this.addWindowListener(eventHandler);
		panel = new ReflectionPanel();
		this.add(panel);
		this.setTitle("Reflection Demo");
		this.setResizable(false);
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
	
	@Override
	public void stop() {panel = null;}
}