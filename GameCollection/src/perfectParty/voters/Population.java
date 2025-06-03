package perfectParty.voters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import perfectParty.election.ElectionResult;
import perfectParty.party.Party;
import perfectParty.party.Policy;
import perfectParty.party.PolicyCollection;

/**
 * Class representing the entire voting population, made up of smaller {@link VoterBlock}s.
 */
public class Population
{
	private long numPopulation;
	private ArrayList<VoterBlock> voterBlocks;
	private long STARTING_POPULATION;
	
	/**
	 * Creates a new {@link Population} object with the given starting population and generates {@link VoterBlock}s
	 * using the given {@link Random} object.
	 */
	public Population(int startingPopulation, Random random)
	{
		if (startingPopulation <= 0)
		{
			throw new IllegalArgumentException("Starting population cannot be <= 0");
		}
		this.STARTING_POPULATION = startingPopulation;
		this.numPopulation = startingPopulation;
		
		voterBlocks = new ArrayList<>();
		generateVoterBlocks(random);
	}
	
	public long getNumber()
	{
		return this.numPopulation;
	}
	
	/**
	 * Returns the distribution of {@link Preference}s of voters for the given {@link Policy}.
	 * The returned {@link HashMap} maps {@link Preference} to the number of voters with that {@link Preference}
	 * in regards to the given {@link Policy}.
	 */
	public HashMap<Preference, Long> getPreferenceDistribution(Policy policy)
	{
		HashMap<Preference, Long> distribution = (HashMap<Preference, Long>) Stream.of(Preference.values())
                .collect(Collectors.toMap(e -> e, e -> 0L)); // Initialize all with 0
		
		for (VoterBlock block : voterBlocks)
		{
			Preference preference = block.getPreference(policy);
			Long oldAmount = distribution.get(preference);
			if (oldAmount == null)
			{
				oldAmount = 0L;
			}
			
			distribution.put(preference, oldAmount + block.numVoters);
		}
		
		return distribution;
	}

	/**
	 * Votes on the given {@link Party} array and returns the {@link ElectionResult}.
	 */
	public ElectionResult vote(Party[] parties)
	{				
		ElectionResult result = new ElectionResult();
		for (VoterBlock block : voterBlocks)
		{
			Party blockWinner = block.vote(parties);
			result.addVotes(blockWinner, block.numVoters);
		}
		
		return result;
	}
	
	/**
	 * Grows the voting population by the given growth factor and regenerates the {@link VoterBlock}s.
	 * @param growthFactor the factor to grow the population by
	 * @param random the {@link Random} object to use for {@link VoterBlock} generation
	 */
	public void growByAndReorganize(double growthFactor, Random random)
	{
		if (growthFactor < 0)
		{
			throw new IllegalArgumentException("Cannot grow by a negative growth factor");
		}
		this.numPopulation *= growthFactor;
		generateVoterBlocks(random);
	}
	
	/**
	 * Resets the population size back to its initial size and regenerates the {@link VoterBlocks} with the given
	 * {@link Random} object.
	 */
	public void resetPopulation(Random random)
	{
		this.numPopulation = this.STARTING_POPULATION;
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
		
		long lowerBound = numPopulation / 1000;
		long upperBound = Long.max(1, numPopulation / 100);
		
		long leftToAllocate = numPopulation;
		while (leftToAllocate > 0)
		{
			long blockSize = 0;
			if (leftToAllocate <= upperBound)
			{
				blockSize = leftToAllocate;
				leftToAllocate = 0;
			}
			
			else
			{
				blockSize = Long.max(1, random.nextLong(upperBound - lowerBound) + lowerBound);
			}
			
			voterBlocks.add(new VoterBlock(blockSize));
			leftToAllocate -= blockSize;
		}
	}
	
	/**
	 * Generates each {@link VoterBlock}s {@link Preference}s for each policy in the given {@link PolicyCollection}
	 * @param policyCollection the {@link PolicyCollection} to generate for
	 * @param random The {@link Random} object to be used for generation
	 */
	public void generatePreferences(PolicyCollection policyCollection, Random random)
	{
		for (VoterBlock voterBlock : voterBlocks)
		{
			voterBlock.generatePreferences(policyCollection, random);
		}
	}
}
