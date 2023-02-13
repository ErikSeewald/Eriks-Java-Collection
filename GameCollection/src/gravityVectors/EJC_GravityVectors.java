package gravityVectors;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import Main.EJC_Interface;
import Main.WindowEventHandler;

public class EJC_GravityVectors extends JFrame implements EJC_Interface
{
	private static final long serialVersionUID = -7941120893843254800L;
	private static final int index = 11;
	
	private GravityVectorsPanel panel;

	public void start(WindowEventHandler eventHandler) 
	{
		panel = new GravityVectorsPanel();
		
		this.addWindowListener(eventHandler);
		this.setTitle("Gravity Vectors");
	
		this.addKeyListener(new KeyListener()
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{
				int code = e.getKeyCode();
				
				if (code == 521) {panel.changeSize(30); pack();} //+
				else if (code == 45) {panel.changeSize(-30); pack();} //-
				else if (code == 49) {panel.changePPOINT_COUNT(-1);} //1
				else if (code == 50) {panel.changePPOINT_COUNT(1);} //2
				else if (code == 71) {panel.gradientMode = !panel.gradientMode; panel.repaint();} //G     
				else if (code == 78) {panel.switchArrowNormalization(); panel.repaint();} //N              
			}
			
			@Override
			public void keyTyped(KeyEvent e) 
			{}
			@Override
			public void keyReleased(KeyEvent e) {}
			
		});
		
		this.setResizable(false);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
		this.add(panel);
		this.pack();
		this.setVisible(true);
	}

	@Override
	public void stop() {panel = null;}
	
	@Override
	public int getIndex() {return index;}
}