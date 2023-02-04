package Main;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import Sierpinski.Sierpinski;

public class SelectionFrame 
{
	private JFrame frame;
	
	public void start(String gameName, WindowEventHandler eventHandler)
	{
		//FRAME
		frame = new JFrame("Selection");
		frame.setIconImage(MainMenu.img.getImage());
		frame.getContentPane().setBackground(new Color(50,50,70));
		frame.setLayout(null);
		frame.setSize(500, 200);
		frame.setVisible(true);
		
		frame.addKeyListener(new KeyListener() 
		{
			@Override
			public void keyTyped(KeyEvent e) 
			{	
				int mode = e.getKeyChar() - '0'; //char to int
				if (mode < 1 || mode > 3) {return;}
				
				switch (gameName)
				{
					case "Sierpinski": new Sierpinski(eventHandler, mode);
					break;
				}
				
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}		
		});
		
		//LABELS
		String[] modes = {"","",""};
		switch(gameName)
		{
			case "Sierpinski": modes[0] = "1 - SLOW"; modes[1] = "2 - NORMAL"; modes[2] = "3 - FAST";
			break;
		}
		
		initLabel(new JLabel("PICK A MODE BY PRESSING THE CORRESPONDING KEY"), 20, 5, 500, 40);
		initLabel(new JLabel(modes[0]), 20, 40, 500, 40);
		initLabel(new JLabel(modes[1]), 20, 70, 500, 40);
		initLabel(new JLabel(modes[2]), 20, 100, 500, 40);
	}
	
	private void initLabel(JLabel label, int a, int b, int c, int d)
	{
		label.setBounds(a,b,c,d);
		label.setForeground(new Color(220,220,240));
		frame.add(label);
	}
}