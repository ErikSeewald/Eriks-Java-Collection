package Main;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;

import bloonShoot.EJC_BloonShoot;
import clothSim.EJC_ClothSim;
import gravityVectors.EJC_GravityVectors;
import insects.EJC_Insects;
import mouseDodge.EJC_MouseDodge;
import parallelUniverses.EJC_ParallelUniverses;
import particles.EJC_Particles;
import pathfindGame.EJC_Pathfind;
import pixelCollision.EJC_PixelCollision;
import randBattle.EJC_RandBattle;
import randGrowth.EJC_RandGrowth;
import reflectionDemo.EJC_ReflectionDemo;
import sidescroller.EJC_Sidescroller;
import snakesAndLadders.EJC_SnakesAndLadders;
import sudoku.EJC_Sudoku;

public class MainMenu extends JFrame implements MouseListener
{
	private static final long serialVersionUID = 1454253443346436L;
	
	MainMenu()
	{
		this.setTitle("Menu");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.getContentPane().setBackground(new Color(40,40,55));
		
		this.setLayout(null);
		this.initialize();
	}
	
	//---------------------------------------INITIALIZATION---------------------------------------
	
		private int resolution = 700;
		
		private WindowEventHandler eventHandler = new WindowEventHandler(this);
		private boolean[] gameOpened = new boolean[24];
		
		//BUTTONS
		private class GButton extends JLabel
		{
			private static final long serialVersionUID = 7604971023802033717L;
			
			int index;		
			int width, height;
			boolean sizeIncreased = false;
			
			GButton(String text, int index)
			{
				this.setText(text);
				this.index = index;
				
				this.setForeground(text_color);
				this.setBackground(b_color_basic);
				this.setOpaque(true);
			}
		}
		
		private final GButton[] gameButtons = 
		{
				new GButton("      Insects", 0), new GButton("     Particles", 1), new GButton("    Sierpinski", 2), new GButton("      Sudoku", 3),
				new GButton("      Pathfind", 4), new GButton("    Parallel U.", 5), new GButton("    Reflection", 6), new GButton(" Mouse Dodge", 7), 
				new GButton("   Sidescroller", 8), new GButton(" Pixel Collision", 9), new GButton("    Cloth Sim", 10), new GButton(" Gravity Vects", 11),
				new GButton("  Bloon Shoot", 12), new GButton("  Rand Battle", 13), new GButton(" Rand Growth", 14), new GButton("     Ladders", 15), 
				new GButton("", 16), new GButton("", 17), new GButton("", 18), new GButton("", 19), 
				new GButton("", 20), new GButton("", 21), new GButton("", 22), new GButton("", 23), 
		};
		
		private static final Color text_color = new Color(210,210,230);
		private static final Color b_color_basic = new Color(75,75,105);
		private static final Color b_color_highlight = new Color(110,110,130);
		private static final Color b_color_pressed = new Color(130,130,150);
		
		//LABELS
		private JLabel[] guideLabels = 
		{new JLabel("ERIK'S COLLECTION"), new JLabel(""), new JLabel(""), new JLabel(""), new JLabel(""), new JLabel("")};
		
		private void initialize()
		{
			this.setSize(resolution, resolution);
			
			//LABELS
			final double[] divisors = {60, 15,10,7.5, 6, 5};
			for (int i = 0; i < guideLabels.length; i++)
			{
				guideLabels[i].setBounds(resolution/40,	(int) (resolution/divisors[i]),	resolution+100,	resolution/15);
				guideLabels[i].setForeground(text_color);
				
				if (i == 0) {guideLabels[i].setFont(new Font ("", Font.BOLD, resolution/26));}
				else {guideLabels[i].setFont(new Font ("", Font.BOLD, resolution/42));}
				
				this.add(guideLabels[i]);
			}
			
			//BUTTONS
			int x = 0, y = 0;
			for (GButton button : gameButtons)
			{
				initButton(button, (resolution/10) + x*(resolution/5), (resolution/3) + y*(resolution/10));
				
				x++;
				if (x > 3) {x = 0; y++;} //move to next row after 4 buttons have been drawn
			}
		}

		//BUTTONS
		private void initButton(GButton button, int x, int y)
		{
			button.width = resolution/6;
			button.height = resolution/15;
			button.setBounds(x, y, button.width, button.height);
			
			button.setFont(new Font ("", Font.PLAIN, resolution/42));
			button.setBorder(BorderFactory.createLineBorder(new Color(100,100,130), resolution/300));
			button.addMouseListener(this);
			this.add(button);
		}
	
	//---------------------------------------GUI_CONTROL---------------------------------------
	
	private void openWindow(GButton button)
	{
		if (gameOpened[button.index]) {return;}
		
		switch (button.index)
		{
			case 0: new EJC_Insects().start(eventHandler); break;
			case 1: new EJC_Particles().start(eventHandler); break;
			case 2: new SelectionFrame().start("Sierpinski", eventHandler); break;
			case 3: new EJC_Sudoku().start(eventHandler); break;
			case 4: new EJC_Pathfind().start(eventHandler); break;
			case 5: new EJC_ParallelUniverses().start(eventHandler); break;
			case 6: new EJC_ReflectionDemo().start(eventHandler); break;
			case 7: new EJC_MouseDodge().start(eventHandler); break;
			case 8: new EJC_Sidescroller().start(eventHandler); break;
			case 9: new EJC_PixelCollision().start(eventHandler); break;
			case 10: new EJC_ClothSim().start(eventHandler); break;
			case 11: new EJC_GravityVectors().start(eventHandler); break;
			case 12: new EJC_BloonShoot().start(eventHandler); break;
			case 13: new EJC_RandBattle().start(eventHandler); break;
			case 14: new EJC_RandGrowth().start(eventHandler); break;
			case 15: new EJC_SnakesAndLadders().start(eventHandler); break;
			
			default: return;
		}
		
		gameOpened[button.index] = true;		
	}
	
	private void buttonAnimation(GButton button, int change)
	{
		if ((change > 0 && button.sizeIncreased) || (change < 0 && !button.sizeIncreased)) {return;} //don't go beyond max/min size
		button.sizeIncreased = change > 0;
		
		button.setBounds
		(
		 button.getX()-(change/2), button.getY()-(change/2),
		 button.getWidth()+change, button.getHeight()+change
		);
	}
	
	private void changeGuide(int index)
	{	
		for(int i = 0; i < guideLabels.length; i++)
		{guideLabels[i].setText(GameTitles.values()[index].guide[i]);}
		System.gc();
	}
	
	//MOUSE
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		GButton button = (GButton) e.getSource();
		buttonAnimation(button, -(button.width /25));
		button.setBackground(b_color_highlight);
		
		openWindow(button);
	}
	
	@Override
	public void mousePressed(MouseEvent e) 
	{
		GButton button = (GButton)e.getSource();
		if (button.sizeIncreased) {buttonAnimation(button, -(button.width /25));}
		button.setBackground(b_color_pressed);
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		GButton button = (GButton)e.getSource();
		buttonAnimation(button, (button.width /25));
		button.setBackground(b_color_highlight);
		
		if (button.getText() != "") 
		{changeGuide(button.index);}
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		GButton button = (GButton)e.getSource();
		if (button.sizeIncreased) {buttonAnimation(button, -(button.width /25));}
		button.setBackground(b_color_basic);	
	}
	
	public void changeSize(int amount)
	{
		resolution+=amount;
		initialize();
	}
	
	public void closeGame(EJC_Interface game)
	{
		game.stop();
		gameOpened[game.getIndex()] = false;
		game = null;
		System.gc();
	}
}