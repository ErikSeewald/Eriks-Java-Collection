package Insects;
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

public class Insects_GUI extends JPanel implements MouseListener
{
	private static final long serialVersionUID = -5876435514501823550L;
	
	private static final int PANEL_WIDTH = 250;
	private static final int PANEL_HEIGHT = 650;
	
	private boolean buttonSizeIncreased = false;
	
	private JTextField addAmountInput;
	
	private JLabel addAmountLabel;
	
	private JLabel antAmountLabel;
	private JLabel antAmountCounter;
	
	private JLabel changeRateLabel;
	private JLabel changeRateCounter;
	
	
	private final Color textColor = new Color(90, 90, 110);
	private final Color borderColor = new Color(120,120,150);
	
	private JLabel startButton;
	private JLabel addButton;
	private JLabel removeButton;
	
	private final Color buttonColor1 = new Color(170, 170, 210);
	private final Color buttonColor2 = new Color(200, 200, 240);
	private final Color buttonColor3 = new Color(215, 215, 255);
	
	private InsectsPanel panel;
	
	Insects_GUI(InsectsPanel panel)
	{
		this.panel = panel;
		
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		this.setBackground(new Color(220,220,250));
		this.setBorder(BorderFactory.createLineBorder(borderColor, 8));

		this.setLayout(null);
		
		//BUTTONS
		startButton = new JLabel("    Start");
		startButton.setBounds(50, 60, 150, 70);
		setButtonSettings(startButton);
		
		addButton = new JLabel("  +");
		addButton.setBounds(93, 465, 60, 60);
		setButtonSettings(addButton);
		
		removeButton = new JLabel("  -");
		removeButton.setBounds(93, 545, 60, 60);
		setButtonSettings(removeButton);
		removeButton.setFont(new Font("", Font.BOLD, 35));
		
		//INPUTS
		addAmountInput = new JTextField("10");
		setTextFieldSettings(addAmountInput);
		addAmountInput.setBounds(70, 400, 110, 45);
		
		//LABELS
		addAmountLabel = new JLabel("Add / Remove");
		setLabelSettings(addAmountLabel);
		addAmountLabel.setBounds(65, 360, 250, 45);
		
		antAmountLabel = new JLabel("Insect amount");
		setLabelSettings(antAmountLabel);
		antAmountLabel.setBounds(62, 160, 250, 45);
		
		antAmountCounter = new JLabel(""+panel.antAmount);
		setLabelSettings(antAmountCounter);
		antAmountCounter.setBounds(108, 190, 250, 45);
		antAmountCounter.setFont(new Font("", Font.PLAIN, 16));
		
		changeRateLabel = new JLabel("Change rate");
		setLabelSettings(changeRateLabel);
		changeRateLabel.setBounds(73, 250, 250, 45);
		
		changeRateCounter = new JLabel("0");
		setLabelSettings(changeRateCounter);
		changeRateCounter.setBounds(120, 280, 250, 45);
		changeRateCounter.setFont(new Font("", Font.PLAIN, 16));
	}
	
	private void setTextFieldSettings(JTextField textField)
	{
		textField.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 170), 3));
		textField.setFont(new Font("", Font.PLAIN, 15));
		textField.setHorizontalAlignment(JTextField.CENTER);
		this.add(textField);
		
		textField.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent e) 
			{
				if ((e.getKeyChar() >= '0' && e.getKeyChar() <= '9') || e.getKeyChar() == '') //-> Delete
				{textField.setEditable(true);} 
				else 
				{textField.setEditable(false);}
				
				if (textField.getText().length() >= 5 && e.getKeyChar() != '') // limit to 5 characters
				{textField.setEditable(false);}
			}
		});
	}
	
	private void setLabelSettings(JLabel label)
	{
		label.setForeground(textColor);
		label.setFont(new Font("", Font.BOLD, 18));
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
		{panel.start();}
		
		else 
		{
			String changeString = addAmountInput.getText();
			if (!changeString.isEmpty())
			{
				panel.changeAmount = Integer.parseInt(changeString);
				boolean increase = e.getSource() == addButton;
				panel.changeAntAmount(increase);
			}
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) 
	{
		if (e.getSource()==startButton) 
		{
			if (buttonSizeIncreased) {buttonAnimation(startButton, -(150 /30));}
			startButton.setBackground(buttonColor3);
		}
		
		else 
		{
			JLabel button = (JLabel) e.getSource();
			button.setBounds
			(
					button.getX()+3, button.getY()+3,
					button.getWidth()-6, button.getHeight()-6
			);
			
			button.setBackground(buttonColor3);
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) 
	{
		if (e.getSource()==startButton) 
		{
			buttonAnimation(startButton, (150 /30));
			startButton.setBackground(buttonColor2);
		}
		
		else 
		{
			JLabel button = (JLabel) e.getSource();
			button.setBackground(buttonColor2);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		if (e.getSource()==startButton) 
		{
			if (buttonSizeIncreased) {buttonAnimation(startButton, -(150 /30));}
			startButton.setBackground(buttonColor1);
		}
		
		else 
		{
			JLabel button = (JLabel) e.getSource();
			button.setBackground(buttonColor1);
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) 
	{
		if (e.getSource()!= startButton)
		{
			JLabel button = (JLabel) e.getSource();
			button.setBounds
			(
					button.getX()-3, button.getY()-3,
					button.getWidth()+6, button.getHeight()+6
			);
		}
	}

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
	
	public void setAntAmountCounter(int amount)
	{
		antAmountCounter.setText(""+amount);
		
		int offset = 0;
		if (amount == 100000) {offset = 10;}
		else if (amount >= 10000) {offset = 5;}
		else if (amount < 1000) {offset = -5;}
		antAmountCounter.setLocation(108-offset, 190);
	}
	
	public void setChangeRate(double change)
	{
		String changeRate = String.format("%.2f", change); //rounds to two decimal places
		changeRateCounter.setText(changeRate);
		
		int offset = 0;
		int length = changeRate.length();
		if (length > 5) {offset = 15;}
		else if (length > 4) {offset = 10;}
		else if (length > 3) {offset = 5;}
		changeRateCounter.setLocation(120-offset, 280);
	}
}