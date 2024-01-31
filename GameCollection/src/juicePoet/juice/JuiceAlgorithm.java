package juicePoet.juice;

import java.awt.Color;
import juicePoet.poem.Poem;

public class JuiceAlgorithm
{
	public static final int MAX_POEM_LENGTH = 1000;
	
	/**
	 * Juices the given poem. Generates a {@link Juice} object from the given {@link Poem}.
	 * 
	 * @param poem the {@link Poem} to juice
	 * @return the extracted {@link Juice}
	 */
	public static Juice juice(Poem poem)
	{	
		poemExceptionCheck(poem);
		
		String poemText = poem.getText();
		Juice juice = new Juice();
		
		//STEP 1: CUT TO MAX LENGTH
		if (poemText.length() > MAX_POEM_LENGTH)
		{
			poemText = poemText.substring(0, MAX_POEM_LENGTH);
		}
		
		//STEP 2: MAIN COLOR
		Color mainColor = extractMainColor(poemText);
		juice.fillTexture(mainColor);
		
		//STEP 3: SINGLE PIXELS
		
		
		return juice;
	}
	
	/**
	 * Extracts a main {@link Color} to fill the {@link Juice} texture with based on the given {@link String} poemText.
	 * 
	 * @param poemText the {@link String} to extract the main {@link Color} from
	 * @return the extracted {@link Color}
	 */
	private static Color extractMainColor(String poemText) 
	{
		lengthExceptionCheck(poemText);
		
		// STEP 1: HUE	
		//Split all the chars of poemText into 3 categories based on their char code. Each char in each category
		//contributes to the color's red, green and blue value respectively		
		poemText = poemText.toUpperCase(); // case insensitive division of letters
		
		int redTotal = 0;
		int greenTotal = 0;
		int blueTotal = 0;
		
		for (char c : poemText.toCharArray())
		{
			if (c < 70) {redTotal++;}
			else if (c < 80) {greenTotal++;}
			else {blueTotal++;}
		}
		
		int textLength = poemText.length();
		
		int r = (int) (redTotal * 255.0 / textLength );
		int g = (int) (greenTotal * 255.0  / textLength);
		int b = (int) (blueTotal * 255.0 / textLength );
		
		//STEP 2: BRIGHTNESS
		// Scale the brightness based on the char code of the very first char
		float[] hsb = Color.RGBtoHSB(r, g, b, null);
		float brightness = ((poemText.charAt(0) % 10) / 10f); // get value between 0.0 and 1.0
		
		// instead of Math truncation: take min brightness and add factor of a smaller scale so the original value
		// redistributes nicely in the range left over without the min brightness
		brightness =  0.2f + (brightness * 0.8f); 
		
		return Color.getHSBColor(hsb[0], hsb[1], brightness);
	}
	
	
	//---------------------------------------EXCEPTION CHECKS---------------------------------------
	
	/**
	 * Checks the given {@link Poem} for exceptions.
	 * 
	 * @param poem the {@link Poem} to check
	 * @throws NullPointerException if the {@link Poem} is null 
	 * @throws IllegalArgumentException if the poemText is null or empty
	 */
	private static void poemExceptionCheck(Poem poem)
	{
		if (poem == null) 
		{throw new NullPointerException("Poem cannot be null!");}
		
		String poemText = poem.getText();
		if (poemText == null || poemText.isEmpty())
		{throw new IllegalArgumentException("Poem text cannot be null or empty!");}
	}
	
	/**
	 * Checks if the given poemText is within the bounds of MAX_POEM_LENGTH.
	 * 
	 * @param poemText the {@link String} to check
	 * @throws NullPointerException if the poemText is null
	 * @throws IllegalArgumentException if the poemText is empty or longer than MAX_POEM_LENGTH
	 */
	private static void lengthExceptionCheck(String poemText) 
	{
		if (poemText == null) 
		{throw new NullPointerException("Poem text cannot be null!");}
		
		if (poemText.isEmpty())
		{throw new IllegalArgumentException("Poem text cannot be empty!");}
		
		if (poemText.length() > MAX_POEM_LENGTH)
		{
			throw new IllegalArgumentException
			("Poem text (length " + poemText.length() + ") is longer than MAX_POEM_LENGTH " + MAX_POEM_LENGTH);
		}
	}
}
