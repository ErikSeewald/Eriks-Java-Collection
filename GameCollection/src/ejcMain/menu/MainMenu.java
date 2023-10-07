package ejcMain.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import ejcMain.EJC_GameHandler;

public class MainMenu extends JFrame implements MouseListener
{
	private static final long serialVersionUID = 1454253443346436L;
	
	private static final class MenuControl
	{
		public static final boolean firstInit = true;
		public static final boolean sizeChange = false;
	}
	
	public MainMenu()
	{
		this.setTitle("Menu");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.getContentPane().setBackground(new Color(40,40,55));
		
		this.setLayout(null);
		this.initialize(MenuControl.firstInit);
	}
	
	//---------------------------------------INITIALIZATION---------------------------------------
	
		private int resolution = 700;
		
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
		
		private GButton[] gameButtons = new GButton[EJC_GameHandler.maxGameCount];
		
		private static final Color text_color = new Color(210,210,230);
		private static final Color b_color_basic = new Color(75,75,105);
		private static final Color b_color_highlight = new Color(110,110,130);
		private static final Color b_color_pressed = new Color(130,130,150);
		
		//LABELS
		private JLabel[] guideLabels = 
		{new JLabel("ERIK'S COLLECTION"), new JLabel(""), new JLabel(""), new JLabel(""), new JLabel(""), new JLabel("")};
		
		private static GameText[] game_text = GameText.values();
		
		private void initialize(boolean firstInit)
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
				
				if (firstInit) {this.add(guideLabels[i]);}
			}
			
			//BUTTONS
			int x = 0, y = 0;
			for (int i = 0; i < EJC_GameHandler.maxGameCount; i++)
			{
				String button_text = i < game_text.length ? game_text[i].button_text : "";
				GButton button = new GButton(button_text, i);
				gameButtons[i] = button;
				initButton(button, (resolution/10) + x*(resolution/5), (resolution/3) + y*(resolution/10));
				
				if (firstInit)
				{
					button.addMouseListener(this);
					this.add(button);
				}
				
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
		}
	
	//---------------------------------------GUI_CONTROL---------------------------------------
	
	private void buttonAnimation(GButton button, int change)
	{
		if ((change > 0 && button.sizeIncreased) || (change < 0 && !button.sizeIncreased)) 
		{return;} //don't go beyond max/min size
		
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
		{guideLabels[i].setText(game_text[index].guide[i]);}
		System.gc();
	}
	
	//MOUSE
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		GButton button = (GButton) e.getSource();
		buttonAnimation(button, -(button.width /25));
		button.setBackground(b_color_highlight);
		
		EJC_GameHandler.startGame(button.index);
	}
	
	@Override
	public void mousePressed(MouseEvent e) 
	{
		GButton button = (GButton)e.getSource();
		if (button.sizeIncreased) {buttonAnimation(button, -(button.width /25));}
		button.setBackground(b_color_pressed);
	}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		GButton button = (GButton)e.getSource();
		buttonAnimation(button, (button.width /25));
		button.setBackground(b_color_highlight);
		
		if (!button.getText().isEmpty()) 
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
		initialize(MenuControl.sizeChange);
		System.gc();
	}
}