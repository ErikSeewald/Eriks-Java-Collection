package clothSim;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.Timer;

import ejcMain.EJC_Game;
import ejcMain.EJC_WindowEventHandler;
import ejcMain.util.EJC_GUI.EJC_MenuBar;

public class EJC_ClothSim extends JFrame implements EJC_Game, ActionListener
{
	private static final long serialVersionUID = -1946430738048947884L;
	
	private ClothSimPanel panel;
	private Timer timer;
	private JMenuItem fileLoad;
	private JMenuItem fileSave;
	
	@Override
	public void start(EJC_WindowEventHandler eventHandler)
	{
		this.addWindowListener(eventHandler);
		this.setTitle("Cloth Sim");
		panel = new ClothSimPanel();
		this.add(panel);
		this.pack();
		
		this.addKeyListener(new KeyListener()
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{
				int code = e.getKeyCode();
				
				if (code == KeyEvent.VK_C) {panel.connect();}
				else if (code == KeyEvent.VK_R) {panel.restart();}
				else if (code == KeyEvent.VK_Z && e.isControlDown()) {panel.removeLastConnector();}
				else if (code == KeyEvent.VK_S)
				{
					if (timer.isRunning()) {timer.stop();} else {timer.start();}
					panel.switchIsRunning();
				}	
			}

			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyTyped(KeyEvent e) {}
		});
		
		timer = new Timer(30, this);
		
		//MENU BAR
		fileLoad  = new JMenuItem("Load");
		fileLoad.addActionListener(this);
		fileSave  = new JMenuItem("Save");
		fileSave.addActionListener(this);
		
		EJC_MenuBar menuBar = new EJC_MenuBar(this);
		menuBar.addEJCMenu("Files", new JMenuItem[] {fileLoad, fileSave});
		
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == timer) {panel.simulate();}
		else if (e.getSource() == fileSave) {panel.saveLayout();}
		else if  (e.getSource() == fileLoad) {panel.loadLayout();}
	}
	
	@Override
	public void stop()
	{timer.stop(); timer = null; panel = null;}
}