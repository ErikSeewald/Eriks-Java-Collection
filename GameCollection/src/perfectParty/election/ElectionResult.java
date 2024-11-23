package perfectParty.election;

import java.util.HashMap;

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
	
	public void addVotes(Party party, long votes)
	{
		if (votes < 0)
		{
			throw new IllegalArgumentException("Cannot set votes to a negative number");
		}
		
		Long prevVotes = resultMap.get(party);
		if (prevVotes != null)
		{
			votes += prevVotes;
		}
		
		resultMap.put(party, votes);
	}
	
	public long getVotes(Party party)
	{
		Long votes = resultMap.get(party);
		if (votes != null)
		{
			return votes;
		}
		return 0;
	}
}
