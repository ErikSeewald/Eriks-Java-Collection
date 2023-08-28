package sierpinski;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Main.menu.WindowEventHandler;
import Main.EJC_Interface;

public class EJC_Sierpinski extends JFrame implements EJC_Interface
{
	private static final long serialVersionUID = 5764966202240396499L;
	private static final int index = 2;
	
	private SierpinskiPanel panel;
	private JPanel selectionPanel;
	private JFrame instance = this;
	
	@Override
	public void start(WindowEventHandler eventHandler)
	{	
		this.setTitle("Sierpinski");
		this.addWindowListener(eventHandler);
		
		//SELECTION FRAME
		selectionPanel = new JPanel();
		selectionPanel.setBackground(new Color(50,50,70));
		selectionPanel.setLayout(null);
		selectionPanel.setPreferredSize(new Dimension(500,150));
		
		this.addKeyListener(new KeyListener() 
		{
			@Override
			public void keyTyped(KeyEvent e) 
			{	
				int mode = e.getKeyChar() - '0'; //char to int
				if (mode < 1 || mode > 3) {return;}
				
				panel = new SierpinskiPanel(mode);
				instance.remove(selectionPanel);
				instance.add(panel);
				instance.pack();
			}
			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}		
		});
		
		//SELECTION LABELS	
		initLabel(new JLabel("PICK A MODE BY PRESSING THE CORRESPONDING KEY"), 20, 5, 500, 40);
		initLabel(new JLabel("1 - SLOW"), 20, 40, 500, 40);
		initLabel(new JLabel("2 - NORMAL"), 20, 70, 500, 40);
		initLabel(new JLabel("3 - FAST"), 20, 100, 500, 40);
		
		//START
		this.add(selectionPanel);
		this.pack();
		this.setResizable(false);
		this.setVisible(true);
	}
	
	private void initLabel(JLabel label, int a, int b, int c, int d)
	{
		label.setBounds(a,b,c,d);
		label.setForeground(new Color(220,220,240));
		selectionPanel.add(label);
	}

	@Override
	public void stop() 
	{
		if (panel != null) {panel.stop();} 
		panel = null;
	}
	
	@Override
	public int getIndex() {return index;}
}