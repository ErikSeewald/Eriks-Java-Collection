package SnakesAndLadders;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SnL_GUI extends JPanel implements MouseListener
{
private static final long serialVersionUID = -587643554501823550L;
	
	private static final int PANEL_WIDTH = 300;
	private static final int PANEL_HEIGHT = 780;
	
	private boolean buttonSizeIncreased = false;
	
	private JTextField playerCountInput;
	
	private JLabel playerCountLabel;
	private JLabel currentPlayerLabel;
	
	private final Color textColor = new Color(90, 90, 110);
	private final Color borderColor = new Color(120,120,150);
	
	private JLabel startButton;
	
	private final Color buttonColor1 = new Color(170, 170, 210);
	private final Color buttonColor2 = new Color(200, 200, 240);
	private final Color buttonColor3 = new Color(215, 215, 255);
	
	private int buttonSizeX = 150;
	private int buttonSizeY = 70;
	
	SnL_Panel panel;
	
	SpinDie die;
	JLabel dieButton;
	
	public interface Animatable
	{
		public int getX();
		public int getY();
		public int getWidth();
		public int getHeight();
		public void setBounds(int a, int b, int c, int d);
	}
	
	SnL_GUI(SnL_Panel panel)
	{
		this.panel = panel;
		
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		this.setBackground(new Color(220,220,250));
		this.setBorder(BorderFactory.createLineBorder(borderColor, 8));

		this.setLayout(null);
		
		//BUTTONS
		startButton = new JLabel("    Start");
		startButton.setBounds(73, 60, buttonSizeX, buttonSizeY);
		setButtonSettings(startButton);
		
		//INPUTS
		playerCountInput = new JTextField("1");
		setTextFieldSettings(playerCountInput);
		playerCountInput.setBounds(93, 210, 110, 45);
		
		
		//LABELS
		playerCountLabel = new JLabel("Player count");
		setLabelSettings(playerCountLabel);
		playerCountLabel.setBounds(85, 170, 250, 45);
		
		currentPlayerLabel = new JLabel("Player 1");
		setLabelSettings(currentPlayerLabel);
		currentPlayerLabel.setBounds(112, 420, 270, 45);
		
		//DIE
		die = new SpinDie();
		die.setBounds(100, 500, die.SIZE, die.SIZE);
		
		dieButton = new JLabel("  Roll");
		dieButton.setBounds(100, 500, die.SIZE, die.SIZE);
		setButtonSettings(dieButton);
		
		this.add(die);
		
	}
	
	private void setTextFieldSettings(JTextField textField)
	{
		textField.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 170), 3));
		textField.setFont(new Font("", Font.PLAIN, 20));
		this.add(textField);
		
		textField.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent e) 
			{
				if ((e.getKeyChar() >= '1' && e.getKeyChar() <= '9') || e.getKeyChar() == '') //-> Delete
				{textField.setEditable(true);} 
				else 
				{textField.setEditable(false);}
				
				if (textField.getText().length() >= 1 && e.getKeyChar() != '') // limit to 1 character
				{textField.setEditable(false);}
			}
		});
	}
	
	private void setLabelSettings(JLabel label)
	{
		label.setForeground(textColor);
		label.setFont(new Font("", Font.BOLD, 20));
		this.add(label);
	}
	
	private void setButtonSettings(JLabel button)
	{
		button.setBackground(buttonColor1);
		button.setForeground(textColor);
		button.setOpaque(true);
		button.setBorder(BorderFactory.createLineBorder(borderColor, 3));
		button.setFont(new Font("", Font.BOLD, 30));
		button.addMouseListener(this);
		this.add(button);
	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		
		if (e.getSource()==startButton) 
		{	
			buttonAnimation(startButton, -(buttonSizeX /30));
			startButton.setBackground(buttonColor2);
		}
		
		else if (e.getSource()==dieButton) 
		{	
			buttonAnimation(dieButton, -(buttonSizeX /30));
			dieButton.setBackground(buttonColor2);
			
			dieButton.setVisible(false);
			die.roll();
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) 
	{
		if (e.getSource()==startButton) 
		{
			if (buttonSizeIncreased) {buttonAnimation(startButton, -(buttonSizeX /30));}
			startButton.setBackground(buttonColor3);
		}
		
		else if (e.getSource()==dieButton) 
		{
			if (buttonSizeIncreased) {buttonAnimation(dieButton, -(buttonSizeX /30));}
			dieButton.setBackground(buttonColor3);
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) 
	{
		if (e.getSource()==startButton) 
		{
			buttonAnimation(startButton, (buttonSizeX /30));
			startButton.setBackground(buttonColor2);
		}
		
		else if (e.getSource()==dieButton) 
		{
			buttonAnimation(dieButton, (buttonSizeX /30));
			dieButton.setBackground(buttonColor2);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		if (e.getSource()==startButton) 
		{
			if (buttonSizeIncreased) {buttonAnimation(startButton, -(buttonSizeX /30));}
			startButton.setBackground(buttonColor1);
		}
		
		else if (e.getSource()==dieButton) 
		{
			if (buttonSizeIncreased) {buttonAnimation(dieButton, -(buttonSizeX /30));}
			dieButton.setBackground(buttonColor1);
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {}

	private void buttonAnimation(JLabel button, int change)
	{
		if ((change > 0 && buttonSizeIncreased) || (change < 0 && !buttonSizeIncreased)) {return;} //don't go beyond max/min size
		
		if (change > 0) {buttonSizeIncreased = true;}
		else {buttonSizeIncreased = false;}
		
		button.setBounds
		(
		 button.getX()-(change/2), button.getY()-(change/2),
		 button.getWidth()+change, button.getHeight()+change
		);
	}
}
