package sudoku;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import Main.EJC_Interface;
import Main.WindowEventHandler;

public class Sudoku extends JFrame implements EJC_Interface
{
	private static final long serialVersionUID = -7551546838805061960L;
	private static final int index = 3;
	
	private SudokuPanel panel;
	
	public void start(WindowEventHandler eventHandler) 
	{
		this.setTitle("Sudoku");
		this.setResizable(false);
		
		panel = new SudokuPanel();
	
		this.addWindowListener(eventHandler);
		this.addKeyListener(new KeyListener() 
		{
			@Override
			public void keyTyped(KeyEvent e) 
			{	
				if (e.getKeyChar() == 's') {panel.sudoku();}
				else if (e.getKeyChar() == '+') {panel.changeSize(10); pack();}
				else if (e.getKeyChar() == '-') {panel.changeSize(-10); pack();}
				else if (e.getKeyChar() == 'r') {panel.reset();}
				
				else 
				{
					int x =  e.getKeyChar() - '0'; //gives the int value
					try{panel.changeValue(x);} catch (Exception ex) {}
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}		
		});
		
		this.add(panel);
		this.pack();
		this.setVisible(true);
	}

	@Override
	public void stop() {panel = null;}
	
	@Override
	public int getIndex() {return index;}
}