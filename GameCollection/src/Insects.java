import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

public class Insects
{
	InsectsPanel panel;
	JFrame frame;
	
	Insects()
	{	
		panel = new InsectsPanel();
		
		frame = new JFrame("Insects");
		
		frame.setResizable(false);
		frame.add(panel);
		frame.pack();
		
		frame.addKeyListener(new KeyListener() 
		{
			@Override
			public void keyTyped(KeyEvent e) 
			{	
				if (e.getKeyChar() == 'r') {panel.start();}
				else if (e.getKeyChar() == '1') {panel.changeAntAmount(false);}
				else if (e.getKeyChar() == '2') {panel.changeAntAmount(true);}
				else if (e.getKeyChar() == '3') {panel.changeAddAmount(false);}
				else if (e.getKeyChar() == '4') {panel.changeAddAmount(true);}
			}
			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}		
		});

		frame.setVisible(true);	
	}
	
	public void start(WindowEventHandler eventHandler)
	{
		frame.addWindowListener(eventHandler);
	}
}
