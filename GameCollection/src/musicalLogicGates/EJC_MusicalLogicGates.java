package musicalLogicGates;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import ejcMain.EJC_Game;
import ejcMain.EJC_WindowEventHandler;
import musicalLogicGates.circuit.CircuitManager;
import musicalLogicGates.graphics.CircuitPanel;
import musicalLogicGates.graphics.EastGUI;
import musicalLogicGates.graphics.WestGUI;

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
		this.setVisible(true);
	}
	
	public void updateGraphics() 
	{
		panel.updateGraphics();
	}
	
	public void saveCircuit()
	{
		FileManager.saveCircuit(circuitManager.getGates());
	}
	
	public void loadCircuit()
	{
		circuitManager.loadCircuit(FileManager.loadCircuit());
		updateGraphics();
	}

	@Override
	public void stop()
	{}
}