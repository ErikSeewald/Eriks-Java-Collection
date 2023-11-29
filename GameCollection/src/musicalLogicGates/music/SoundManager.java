package musicalLogicGates.music;

import java.util.Random;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;
import musicalLogicGates.gates.Gate.GateType;

/**
 * Class to manage all sound effects for {@link EJC_MusicalLogicGates}.
 */
public class SoundManager 
{
    private Sequencer sequencer;
    private Synthesizer synthesizer;
    
    private Sequence[] soundSequences = new Sequence[9];
    private Random random;
    
    private static final int bannedInstruments[] = {49, 78, 79, 89, 93, 95, 101, 119, 121, 122, 125, 126};

    /**
     * Creates a new {@link SoundManager}.
     */
    public SoundManager() 
    {
    	random = new Random();
        try 
        {
            sequencer = MidiSystem.getSequencer();
            synthesizer = MidiSystem.getSynthesizer();

            sequencer.open();
            synthesizer.open();
            
            regenerateSequences();
              
        } catch (Exception e) {e.printStackTrace();}
    }
    
    /**
     * Generates a new random set of sound {@link Sequence}s for all {@link GateType}s.
     */
    public void regenerateSequences()
    {
    	for (int i = 0; i < soundSequences.length; i++) 
    	{
            try
            {
				soundSequences[i] = createMidiSequence(random.nextInt(60)+30, 50, 300);
				
			} catch (InvalidMidiDataException e) {e.printStackTrace();}
        }
    }
    
    /**
     * Creates a new midi sound effect {@link Sequence}.
     * 
     * @param note the note to play
     * @param volume the volume of the sound
     * @param duration the duration of the sound
     * @return new midi sound effect {@link Sequence}
     * @throws InvalidMidiDataException
     */
    private Sequence createMidiSequence(int note, int volume, int duration) throws InvalidMidiDataException 
    {
        Sequence sequence = new Sequence(Sequence.PPQ, 1);
        Track track = sequence.createTrack();
        
        //INSTRUMENT
        ShortMessage programChange = new ShortMessage();
        
        int instrument = 0;
        boolean validInstrument = false;
        while (!validInstrument)
        {
        	instrument = random.nextInt(127);
        	validInstrument = true;
        	
            for (int i = 0; i < bannedInstruments.length; i++)
            {
            	if (instrument == bannedInstruments[i])
            	{validInstrument = false;}
            }
        }
        
        programChange.setMessage(ShortMessage.PROGRAM_CHANGE, 0, instrument, 0);
        track.add(new MidiEvent(programChange, 0));

        //NOTE-ON
        ShortMessage onMessage = new ShortMessage();
        onMessage.setMessage(ShortMessage.NOTE_ON, 0, note, volume);
        track.add(new MidiEvent(onMessage, 0));

        //NOTE-OFF
        ShortMessage offMessage = new ShortMessage();
        offMessage.setMessage(ShortMessage.NOTE_OFF, 0, note, volume);
        track.add(new MidiEvent(offMessage, duration));

        return sequence;
    }

    /**
     * Plays a sound effect based on the given {@link GateType} on a new {@link Thread}.
     * 
     * @param type the {@link GateType} to play a sound effect for
     */
    public void playGateSound(GateType type) 
    {
    	int soundIndex = type.ordinal();

        new Thread(() -> 
        {
            try 
            {
                Sequence sequence = soundSequences[soundIndex];
                sequencer.setSequence(sequence);
                sequencer.start();

                // wait for the note to end
                Thread.sleep(sequence.getTickLength());

                sequencer.stop();
            } catch (Exception e) {e.printStackTrace();}
        }).start();
    }

    /**
     * Stops and closes active resources.
     */
    public void stop() 
    {
        if (sequencer != null && sequencer.isOpen()) 
        {sequencer.close();}
        if (synthesizer != null && synthesizer.isOpen()) 
        {synthesizer.close();}
    }
}