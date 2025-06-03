package snakesAndLadders.gui;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SpinDie extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 3639957232418974530L;
	
	private int currentState;
	private int spinTime;
	
	final static int SIZE = 100;
	
	private Timer rollTimer;
	private Random random;
	private SnL_GUI GUI;

	SpinDie(SnL_GUI GUI)
	{
		this.GUI = GUI;
		this.setPreferredSize(new Dimension(SIZE,SIZE));
		
		rollTimer = new Timer(150, this);
		random = new Random();
	}
	
	public void stop() {rollTimer.stop();}
	
	public int roll()
	{
		spinTime = random.nextInt(5)+12;
		currentState = random.nextInt(6)+1;
		rollTimer.start();
		
		int result = (spinTime+currentState) % 6;
		if (result == 0) {result = 6;}
		return result;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (spinTime > 0)
		{
			++currentState; 
			if (currentState > 6) {currentState = 1;}
			repaint();
		}
		--spinTime;
		
		if (spinTime == -5) //pause a few frames after spin
		{rollTimer.stop(); GUI.enableMove();}
	}
	
	private boolean[] getResultArray()
	{
		/*
	     *    0, 1, 2          0     0
	     *    3, 4, 5  => 5 =     0     = {1, 0, 1, 0, 1, 0, 1, 0, 1}
	     *    6, 7, 8          0     0
	    */
		boolean[] result = new boolean[9];
		
	    result[0] = result[8] = currentState > 1;
	    result[2] = result[6] = currentState > 3;
		result[3] = result[5] = currentState == 6;
	    result[4] = currentState % 2 == 1;
	    
	    return result; 
	}
	
	//---------------------------------------PAINT---------------------------------------
	
	private static final Color borderColor = new Color(120,120,150);

	public void paint(Graphics g)
	{		
		Toolkit.getDefaultToolkit().sync(); // Force flush (for X11)
		Graphics2D g2D = (Graphics2D) g;
		super.paint(g2D);

		//BORDER
		g2D.setPaint(borderColor);
		g2D.setStroke(new BasicStroke(8));
		g2D.drawRect(0, 0, SIZE, SIZE);

		//NUMBERS
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		boolean[] result = getResultArray();
		int yOffset = -16;
		for (int i = 0; i < 9; i++)
		{
			if (i % 3 == 0) {yOffset+=30;} //move into next row

			if (result[i] == true)
			{g2D.fillOval(12 + (i%3)*30, yOffset, 15, 15);}
		}	
	}
}