package musicalLogicGates.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ejcMain.EJC_GUI;
import musicalLogicGates.EJC_MusicalLogicGates;
import musicalLogicGates.EJC_MusicalLogicGates.OnOff;
import musicalLogicGates.circuit.CircuitManager;
import musicalLogicGates.gates.Gate.GateType;

/**
 * GUI for the west side of a {@link CircuitPanel}.
 */
public class WestGUI extends JPanel implements MouseListener
{
	private static final long serialVersionUID = -587643554501823550L;
	private static final int PANEL_WIDTH = 200;

	//COLORS
	private static final Color textColor = new Color(90, 90, 110);
	private static final Color borderColor = new Color(90,90,120);
	
	//BUTTONS
	private JLabel playButton;
	
	private JLabel andButton, nandButton;
	private JLabel orButton, norButton;
	private JLabel xorButton, xnorButton;
	
	private int buttonSizeX = 150, buttonSizeY = 70;
	private boolean buttonSizeIncreased = false;
	
	//REFERENCES
	private CircuitManager circuitManager;
	private EJC_MusicalLogicGates game;
	
	public WestGUI(CircuitManager circuitManager, EJC_MusicalLogicGates game)
	{
		if (circuitManager == null)
		{throw new IllegalArgumentException("circuitManager cannot be null!");}
		this.circuitManager = circuitManager;
		
		if (game == null)
		{throw new IllegalArgumentException("game cannot be null!");}
		this.game = game;
		
		this.setPreferredSize(new Dimension(PANEL_WIDTH, CircuitPanel.PANEL_HEIGHT));
		this.setBackground(new Color(120,120,150));
		this.setBorder(BorderFactory.createLineBorder(borderColor, 8));

		//BUTTONS
		playButton = new JLabel("   PLAY");
		setButtonSettings(playButton, 23, 30, buttonSizeX, buttonSizeY);
		
		andButton = new JLabel("    AND");
		setButtonSettings(andButton, 23, 180, buttonSizeX, buttonSizeY);
		nandButton = new JLabel("   NAND");
		setButtonSettings(nandButton, 23, 260, buttonSizeX, buttonSizeY);
		
		orButton = new JLabel("     OR");
		setButtonSettings(orButton, 23, 360, buttonSizeX, buttonSizeY);
		norButton = new JLabel("    NOR");
		setButtonSettings(norButton, 23, 440, buttonSizeX, buttonSizeY);
		
		xorButton = new JLabel("    XOR");
		setButtonSettings(xorButton, 23, 540, buttonSizeX, buttonSizeY);
		xnorButton = new JLabel("   XNOR");
		setButtonSettings(xnorButton, 23, 620, buttonSizeX, buttonSizeY);
		
		this.setLayout(null);
	}
	
	/**
	 * Helper method to disable and enable all edit buttons while in 'playing' mode
	 */
	public void switchOnOffEditButtons(OnOff onOff)
	{
		boolean b = onOff == OnOff.ON ? true : false;
		
		andButton.setVisible(b);
		nandButton.setVisible(b);
		orButton.setVisible(b);
		norButton.setVisible(b);
		xorButton.setVisible(b);
		xnorButton.setVisible(b);
	}
	
	/**
	 * Sets the settings for the given {@link JLabel} button and adds it to the {@link JPanel}.
	 * 
	 * @param button the {@link JLabel} button
	 * @param a the x coordinate
	 * @param b the y coordinate
	 * @param c the width
	 * @param d the height
	 */
	private void setButtonSettings(JLabel button, int a, int b, int c, int d)
	{
		button.setBounds(a,b,c,d);
		button.setBackground(EJC_GUI.b_color_basic);
		button.setForeground(textColor);
		button.setOpaque(true);
		button.setBorder(BorderFactory.createLineBorder(borderColor, 3));
		button.setFont(new Font("", Font.BOLD, 32));
		button.addMouseListener(this);
		this.add(button);
	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		JLabel button = (JLabel) e.getSource();
		
		if (button.equals(playButton))
		{
			if (circuitManager.isPlaying())
			{
				game.stopPlaying();
				playButton.setText("   PLAY");
			}
			
			else
			{
				game.startPlaying();
				playButton.setText("   STOP");
			}	
		}
		
		else if (button.equals(andButton))
		{circuitManager.addGate(GateType.AND);}
		
		else if (button.equals(nandButton))
		{circuitManager.addGate(GateType.NAND);}
		
		else if (button.equals(orButton))
		{circuitManager.addGate(GateType.OR);}
		
		else if (button.equals(norButton))
		{circuitManager.addGate(GateType.NOR);}
		
		else if (button.equals(xorButton))
		{circuitManager.addGate(GateType.XOR);}
		
		else if (button.equals(xnorButton))
		{circuitManager.addGate(GateType.XNOR);}
		
		buttonAnimation(button, -(buttonSizeX /30));
		button.setBackground(EJC_GUI.b_color_highlight);
		
		game.updateGraphics();
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
		JLabel button = (JLabel) e.getSource();
			
		buttonAnimation(button, (buttonSizeX /30));
		button.setBackground(EJC_GUI.b_color_highlight);
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

	/**
	 * Handles animating the given {@link JLabel} button by a given change rate.
	 * 
	 * @param button the {@link JLabel} button to animate
	 * @param change the amount of change
	 */
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
