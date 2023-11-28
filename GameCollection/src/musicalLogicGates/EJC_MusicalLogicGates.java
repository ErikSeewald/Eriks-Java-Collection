package musicalLogicGates;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.Timer;

import ejcMain.EJC_Game;
import ejcMain.EJC_WindowEventHandler;
import musicalLogicGates.circuit.CircuitManager;
import musicalLogicGates.circuit.FileManager;
import musicalLogicGates.graphics.CircuitPanel;
import musicalLogicGates.graphics.EastGUI;
import musicalLogicGates.graphics.WestGUI;
import musicalLogicGates.music.BeatManager;

/**
 * An {@link EJC_Game} in which the player can build logic gate circuits and have them play audio.
 */
public class EJC_MusicalLogicGates extends JFrame implements EJC_Game, ActionListener 
{
	private static final long serialVersionUID = 1588751028197042712L;
	
	private CircuitPanel panel;
	private WestGUI westGUI;
	private EastGUI eastGUI;
	private CircuitManager circuitManager;
	private BeatManager beatManager;
	private Timer playingAnimationTimer;

	@Override
	public void start(EJC_WindowEventHandler eventHandler) 
	{
		this.addWindowListener(eventHandler);
		this.setTitle("Musical Logic Gates");

		circuitManager = new CircuitManager();
		beatManager = new BeatManager(this);
		playingAnimationTimer = new Timer(30, this);
		
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
		
		this.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == 'b')
				{
					circuitManager.updateOneStep();
					updateGraphics();
				}
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	public enum OnOff 
	{
		ON, OFF
	}
	
	/**
	 * Enters 'playing' mode and starts playing the music of the circuit.
	 */
	public void startPlaying()
	{
		if (circuitManager.isPlaying()) 
		{throw new IllegalStateException("Cannot start playing when already in 'playing' mode");}
		
		eastGUI.switchOnOffEditButtons(OnOff.OFF);
		westGUI.switchOnOffEditButtons(OnOff.OFF);
		circuitManager.startPlaying();
		beatManager.startBeat();
		playingAnimationTimer.start();
	}
	
	/**
	 * Exits 'playing' mode.
	 */
	public void stopPlaying()
	{
		if (!circuitManager.isPlaying()) 
		{throw new IllegalStateException("Cannot stop playing while not in 'playing' mode");}
		
		playingAnimationTimer.stop();
		beatManager.stopBeat();
		eastGUI.switchOnOffEditButtons(OnOff.ON);
		westGUI.switchOnOffEditButtons(OnOff.ON);
		circuitManager.stopPlaying();
	}
	
	/**
	 * Advances the circuit by one step/beat.
	 */
	public void beatAdvanceStep()
	{
		if (!circuitManager.isPlaying()) 
		{throw new IllegalStateException("Cannot advance step while not in 'playing' mode");}
		
		circuitManager.updateOneStep();
		updateGraphics();
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
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == playingAnimationTimer)
		{updateGraphics();}
	}
	
	@Override
	public void stop()
	{
		if (circuitManager.isPlaying())
		{stopPlaying();}
	}
}