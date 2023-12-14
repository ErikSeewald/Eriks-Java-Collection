package musicalLogicGates.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import ejcMain.EJC_GUI;
import ejcMain.EJC_Util.StateControl;
import ejcMain.menu.MainMenu;
import musicalLogicGates.EJC_MusicalLogicGates;
import musicalLogicGates.circuit.CircuitManager;
import musicalLogicGates.gates.Gate.GateType;

/**
 * GUI for the east side of a {@link CircuitPanel}.
 */
public class EastGUI extends JPanel implements MouseListener
{
	private static final long serialVersionUID = -587643554501823550L;
	private static final int PANEL_WIDTH = 200;

	//COLORS
	private static final Color textColor = new Color(90, 90, 110);
	private static final Color borderColor = new Color(90,90,120);
	
	//BUTTONS
	private JLabel clearButton;
	
	private JLabel notButton;
	private JLabel inButton;
	private JLabel outButton;
	private Map<JLabel, GateType> gateButtonsToType = new HashMap<>();
	
	private JLabel saveButton;
	private JLabel loadButton;
	
	private int buttonSizeX = 150, buttonSizeY = 70;
	private boolean buttonSizeIncreased = false;
	
	//REFERENCES
	private CircuitManager circuitManager;
	private EJC_MusicalLogicGates game;
	
	/**
	 * Creates a new {@link EastGUI} with the given {@link CircuitManager} and {@link EJC_MusicalLogicGates}.
	 * 
	 * @param circuitManager the {@link CircuitManager} for this {@link EastGUI}
	 * @param game the {@link EJC_MusicalLogicGates} for this {@link EastGUI}
	 */
	public EastGUI(CircuitManager circuitManager, EJC_MusicalLogicGates game)
	{
		if (circuitManager == null)
		{throw new NullPointerException("circuitManager cannot be null!");}
		this.circuitManager = circuitManager;
		
		if (game == null)
		{throw new NullPointerException("game cannot be null!");}
		this.game = game;
		
		this.setPreferredSize(new Dimension(PANEL_WIDTH, CircuitPanel.PANEL_HEIGHT));
		this.setBackground(new Color(120,120,150));
		this.setBorder(BorderFactory.createLineBorder(borderColor, 8));

		//BUTTONS
		clearButton = new JLabel("  CLEAR");
		setButtonSettings(clearButton, 23, 40, buttonSizeX, buttonSizeY);
		
		notButton = new JLabel("    NOT");
		setButtonSettings(notButton, 23, 180, buttonSizeX, buttonSizeY);
		gateButtonsToType.put(notButton, GateType.NOT);
		inButton = new JLabel("      IN");
		setButtonSettings(inButton, 23, 260, buttonSizeX, buttonSizeY);
		gateButtonsToType.put(inButton, GateType.IN);
		outButton = new JLabel("    OUT");
		setButtonSettings(outButton, 23, 340, buttonSizeX, buttonSizeY);
		gateButtonsToType.put(outButton, GateType.OUT);
		
		saveButton = new JLabel("   SAVE");
		setButtonSettings(saveButton, 23, 500, buttonSizeX, buttonSizeY);
		loadButton = new JLabel("   LOAD");
		setButtonSettings(loadButton, 23, 580, buttonSizeX, buttonSizeY);
		
		this.setLayout(null);
	}
	
	/**
	 * Helper method to disable and enable all edit buttons while in 'playing' mode
	 */
	public void switchOnOffEditButtons(StateControl onOff)
	{
		boolean b = (onOff == StateControl.ON);
		
		clearButton.setVisible(b);
		notButton.setVisible(b);
		inButton.setVisible(b);
		outButton.setVisible(b);
		saveButton.setVisible(b);
		loadButton.setVisible(b);
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
		button.setFont(new Font(MainMenu.fontName, Font.BOLD, 32));
		button.addMouseListener(this);
		this.add(button);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		JLabel button = (JLabel) e.getSource();
		
		if (button.equals(clearButton))
		{circuitManager.clearGates();}
		
		else if (gateButtonsToType.containsKey(button))
		{
			circuitManager.addGate(gateButtonsToType.get(button));
		}
		
		else if (button.equals(saveButton))
		{game.saveCircuit();}
		
		else if (button.equals(loadButton))
		{game.loadCircuit();}
		
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
