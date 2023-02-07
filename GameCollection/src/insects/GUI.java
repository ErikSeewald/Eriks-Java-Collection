package insects;
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

public class GUI extends JPanel implements MouseListener
{
	private static final long serialVersionUID = -5876435514501823550L;
	
	public static final int PANEL_WIDTH = 250;
	public static final int PANEL_HEIGHT = 650;
	
	//INPUTS
	private JTextField addAmountInput = new JTextField("10");
	
	//LABELS
	private JLabel addAmountLabel = new JLabel("Add / Remove");
	
	private JLabel antAmountLabel = new JLabel("Insect amount");
	private JLabel antAmountCounter;
	
	private JLabel changeRateLabel = new JLabel("Change rate");
	private JLabel changeRateCounter = new JLabel("0");
	
	private static final Color textColor = new Color(90, 90, 110);
	private static final Color borderColor = new Color(120,120,150);
	
	//BUTTONS
	private boolean buttonSizeIncreased = false;
	
	private JLabel startButton = new JLabel("    Start");
	private JLabel addButton = new JLabel("  +");
	private JLabel removeButton = new JLabel("  -");
	
	private static final Color b_color_basic = new Color(170, 170, 210);
	private static final Color b_color_highlight = new Color(200, 200, 240);
	private static final Color b_color_pressed = new Color(215, 215, 255);
	
	private InsectsPanel panel;
	
	GUI(InsectsPanel panel)
	{
		this.panel = panel;
		
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		this.setBackground(new Color(220,220,250));
		this.setBorder(BorderFactory.createLineBorder(borderColor, 8));

		this.setLayout(null);
		this.initialize();
	}
	
	//---------------------------------------GUI_CONTROL---------------------------------------
	
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
				int changeAmount = Integer.parseInt(changeString);
				if (e.getSource() == removeButton) {changeAmount*= -1;}
				panel.simulation.changeAntAmount(changeAmount);
			}
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) 
	{
		if (e.getSource()==startButton) 
		{
			if (buttonSizeIncreased) {buttonAnimation(startButton, -(150 /30));}
			startButton.setBackground(b_color_pressed);
		}
		
		else 
		{
			JLabel button = (JLabel) e.getSource();
			button.setBounds
			(
					button.getX()+3, button.getY()+3,
					button.getWidth()-6, button.getHeight()-6
			);
			
			button.setBackground(b_color_pressed);
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) 
	{
		if (e.getSource()==startButton) 
		{
			buttonAnimation(startButton, (150 /30));
			startButton.setBackground(b_color_highlight);
		}
		
		else 
		{
			JLabel button = (JLabel) e.getSource();
			button.setBackground(b_color_highlight);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		if (e.getSource()==startButton) 
		{
			if (buttonSizeIncreased) {buttonAnimation(startButton, -(150 /30));}
			startButton.setBackground(b_color_basic);
		}
		
		else 
		{
			JLabel button = (JLabel) e.getSource();
			button.setBackground(b_color_basic);
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
		buttonSizeIncreased = change > 0;
		
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
	
	//---------------------------------------INITIALIZATION---------------------------------------
	
	private void initialize()
	{
		//BUTTONS
		setButtonSettings(startButton, 50, 60, 150, 70);
		setButtonSettings(addButton, 93, 465, 60, 60);

		setButtonSettings(removeButton, 93, 545, 60, 60);
		removeButton.setFont(new Font("", Font.BOLD, 35));

		//INPUTS
		setTextFieldSettings(addAmountInput, 70, 400, 110, 45);

		//LABELS
		setLabelSettings(addAmountLabel, 65, 360, 250, 45);	
		setLabelSettings(antAmountLabel, 62, 160, 250, 45);

		antAmountCounter = new JLabel("");
		setLabelSettings(antAmountCounter, 108, 190, 250, 45);
		antAmountCounter.setFont(new Font("", Font.PLAIN, 16));

		setLabelSettings(changeRateLabel, 73, 250, 250, 45);
		setLabelSettings(changeRateCounter, 120, 280, 250, 45);
		changeRateCounter.setFont(new Font("", Font.PLAIN, 16));
	}
	
	private void setTextFieldSettings(JTextField textField, int a, int b, int c, int d)
	{
		textField.setBounds(a,b,c,d);
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
	
	private void setLabelSettings(JLabel label, int a, int b, int c, int d)
	{
		label.setBounds(a,b,c,d);
		label.setForeground(textColor);
		label.setFont(new Font("", Font.BOLD, 18));
		this.add(label);
	}
	
	private void setButtonSettings(JLabel button, int a, int b, int c, int d)
	{
		button.setBounds(a,b,c,d);
		button.setBackground(b_color_basic);
		button.setForeground(textColor);
		button.setOpaque(true);
		button.setBorder(BorderFactory.createLineBorder(borderColor, 3));
		button.setFont(new Font("", Font.BOLD, 30));
		button.addMouseListener(this);
		this.add(button);
	}
}