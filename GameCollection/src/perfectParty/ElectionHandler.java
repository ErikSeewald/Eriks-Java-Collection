package perfectParty;

import perfectParty.party.CpuParty;
import perfectParty.party.Party;
import perfectParty.party.PolicyPoints;
import perfectParty.voters.Population;

public class ElectionHandler
{
	private Population population;
	public static final int STARTING_POPULATION = 100_000;
	public static final double POPULATION_GROWTH_FACTOR = 1.5;
	
	public static final int INIT_POLICY_POINTS = 12;
	private CpuParty cpuParty;
	private Party playerParty;
	
	public ElectionHandler()
	{
		this.population = new Population(STARTING_POPULATION);
		this.cpuParty = new CpuParty("CPU", PolicyPoints.getInitialPoints(INIT_POLICY_POINTS));
		this.playerParty = new Party("Player", PolicyPoints.getInitialPoints(INIT_POLICY_POINTS));
	}
}
