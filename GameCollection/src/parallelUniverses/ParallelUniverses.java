package parallelUniverses;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import javax.swing.JFrame;
import javax.swing.Timer;
import Main.EJC_Interface;
import Main.WindowEventHandler;

public class ParallelUniverses extends JFrame implements EJC_Interface
{
	private static final long serialVersionUID = -728908076775898206L;
	private static final int index = 5;
	
	private PU_Panel panel;
	
	private HashSet<Integer> pressedKeys = new HashSet<>();
	
	private Timer timer = new Timer(25, new ActionListener()
	{	@Override
		public void actionPerformed(ActionEvent e) 
	 	{
			int x = 0, y = 0, moveCount = 1;	
			boolean move = false;
			
			if (pressedKeys.contains(68)) {x = 1;  move = true;} 	//D
			if (pressedKeys.contains(65)) {x = -1; move = true;} 	//A
			if (pressedKeys.contains(87)) {y = -1; move = true;} 	//W
			if (pressedKeys.contains(83)) {y = 1;  move = true;} 	//S
			
			if (pressedKeys.contains(16)) {moveCount = 4;}	//SHIFT
		
			if(!move)
			{return;}
			
			panel.movePlayer(x, y, moveCount);
		}	
	});
	
	@Override
	public void start(WindowEventHandler eventHandler) 
	{
		this.setTitle("Parallel Universes");
		panel = new PU_Panel();
		
		this.addWindowListener(eventHandler);
		this.setResizable(false);
		this.add(panel);
		this.pack();
		
		this.addKeyListener(new KeyListener() 
		{	
			@Override
			public void keyPressed(KeyEvent e) 
			{
				int code = e.getKeyCode();
				
				if (!(pressedKeys.contains(code))) {pressedKeys.add(code);}
				if (code == 72) {panel.hideUniverses();} //H
			
			}
			@Override
			public void keyReleased(KeyEvent e) 
			{pressedKeys.remove(e.getKeyCode());}
			
			@Override
			public void keyTyped(KeyEvent e) {}
		});	
		
		timer.start();
		this.setVisible(true);
	}
	
	@Override
	public void stop()
	{timer.stop(); panel = null; pressedKeys = null;}
	
	@Override
	public int getIndex() {return index;}
}