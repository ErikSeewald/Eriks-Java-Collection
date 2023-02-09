package sierpinski;
import javax.swing.JFrame;
import Main.EJC_Interface;
import Main.WindowEventHandler;

public class Sierpinski extends JFrame implements EJC_Interface
{
	private static final long serialVersionUID = 5764966202240396499L;
	private static final int index = 2;
	
	private int speed = 0;
	private SierpinskiPanel panel;
	
	public Sierpinski(int speed)
	{this.speed = speed;}
	
	@Override
	public void start(WindowEventHandler eventHandler)
	{
		this.setTitle("Sierpinski");
		this.addWindowListener(eventHandler);
		
		panel = new SierpinskiPanel(speed);
		this.add(panel);
		
		this.pack();
		this.setResizable(false);
		this.setVisible(true);
	}

	@Override
	public void stop() {panel.stop(); panel = null;}
	
	@Override
	public int getIndex() {return index;}
}