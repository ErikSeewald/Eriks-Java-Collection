package randGrowth;
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

import ejcMain.EJC_GUI;

public class RG_GUI_Panel extends JPanel implements MouseListener
{
	private static final long serialVersionUID = -5876435514501823550L;
	private static final int WIDTH = 300, HEIGHT = 780;
	
	private boolean buttonSizeIncreased = false;
	
	private JTextField pixelSizeInput, simSpeedInput, deathChanceInput, reviveChanceInput;
	private JLabel dieRateLabel, reviveRateLabel, sizeLabel, speedLabel;
	
	private static final Color textColor = new Color(90, 90, 110);
	private static final Color borderColor = new Color(120,120,150);
	
	private JLabel startButton;
	
	private int buttonSizeX = 150, buttonSizeY = 70;
	
	private GrowthHandler growthHandler;
	
	RG_GUI_Panel(GrowthHandler growthHandler)
	{
		this.growthHandler = growthHandler;
		
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setBackground(new Color(220,220,250));
		this.setBorder(BorderFactory.createLineBorder(borderColor, 8));

		this.setLayout(null);
		
		//BUTTONS
		startButton = new JLabel("    Start");
		startButton.setBounds(73, 60, buttonSizeX, buttonSizeY);
		setButtonSettings(startButton);
		
		//INPUTS
		pixelSizeInput = new JTextField("3");
		initTextField(pixelSizeInput, 93, 210, 110, 45);
		simSpeedInput = new JTextField("15");
		initTextField(simSpeedInput, 93, 360, 110, 45);
		deathChanceInput = new JTextField("25");
		initTextField(deathChanceInput, 93, 510, 110, 45);
		reviveChanceInput = new JTextField("35");
		initTextField(reviveChanceInput, 93, 660, 110, 45);
		
		//LABELS
		setLabelSettings(dieRateLabel, "Chance for a pixel to die each frame", 35, 470, 250, 45 );
		setLabelSettings(reviveRateLabel, "Chance to bring a surrounding pixel to life", 17, 620, 270, 45);
		setLabelSettings(sizeLabel, "Pixel size", 115, 170, 130, 45);
		setLabelSettings(speedLabel, "Simulation Speed", 90, 320, 130, 45);
	}
	
	private void initTextField(JTextField textField, int a, int b, int c, int d)
	{
		textField.setBounds(a,b,c,d);
		textField.setBorder(BorderFactory.createLineBorder(new Color(130, 130, 150), 3));
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
	
	private void setLabelSettings(JLabel label, String name, int a, int b, int c , int d)
	{
		label = new JLabel(name);
		label.setBounds(a,b,c,d);
		label.setForeground(textColor);
		label.setFont(new Font("", Font.BOLD, 13));
		this.add(label);
	}
	
	private void setButtonSettings(JLabel button)
	{
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
		if (e.getSource()==startButton) 
		{	
			buttonAnimation(startButton, -(buttonSizeX /30));
			startButton.setBackground(EJC_GUI.b_color_highlight);
			readInputs();
		}
	}
	
	private void readInputs()
	{
		int deathChance = 1, reviveChance = 1, pixelSize = 1, simSpeed = 1;
		
		String deathChanceStr = deathChanceInput.getText();
		if (!deathChanceStr.isEmpty()) {deathChance = Integer.parseInt(deathChanceStr);}
		
		String reviveChanceStr = reviveChanceInput.getText();
		if (!reviveChanceStr.isEmpty()) {reviveChance = Integer.parseInt(reviveChanceStr);}

		String pixelSizeStr = pixelSizeInput.getText();
		if (!pixelSizeStr.isEmpty()) {pixelSize = Integer.parseInt(pixelSizeStr);}
		
		String simSpeedStr = simSpeedInput.getText();
		if (!simSpeedStr.isEmpty()) {simSpeed = Integer.parseInt(simSpeedStr);}
		
		growthHandler.reset(deathChance, reviveChance, pixelSize, simSpeed);
	}
	
	@Override
	public void mousePressed(MouseEvent e) 
	{
		if (e.getSource()==startButton) 
		{
			if (buttonSizeIncreased) {buttonAnimation(startButton, -(buttonSizeX /30));}
			startButton.setBackground(EJC_GUI.b_color_pressed);
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) 
	{
		if (e.getSource()==startButton) 
		{
			buttonAnimation(startButton, (buttonSizeX /30));
			startButton.setBackground(EJC_GUI.b_color_highlight);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		if (e.getSource()==startButton) 
		{
			if (buttonSizeIncreased) {buttonAnimation(startButton, -(buttonSizeX /30));}
			startButton.setBackground(EJC_GUI.b_color_basic);
		}
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
}