package musicalLogicGates;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.Timer;
import ejcMain.EJC_Game;
import ejcMain.EJC_Util.StateControl;
import ejcMain.EJC_WindowEventHandler;
import musicalLogicGates.circuit.CircuitManager;
import musicalLogicGates.circuit.FileManager;
import musicalLogicGates.graphics.CircuitPanel;
import musicalLogicGates.graphics.EastGUI;
import musicalLogicGates.graphics.WestGUI;
import musicalLogicGates.music.BeatManager;
import musicalLogicGates.music.SoundManager;

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
	private SoundManager soundManager;

	@Override
	public void start(EJC_WindowEventHandler eventHandler) 
	{
		this.addWindowListener(eventHandler);
		this.setTitle("Musical Logic Gates");

		soundManager = new SoundManager();
		circuitManager = new CircuitManager(soundManager);
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
	}
	
	/**
	 * Enters 'playing' mode and starts playing the music of the circuit.
	 * 
	 * @throws IllegalStateException if {@link CircuitManager} is already in 'playing' mode
	 */
	public void startPlaying()
	{
		if (circuitManager.isPlaying()) 
		{throw new IllegalStateException("Cannot start playing when already in 'playing' mode");}
		
		eastGUI.switchOnOffEditButtons(StateControl.OFF);
		westGUI.switchOnOffEditButtons(StateControl.OFF);
		circuitManager.startPlaying();
		beatManager.startBeat();
		playingAnimationTimer.start();
	}
	
	/**
	 * Exits 'playing' mode.
	 * 
	 * @throws IllegalStateException if {@link CircuitManager} is not in 'playing' mode
	 */
	public void stopPlaying()
	{
		if (!circuitManager.isPlaying()) 
		{throw new IllegalStateException("Cannot stop playing while not in 'playing' mode");}
		
		playingAnimationTimer.stop();
		beatManager.stopBeat();
		eastGUI.switchOnOffEditButtons(StateControl.ON);
		westGUI.switchOnOffEditButtons(StateControl.ON);
		circuitManager.stopPlaying();
	}
	
	/**
	 * Advances the circuit by one step/beat.#
	 * 
	 * @throws IllegalStateException if {@link CircuitManager} is not in 'playing' mode
	 */
	public void beatAdvanceStep()
	{
		if (!circuitManager.isPlaying()) 
		{throw new IllegalStateException("Cannot advance step while not in 'playing' mode");}
		
		circuitManager.updateOneStep();
		updateGraphics();
	}
	
	/**
	 * Changes the {@link SoundManager}s instruments and restarts 'playing' mode.
	 */
	public void changeInstruments()
	{
		soundManager.regenerateSequences();
		stopPlaying();
		startPlaying();
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
		
		soundManager.stop();
	}
}