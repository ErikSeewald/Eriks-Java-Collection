package perfectParty.voters;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class representing the entire voting population, made up of smaller {@link VoterBlock}s.
 */
public class Population
{
	private int numPopulation;
	private ArrayList<VoterBlock> voterBlocks;
	
	public Population(int startingPopulation)
	{
		if (startingPopulation <= 0)
		{
			throw new IllegalArgumentException("Starting population cannot be <= 0");
		}
		this.numPopulation = startingPopulation;
		
		voterBlocks = new ArrayList<>();
	}
	
	public int getNumber()
	{
		return this.numPopulation;
	}
	
	/**
	 * Grows the voting population by the given number of people and regenerates the {@link VoterBlock}s.
	 * @param numPeople the number of people to grow the voting population by
	 * @param random the {@link Random} object to use for {@link VoterBlock} generation
	 */
	public void growByAndReorganize(int numPeople, Random random)
	{
		if (numPeople < 0)
		{
			throw new IllegalArgumentException("Cannot grow by a negative number of people");
		}
		this.numPopulation += numPeople;
		generateVoterBlocks(random);
	}
	
	/**
	 * Splits the total voting population into discrete {@link VoterBlock}s.
	 * The number of voters in each block is randomized within certain bounds so that there never 
	 * need to be too many block objects as the population grows.
	 */
	private void generateVoterBlocks(Random random)
	{
		voterBlocks.clear();
		
		int lowerBound = numPopulation / 1000;
		int upperBound = (int) Math.max(1, numPopulation / 100.0);
		
		int leftToAllocate = numPopulation;
		while (leftToAllocate > 0)
		{
			int blockSize = 0;
			if (leftToAllocate <= upperBound)
			{
				blockSize = leftToAllocate;
				leftToAllocate = 0;
			}
			
			else
			{
				blockSize = (int) Math.max(1, random.nextInt(upperBound - lowerBound) + lowerBound);
			}
			
			voterBlocks.add(new VoterBlock(blockSize));
			leftToAllocate -= blockSize;
		}
	}
}
