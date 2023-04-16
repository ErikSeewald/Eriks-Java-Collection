package infdungeons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import javax.swing.JFrame;
import javax.swing.Timer;
import Main.EJC_Interface;
import Main.WindowEventHandler;

public class EJC_InfDungeons extends JFrame implements EJC_Interface
{
	private static final long serialVersionUID = 4927061210031943469L;
	private static final int index = 17;

	private Inf_Panel panel;
	private HashSet<Integer> pressedKeys = new HashSet<>();
	private Timer timer;
	
	@Override
	public void start(WindowEventHandler eventHandler)
	{	
		this.addWindowListener(eventHandler);
		this.setTitle("Inf Dungeons");
		this.setResizable(false);
		
		panel = new Inf_Panel();
		this.add(panel);
		this.pack();
		this.setVisible(true);
		
		this.addKeyListener(new KeyListener() 
		{	
			@Override
			public void keyPressed(KeyEvent e) 
			{
				int code = e.getKeyCode();
				pressedKeys.add(code);
				
				if (code == 521) {panel.changeSize(10); pack();} // +
				else if (code == 45) {panel.changeSize(-10); pack();} // -
				else if (code == 69) {panel.interactEvent();} // E
			
			}
			@Override
			public void keyReleased(KeyEvent e) 
			{pressedKeys.remove(e.getKeyCode());}
			
			@Override
			public void keyTyped(KeyEvent e) {}
		});	
		
		timer = new Timer(25, new ActionListener()
		{	@Override
			public void actionPerformed(ActionEvent e) 
			{
				int x = 0, y = 0, speed = panel.getPlayerSpeed();

				boolean is_attacking = pressedKeys.contains(32);
				
				if (panel.player.isAttacking && !is_attacking) {panel.player.isAttacking = false;} // && !is_attacking for extra sword frame
				else if (is_attacking)
				{
					panel.player.isAttacking = is_attacking;
					pressedKeys.remove(32);
					speed /= 2;
				}
				
				if (pressedKeys.contains(68)) {x = speed;} 	//D
				if (pressedKeys.contains(65)) {x = -speed;} //A
				if (pressedKeys.contains(87)) {y = -speed;} //W
				if (pressedKeys.contains(83)) {y = speed;} 	//S

				if(x != 0 || y != 0)
				{panel.player.move(x, y);}

				panel.updateDungeon();
			}	
		});

		timer.setInitialDelay(100);
		timer.start();
	}
	
	@Override
	public void stop()
	{timer.stop(); panel.stop(); panel = null;}

	@Override
	public int getIndex() {return index;}
}
