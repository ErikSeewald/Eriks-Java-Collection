package SnakesAndLadders;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SnL_GUI extends JPanel implements MouseListener
{
private static final long serialVersionUID = -587643554501823550L;
	
	private static final int PANEL_WIDTH = 300;
	private static final int PANEL_HEIGHT = 780;
	
	private boolean buttonSizeIncreased = false;
	
	private JLabel playerCountLabel;
	JLabel currentPlayerLabel;
	
	private final Color textColor = new Color(90, 90, 110);
	private final Color borderColor = new Color(120,120,150);
	
	private JLabel startButton;
	private JLabel autoMoveButton;
	private JLabel playerCountButton;
	
	private final Color buttonColor1 = new Color(170, 170, 210);
	private final Color buttonColor2 = new Color(200, 200, 240);
	private final Color buttonColor3 = new Color(215, 215, 255);
	
	private int buttonSizeX = 150;
	private int buttonSizeY = 70;
	
	private SnL_Panel panel;
	
	private SpinDie die;
	private JLabel dieButton;
	
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
		
		autoMoveButton = new JLabel(" Auto Move");
		autoMoveButton.setBounds(60, 650,180,60);
		setButtonSettings(autoMoveButton);
		enableAutoMoveButton(false);
		
		playerCountButton = new JLabel("  1");
		playerCountButton.setBounds(120, 220, 60, 60);
		setButtonSettings(playerCountButton);
		
		//LABELS
		playerCountLabel = new JLabel("Player count");
		setLabelSettings(playerCountLabel);
		playerCountLabel.setBounds(87, 170, 250, 45);
		
		currentPlayerLabel = new JLabel("Player 1");
		setLabelSettings(currentPlayerLabel);
		currentPlayerLabel.setBounds(85, 420, 270, 45);
		currentPlayerLabel.setFont(new Font("", Font.BOLD, 35));
		
		//DIE
		die = new SpinDie(this);
		die.setBounds(100, 500, die.SIZE, die.SIZE);
		
		dieButton = new JLabel("  Roll");
		dieButton.setBounds(100, 500, die.SIZE, die.SIZE);
		setButtonSettings(dieButton);
		
		this.add(die);
		
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
		JLabel button = (JLabel) e.getSource();
		
		buttonAnimation(button, -(buttonSizeX /30));
		button.setBackground(buttonColor2);
		
		if (e.getSource()==startButton) 
		{
			panel.start(Integer.parseInt(playerCountButton.getText().trim()));
		}
		
		else if (e.getSource()==autoMoveButton) 
		{	
			enableAutoMoveButton(false);
			panel.autoMove();
		}
		
		else if (e.getSource()==playerCountButton)
		{
			int playerCount = (Integer.parseInt(playerCountButton.getText().trim())+1);
			if (playerCount == 6) {playerCount = 1;}
			playerCountButton.setText("  " + playerCount);
		}
		
		else if (e.getSource()==dieButton) 
		{	
			if (panel.hasStarted)
			{
				dieButton.setVisible(false);
				panel.setRolledGridPosition(die.roll());
				enableStartButton(false);
			}
		}		
	}
	
	@Override
	public void mousePressed(MouseEvent e) 
	{
		JLabel button = (JLabel) e.getSource();
		
		buttonAnimation(button, -(buttonSizeX /30));
		button.setBackground(buttonColor3);
	}
	
	@Override
	public void mouseEntered(MouseEvent e) 
	{
		JLabel button = (JLabel) e.getSource();
		
		buttonAnimation(button, (buttonSizeX /30));
		button.setBackground(buttonColor2);
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		JLabel button = (JLabel) e.getSource();
		
		if (buttonSizeIncreased) {buttonAnimation(button, -(buttonSizeX /30));}
		button.setBackground(buttonColor1);
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
	
	public void enableAutoMoveButton(boolean enable)
	{autoMoveButton.setVisible(enable);}
	
	public void enableDieButton(boolean enable)
	{dieButton.setVisible(enable);}
	
	public void enableStartButton(boolean enable)
	{startButton.setVisible(enable);}
	
	public void enableManualMove()
	{panel.moveAvailable = true;}
}
