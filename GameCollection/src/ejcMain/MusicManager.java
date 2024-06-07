package ejcMain;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MusicManager
{
	public static enum EJC_Track
	{
		UrgentTaxation
	}

	private static final HashMap<EJC_Game, ArrayList<Clip>> activeTracks = new HashMap<>();

	public static boolean loopTrack(EJC_Game game, EJC_Track track)
	{
		if (track == null)
		{
			return false;
		}

		ArrayList<Clip> tracks = activeTracks.get(game);
		if (tracks == null)
		{
			tracks = new ArrayList<>();
			activeTracks.put(game, tracks);
		}

		try
		{
			Clip clip = AudioSystem.getClip();
			AudioInputStream inputStream = getTrackAudioStream(track);
			clip.open(inputStream);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			tracks.add(clip);
		} catch (Exception ex)
		{
			return false; // Just play no music. That is fine too :)
		}
		
		return true;
	}

	private static AudioInputStream getTrackAudioStream(EJC_Track track)
	{
		if (track == null)
		{
			return null;
		}

		// MUSIC FOLDER
		String musicFolderPath = "Music/";
        File file = new File(musicFolderPath);

        // Fallback path
        if (!file.exists() || !file.isDirectory()) 
        {
        	musicFolderPath = "../Music/";
        }
        
        // MUSIC FILE
        String musicFilePath = musicFolderPath;
		switch (track)
		{
		case UrgentTaxation:
			musicFilePath += "Urgent Taxation.wav";
			break;
		default:
			return null;
		}
		file = new File(musicFilePath);
		

		// AUDIO INPUT STREAM
		try
		{
			return AudioSystem.getAudioInputStream(file);
		} catch (IOException | UnsupportedAudioFileException ex)
		{
			return null;
		}
	}

	public static void closeAllTracks(EJC_Game game)
	{
		ArrayList<Clip> tracks = activeTracks.get(game);
		if (tracks == null)
		{
			return;
		}

		for (Clip clip : tracks)
		{
			clip.close();
		}

		tracks.clear();
		activeTracks.remove(game);
	}
}
