package clothSim;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.Timer;
import Main.EJC_Interface;
import Main.WindowEventHandler;

public class EJC_ClothSim extends JFrame implements EJC_Interface, ActionListener
{
	private static final long serialVersionUID = -1946430738048947884L;
	private static final int index = 10;
	
	private ClothSimPanel panel;
	private Timer timer;
	private JMenuItem fileLoad;
	private JMenuItem fileSave;
	
	@Override
	public void start(WindowEventHandler eventHandler)
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
				
				if (code == 67) {panel.connect();}	//C -> CONNECT
				else if (code == 82) {panel.restart();} //R -> RESTART
				else if (code == 90 && e.isControlDown()) {panel.removeLastConnector();} //CTRL Z
				else if (code == 83) //S -> START
				{
					if (timer.isRunning()) {timer.stop();} else {timer.start();}
					panel.switchIsRunning();
				}	
			}
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}
		});
		
		timer = new Timer(30, this);
		
		JMenu fileMenu= new JMenu("Files");
		fileMenu.setForeground(new Color (230,230,250));
		fileMenu.setBorder(BorderFactory.createLineBorder(new Color (100,100,120)));
		
		fileLoad  = new JMenuItem("Load");
		fileSave  = new JMenuItem("Save");
		setItemBasics(fileLoad, fileMenu);
		setItemBasics(fileSave, fileMenu);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		menuBar.setBackground(new Color (100,100,120));
		menuBar.setBorder(BorderFactory.createLineBorder(new Color (115,115,135), 2));
		this.setJMenuBar(menuBar);
		
		this.setVisible(true);
	}
	
	private void setItemBasics(JMenuItem item, JMenu menu)
	{item.addActionListener(this); menu.add(item);}
	
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
	
	@Override
	public int getIndex() {return index;}
}