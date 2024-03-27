package juicePoet.juice;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import juicePoet.poem.Poem;

/**
 * Algorithm for extracting a {@link Juice} texture from a {@link Poem} {@link String}
 */
public class JuiceAlgorithm
{
	public static final int MAX_POEM_LENGTH = 1000;
	
	private static final Set<Character> WHITESPACE_CHARS = new HashSet<>();
	private static final Set<Character> VOWEL_CHARS = new HashSet<>();
	static 
	{
		WHITESPACE_CHARS.add(' ');
		WHITESPACE_CHARS.add('\t');
		WHITESPACE_CHARS.add('\n');
		
		VOWEL_CHARS.add('a');
		VOWEL_CHARS.add('e');
		VOWEL_CHARS.add('i');
		VOWEL_CHARS.add('o');
		VOWEL_CHARS.add('u');
	}
	
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
		
		//STEP 3: PIXEL PATTERNS
		// get a seed value based on the number of occurrences of certain character sets within the poem text
		// and then use this seed to generate a pattern of pixels each time

		//PATTERN 1
		int whitespaceCount = countOccurrencesOfChars(WHITESPACE_CHARS, poemText);
		extractPatternWithSeed(juice, poemText, whitespaceCount);
		
		//PATTERN 2
		int vowelCount = countOccurrencesOfChars(VOWEL_CHARS, poemText.toLowerCase());
		extractPatternWithSeed(juice, poemText, vowelCount);
		
		//PATTERN 3
		int lastCharCount = countOccurrencesOfChars(Set.of(poemText.charAt(poemText.length() - 1)), poemText);
		extractPatternWithSeed(juice, poemText, lastCharCount);
		
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
		int textLength = poemText.length();
		
		// STEP 1: HUE	
		//Split all the chars of poemText into 3 categories based on their char code. Each char in each category
		//contributes to the color's red, green and blue value respectively		
		poemText = poemText.toUpperCase(); // case insensitive division of letters
		
		int[] totals = new int[3];
		for (char c : poemText.toCharArray())
		{	
			totals[c % 3]++;
		}
		
		//order in which the modulo 3 counts contribute to the r,g,b
		int order = textLength % 3;
		int r = (int) (totals[order] * 255.0 / textLength );
		int g = (int) ( totals[(order + 1) % 3] * 255.0  / textLength);
		int b = (int) (totals[(order + 2) % 3] * 255.0 / textLength );
		
		//STEP 2: BRIGHTNESS
		// Scale the brightness based on the char code of the very first char
		float[] hsb = Color.RGBtoHSB(r, g, b, null);
		float brightness = ((poemText.charAt(0) % 10) / 10f); // get value between 0.0 and 1.0
		
		// instead of Math truncation: take min brightness and add factor of a smaller scale so the original value
		// redistributes nicely in the range left over without the min brightness
		brightness =  0.2f + (brightness * 0.8f);
		
		return Color.getHSBColor(hsb[0], hsb[1], brightness);
	}
	
	/**
	 * Extracts a pixel pattern from the given poem text based on the given seed and then adds it to the texture
	 * of the given {@link Juice}.
	 * 
	 * @param juice the {@link Juice} to update
	 * @param poemText the {@link String} text to extract the pattern from
	 * @param seed the seed for the pattern extraction
	 */
	private static void extractPatternWithSeed(Juice juice, String poemText, int seed)
	{
		lengthExceptionCheck(poemText);
		
		int textLength = poemText.length();
		int seedIndex = seed % textLength;
		char patternChar = poemText.charAt(seedIndex);
		
		//COLOR
		// count the occurrences of the char at seedIndex and the two chars next to it (modulo textLength)
		// and use each count as the r,g or b value respectively
		int[] rgb = new int[3];
		for (int i = 0; i < 3; i++)
		{
			int charIndex = (seedIndex + i) % textLength;
			
			rgb[i] = countOccurrencesOfChars(Set.of(poemText.charAt(charIndex)), poemText);
			rgb[i] = (int) (rgb[i] * 255.0 / (textLength * 0.25));
			rgb[i] = Math.min(255, rgb[i]);
		}
		Color patternColor = new Color(rgb[0], rgb[1], rgb[2]);
		
		//PATTERN
		//wrap the poemText around the texture (repeating if necessary) and then draw a pixel if the char
		//at the index of the pixel in the poemText matches the given pattern char
		for (int x = 0; x < Juice.TEXTURE_SIZE; x++)
		{
			for (int y = 0; y < Juice.TEXTURE_SIZE; y++)
			{
				int pixelIndexInPoemText = (x * Juice.TEXTURE_SIZE + y) % textLength;	
				if (poemText.charAt(pixelIndexInPoemText) == patternChar)
				{
					juice.setTexturePixel(x, y, patternColor);
				}
			}
		}
	}
	
	/**
	 * Counts the number of times any of the given chars occur in the given poem text.
	 * 
	 * @param chars the chars to look for
	 * @param poemText the {@link String} to look through
	 * @return the number of times any of the given chars occur
	 */
	private static int countOccurrencesOfChars(Set<Character> chars, String poemText)
	{	
		lengthExceptionCheck(poemText);
		
		int count = 0;
		for (char c : poemText.toCharArray())
		{
			if (chars.contains(c)) {count++;}
		}
		
		return count;
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
