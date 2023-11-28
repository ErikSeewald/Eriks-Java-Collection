package musicalLogicGates;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import ejcMain.EJC_Game;
import ejcMain.EJC_WindowEventHandler;
import musicalLogicGates.circuit.CircuitManager;
import musicalLogicGates.graphics.CircuitPanel;
import musicalLogicGates.graphics.EastGUI;
import musicalLogicGates.graphics.WestGUI;

/**
 * An {@link EJC_Game} in which the player can build logic gate circuits and have them play audio.
 */
public class EJC_MusicalLogicGates extends JFrame implements EJC_Game 
{
	private static final long serialVersionUID = 1588751028197042712L;
	
	private CircuitPanel panel;
	private WestGUI westGUI;
	private EastGUI eastGUI;
	private CircuitManager circuitManager;

	@Override
	public void start(EJC_WindowEventHandler eventHandler) 
	{
		this.addWindowListener(eventHandler);
		this.setTitle("Musical Logic Gates");

		circuitManager = new CircuitManager();
		
		panel = new CircuitPanel(circuitManager);
		this.add(panel, BorderLayout.CENTER);
		
		westGUI = new WestGUI(circuitManager, this);
		this.add(westGUI, BorderLayout.WEST);
		
		eastGUI = new EastGUI(circuitManager, this);
		this.add(eastGUI, BorderLayout.EAST);
		
		this.setResizable(false);
		this.pack();
		
		circuitManager.loadCircuit(FileManager.loadString(FileManager.full_adder));
		
		this.setVisible(true);
	}
	
	/**
	 * Updates all graphics across the {@link EJC_MusicalLogicGates} instance.
	 */
	public void updateGraphics() 
	{
		panel.updateGraphics();
	}
	
	/**
	 * Handles interaction with the {@link FileManager} and {@link CircuitManager} to save the current circuit.
	 */
	public void saveCircuit()
	{
		FileManager.saveCircuit(circuitManager.getGates());
	}
	
	/**
	 * Handles interaction with the {@link FileManager} and {@link CircuitManager} to load a circuit from a file.
	 */
	public void loadCircuit()
	{
		circuitManager.loadCircuit(FileManager.loadCircuit());
		updateGraphics();
	}

	@Override
	public void stop()
	{}
}