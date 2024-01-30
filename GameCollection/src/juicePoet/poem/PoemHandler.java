package juicePoet.poem;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class PoemHandler
{
	private List<Poem> poems;
	
	public PoemHandler()
	{
		poems = new ArrayList<>();
	}
	
	public void addPoem()
	{
		String poemText = JOptionPane.showInputDialog("Enter your poem");
		if (poemText == null || poemText.isEmpty()) {return;}
		
		if (poemText.length() > 1000) 
		{
			poemText = poemText.substring(0, 1000);
		}
		
		poems.add(new Poem(poemText, 100, 100));
	}
	
	public boolean removePoem(Poem poem)
	{
		return poems.remove(poem);
	}
	
	public List<Poem> getPoems() {return poems;}
}
