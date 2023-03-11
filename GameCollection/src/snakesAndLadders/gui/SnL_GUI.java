package snakesAndLadders.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Main.EJC_GUI;
import snakesAndLadders.board.BoardHandler;

public class SnL_GUI extends JPanel implements MouseListener
{
	private static final long serialVersionUID = -587643554501823550L;
	private static final int PANEL_WIDTH = 300, PANEL_HEIGHT = 780;

	private JLabel playerCountLabel;
	private JLabel currentPlayerLabel;
	
	private static final Color textColor = new Color(90, 90, 110);
	private static final Color borderColor = new Color(120,120,150);
	
	private JLabel startButton, autoMoveButton, playerCountButton;
	private boolean buttonSizeIncreased = false;
	
	private int buttonSizeX = 150, buttonSizeY = 70;
	
	private BoardHandler boardHandler;
	private SpinDie die;
	private JLabel dieButton;
	
	public SnL_GUI()
	{
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		this.setBackground(new Color(220,220,250));
		this.setBorder(BorderFactory.createLineBorder(borderColor, 8));

		this.setLayout(null);
		
		//BUTTONS
		startButton = new JLabel("    Start");
		setButtonSettings(startButton, 73, 60, buttonSizeX, buttonSizeY);
		
		playerCountButton = new JLabel("  1");
		setButtonSettings(playerCountButton, 120, 220, 60, 60);
		
		autoMoveButton = new JLabel(" Auto Move");
		setButtonSettings(autoMoveButton, 60, 650,180,60);
		enableAutoMoveButton(false);
		
		//LABELS
		playerCountLabel = new JLabel( "Player count");
		setLabelSettings(playerCountLabel, 87, 170, 250, 45);
		
		currentPlayerLabel = new JLabel("Player 1");
		setLabelSettings(currentPlayerLabel,85, 420, 270, 45 );
		currentPlayerLabel.setFont(new Font("", Font.BOLD, 35));
		
		//DIE
		die = new SpinDie(this);
		die.setBounds(100, 500, SpinDie.SIZE, SpinDie.SIZE);
		
		dieButton = new JLabel("  Roll");
		setButtonSettings(dieButton, 100, 500, SpinDie.SIZE, SpinDie.SIZE);
		
		this.add(die);
	}
	
	public void addBoardHandler(BoardHandler boardHandler)
	{this.boardHandler = boardHandler;}
	
	private void setLabelSettings(JLabel label, int a, int b, int c, int d)
	{
		label.setBounds(a,b,c,d);
		label.setForeground(textColor);
		label.setFont(new Font("", Font.BOLD, 20));
		this.add(label);
	}
	
	private void setButtonSettings(JLabel button, int a, int b, int c, int d)
	{
		button.setBounds(a,b,c,d);
		button.setBackground(EJC_GUI.b_color_basic);
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
		button.setBackground(EJC_GUI.b_color_highlight);
		
		if (e.getSource()==startButton) 
		{
			boardHandler.start(Integer.parseInt(playerCountButton.getText().trim()));
		}
		
		else if (e.getSource()==autoMoveButton) 
		{	
			enableAutoMoveButton(false);
			boardHandler.autoMove();
		}
		
		else if (e.getSource()==playerCountButton)
		{
			int playerCount = (Integer.parseInt(playerCountButton.getText().trim())+1);
			if (playerCount == 6) {playerCount = 1;}
			playerCountButton.setText("  " + playerCount);
		}
		
		else if (e.getSource()==dieButton) 
		{	
			dieButton.setVisible(false);
			boardHandler.setRolledGridPosition(die.roll());
			enableStartButton(false);
		}		
	}
	
	@Override
	public void mousePressed(MouseEvent e) 
	{
		JLabel button = (JLabel) e.getSource();
		
		buttonAnimation(button, -(buttonSizeX /30));
		button.setBackground(EJC_GUI.b_color_pressed);
	}
	
	@Override
	public void mouseEntered(MouseEvent e) 
	{
		if (e.getSource() == playerCountButton)
		{playerCountButton.setBackground(EJC_GUI.b_color_highlight);}
		
		else
		{
			JLabel button = (JLabel) e.getSource();
			
			buttonAnimation(button, (buttonSizeX /30));
			button.setBackground(EJC_GUI.b_color_highlight);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		JLabel button = (JLabel) e.getSource();
		
		if (buttonSizeIncreased) {buttonAnimation(button, -(buttonSizeX /30));}
		button.setBackground(EJC_GUI.b_color_basic);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {}

	private void buttonAnimation(JLabel button, int change)
	{
		if ((change > 0 && buttonSizeIncreased) || (change < 0 && !buttonSizeIncreased)) {return;} //don't go beyond max/min size
		buttonSizeIncreased = change > 0;
		
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
	
	public void enableMove()
	{
		autoMoveButton.setVisible(true);
		boardHandler.setMoveAvailable(true);
	}
	
	public void stopRollTimer()
	{die.stop();}
	
	public void setCurrentPlayerLabel(String text) {currentPlayerLabel.setText(text);}
}