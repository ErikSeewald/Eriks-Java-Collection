package musicalLogicGates.music;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import musicalLogicGates.EJC_MusicalLogicGates;

/**
 * Class to manage the beat progression through the circuit in {@link EJC_MusicalLogicGates}.
 */
public class BeatManager implements ActionListener
{
	private Timer beat;
	private EJC_MusicalLogicGates game;
	
	/**
	 * Creates a new {@link BeatManager} with the given {@link EJC_MusicalLogicGates}.
	 * 
	 * @param game the {@link EJC_MusicalLogicGates} for this {@link BeatManager}
	 */
	public BeatManager(EJC_MusicalLogicGates game)
	{
		if (game == null)
		{throw new IllegalArgumentException("game cannot be null!");}
		this.game = game;
		
		beat = new Timer(1000, this);
	}
	
	/**
	 * Returns whether the beat is running.
	 * 
	 * @return {@link boolean} whether the beat is running
	 */
	public boolean isRunning() {return beat.isRunning();}
	
	/**
	 * Starts the beat.
	 * 
	 * @throws IllegalStateException if the beat is already running
	 */
	public void startBeat()
	{
		if (beat.isRunning())
		{throw new IllegalStateException("Cannot start beat while beat is running");}
		
		beat.start();
	}
	
	/**
	 * Stops the beat.
	 * 
	 * @throws IllegalStateException if the beat is not running
	 */
	public void stopBeat()
	{
		if (!beat.isRunning())
		{throw new IllegalStateException("Cannot stop beat while beat is not running");}
		
		beat.stop();
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		game.beatAdvanceStep();
	}
}
