package perfectParty.party;

/**
 * Class extending {@link Party}. Control the moves of the CPU's {@link Party} against the Player's {@link Party}.
 */
public class CpuParty extends Party
{
	public CpuParty(String name, PolicyPoints policyPoints)
	{
		super(name, policyPoints);
	}
}
