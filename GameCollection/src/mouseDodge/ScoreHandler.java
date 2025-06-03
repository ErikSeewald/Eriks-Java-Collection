package mouseDodge;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ScoreHandler 
{
	private int time;
	private int bestTime;
	
	ScoreHandler() {reset();}
	
	public void reset()
	{this.time = 0; this.load();}
	
	public void finalize()
	{
		if (this.time > this.bestTime)
		{
			bestTime = time;
			this.save();
		}
	}
	
	private void save()
	{
		try 
		{
			FileWriter fw = new FileWriter("Highscore.txt",false);
			fw.write(""+this.bestTime);
			fw.close();
		}
		catch (IOException e) {System.out.println("Save failed");}
	}
	
	private void load()
	{
		try 
		{
			BufferedReader reader = new BufferedReader(new FileReader("Highscore.txt"));
			String text = reader.readLine();
			reader.close();

			if (text != null && text.length() < 50)
			{this.bestTime = Integer.parseInt(text);}	

		}
		catch (IOException e) {this.bestTime = 0;}
	}
	
	public int getTime() {return this.time;}

	public int getBestTime() {return this.bestTime;}
	
	public void increase() {this.time++;}
}