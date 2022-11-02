package RandGrowth;
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

public class RG_GUI_Panel extends JPanel implements MouseListener
{
	private static final long serialVersionUID = -5876435514501823550L;
	
	private static final int PANEL_WIDTH = 300;
	private static final int PANEL_HEIGHT = 800;
	
	private boolean buttonSizeIncreased = false;
	
	private JTextField pixelSizeInput;
	private JTextField simSpeedInput;
	private JTextField deathChanceInput;
	private JTextField reviveChanceInput;
	
	private JLabel dieRateLabel;
	private JLabel reviveRateLabel;
	private JLabel sizeLabel;
	private JLabel speedLabel;
	
	private final Color textColor = new Color(90, 90, 110);
	private final Color borderColor = new Color(120,120,150);
	
	private JLabel startButton;
	
	private final Color buttonColor1 = new Color(170, 170, 210);
	private final Color buttonColor2 = new Color(200, 200, 240);
	private final Color buttonColor3 = new Color(215, 215, 255);
	
	private int buttonSizeX = 150;
	private int buttonSizeY = 70;
	
	RandGrowthPanel panel;
	
	RG_GUI_Panel(RandGrowthPanel panel)
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
		pixelSizeInput = new JTextField("3");
		setTextFieldSettings(pixelSizeInput);
		pixelSizeInput.setBounds(93, 210, 110, 45);
		
		simSpeedInput = new JTextField("15");
		setTextFieldSettings(simSpeedInput);
		simSpeedInput.setBounds(93, 360, 110, 45);
		
		deathChanceInput = new JTextField("25");
		setTextFieldSettings(deathChanceInput);
		deathChanceInput.setBounds(93, 510, 110, 45);
		
		reviveChanceInput = new JTextField("35");
		setTextFieldSettings(reviveChanceInput);
		reviveChanceInput.setBounds(93, 660, 110, 45);
		
		//LABELS
		dieRateLabel = new JLabel("Chance for a pixel to die each frame");
		setLabelSettings(dieRateLabel);
		dieRateLabel.setBounds(35, 470, 250, 45);
		
		reviveRateLabel = new JLabel("Chance to bring a surrounding pixel to life");
		setLabelSettings(reviveRateLabel);
		reviveRateLabel.setBounds(17, 620, 270, 45);
		
		sizeLabel = new JLabel("Pixel size");
		setLabelSettings(sizeLabel);
		sizeLabel.setBounds(115, 170, 130, 45);
		
		speedLabel = new JLabel("Simulation Speed");
		setLabelSettings(speedLabel);
		speedLabel.setBounds(90, 320, 130, 45);	
	}
	
	private void setTextFieldSettings(JTextField textField)
	{
		textField.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 170), 3));
		textField.setFont(new Font("", Font.PLAIN, 15));
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
		label.setFont(new Font("", Font.BOLD, 13));
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
			
			int deathChance = Integer.parseInt(deathChanceInput.getText());
			int reviveChance = Integer.parseInt(reviveChanceInput.getText());
			int pixelSize = Integer.parseInt(pixelSizeInput.getText());
			int simSpeed = Integer.parseInt(simSpeedInput.getText());
			if (simSpeed != 0) {simSpeed = 1000/simSpeed;}
			else {simSpeed = 1000000;}
			
			panel.reset(deathChance, reviveChance, pixelSize, simSpeed);
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
	}
	
	@Override
	public void mouseEntered(MouseEvent e) 
	{
		if (e.getSource()==startButton) 
		{
			buttonAnimation(startButton, (buttonSizeX /30));
			startButton.setBackground(buttonColor2);
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
