package reflectionDemo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import Main.EJC_Interface;
import Main.menu.WindowEventHandler;

public class EJC_ReflectionDemo extends JFrame implements EJC_Interface
{
	private static final long serialVersionUID = 1345146613461L;
	private static final int index = 6;
	
	private ReflectionPanel panel;
	
	private class KeyOperations
	{
		static final boolean decrease = false;
		static final boolean increase = true;
	}

	@Override
	public void start(WindowEventHandler eventHandler)
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
	
	@Override
	public int getIndex() {return index;}
}