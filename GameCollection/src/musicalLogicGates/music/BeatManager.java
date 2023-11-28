package musicalLogicGates.music;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import musicalLogicGates.EJC_MusicalLogicGates;

public class BeatManager implements ActionListener
{
	Timer beat;
	EJC_MusicalLogicGates game;
	
	public BeatManager(EJC_MusicalLogicGates game)
	{
		if (game == null)
		{throw new IllegalArgumentException("game cannot be null!");}
		this.game = game;
		
		beat = new Timer(1000, this);
	}
	
	public boolean isRunning() {return beat.isRunning();}
	
	public void startBeat()
	{
		if (beat.isRunning())
		{throw new IllegalStateException("Cannot start beat while beat is running");}
		
		beat.start();
	}
	
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
