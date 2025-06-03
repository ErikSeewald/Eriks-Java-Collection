package perfectParty.election;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import perfectParty.party.Party;

/**
 * Data class storing the result of a single election
 */
public class ElectionResult
{
	private final HashMap<Party, Long> resultMap;

	public ElectionResult()
	{
		this.resultMap = new HashMap<>();
	}

	/**
	 * Adds the given amount of votes to the total for the given {@link Party}
	 */
	public void addVotes(Party party, long votes)
	{
		if (votes < 0)
		{
			throw new IllegalArgumentException("Cannot set votes to a negative number");
		}

		resultMap.merge(party, votes, Long::sum);
	}

	/**
	 * Returns the total number of votes for the given {@link Party}
	 */
	public long getVotes(Party party)
	{
		Long votes = resultMap.get(party);
		if (votes != null)
		{
			return votes;
		}
		return 0;
	}
	
	/**
	 * Returns the relative percentage of votes for the given {@link Party}
	 */
	public int getPercentage(Party party)
	{
		long totalVotes = getTotalVotes();
		return totalVotes == 0 ? 0 : (int) ((getVotes(party) * 100.0) / totalVotes);
	}

	/**
	 * Returns the total number of votes cast
	 */
	public long getTotalVotes()
	{
		return resultMap.values().stream().mapToLong(Long::longValue).sum();
	}
	
	/**
	 * Returns the party with the largest number of absolute votes. In the event of a tie, only one party
	 * is returned. Use {@link #breakTieInFavor(Party favored)} to avoid this behavior.
	 */
	public Party getWinner()
	{
		return resultMap.entrySet()
			    .stream()
			    .max(Map.Entry.comparingByValue())
			    .map(Map.Entry::getKey)
			    .orElse(null);
	}
	
	/**
	 * If a tie exists, a single vote is removed from the other parties with the same number of votes and given
	 * to the favored party. Note that this does not work if parties tie with 0 votes.
	 */
	public void breakTieInFavor(Party favored)
	{
		long favoredVotes = getVotes(favored);
		if (favoredVotes == 0) {return;}
		
		for (Entry<Party, Long> entry : resultMap.entrySet())
		{
			Party party = entry.getKey();
			if (party == favored) {continue;}
			
			if (entry.getValue().equals(favoredVotes))
			{
				resultMap.merge(party, -1L, Long::sum);
				resultMap.merge(favored, 1L, Long::sum);
			}
		}
	}
}