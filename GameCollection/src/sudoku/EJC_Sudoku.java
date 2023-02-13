package sudoku;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import Main.EJC_Interface;
import Main.WindowEventHandler;

public class EJC_Sudoku extends JFrame implements EJC_Interface
{
	private static final long serialVersionUID = -7551546838805061960L;
	private static final int index = 3;
	
	private SudokuPanel panel;
	
	public void start(WindowEventHandler eventHandler) 
	{
		this.setTitle("Sudoku");
		this.setResizable(false);
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
				{panel.changeValue(e.getKeyChar() - '0');} //char to int	
			}
			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}		
		});
		
		panel = new SudokuPanel();
		this.add(panel);
		this.pack();
		this.setVisible(true);
	}

	@Override
	public void stop() {panel = null;}
	
	@Override
	public int getIndex() {return index;}
}