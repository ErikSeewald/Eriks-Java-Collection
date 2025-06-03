package juicePoet.poem;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import juicePoet.juice.JuiceAlgorithm;

/**
 * Class holding a list of all {@link Poem}s. Manages their creation and removal.
 */
public class PoemHandler
{
	private List<Poem> poems;
	
	public PoemHandler()
	{
		poems = new ArrayList<>();
	}
	
	/**
	 * Handles input dialog and creation of a new {@link Poem}. 
	 * Adds the {@link Poem} to the {@link PoemHandler}'s list.
	 * If the user submits an empty poem, the method simply returns without creating a {@link Poem} object.
	 */
	public void addPoem()
	{
		String poemText = JOptionPane.showInputDialog("Enter your poem");
		if (poemText == null || poemText.isEmpty()) {return;}
		
		if (poemText.length() > JuiceAlgorithm.MAX_POEM_LENGTH) 
		{
			poemText = poemText.substring(0, JuiceAlgorithm.MAX_POEM_LENGTH);
		}
		
		poems.add(new Poem(poemText, 100, 100));
	}
	
	public boolean removePoem(Poem poem)
	{
		return poems.remove(poem);
	}
	
	public List<Poem> getPoems() {return poems;}
}