package perfectParty.election;

import java.util.Random;

import perfectParty.gui.FrameManager;
import perfectParty.party.CpuParty;
import perfectParty.party.Party;
import perfectParty.party.PolicyCollection;
import perfectParty.party.PolicyPoints;
import perfectParty.voters.Population;

public class ElectionHandler
{
	// POPULATION
	private Population population;
	public static final int STARTING_POPULATION = 100_000;
	public static final double POPULATION_GROWTH_FACTOR = 1.5;
	
	// PARTIES
	public static final int INIT_POLICY_POINTS = 12;
	private CpuParty cpuParty;
	private Party playerParty;
	private Party[] parties;
	
	// POLICIES
	private PolicyCollection policyCollection;
	
	// ELECTION
	private int currentRound;
	
	// UTIL AND GUI
	private FrameManager frameManager;
	private final Random random;
	
	public ElectionHandler(FrameManager frameManager)
	{
		this.frameManager = frameManager;
		this.random = new Random();
		this.population = new Population(STARTING_POPULATION, random);
		
		this.cpuParty = new CpuParty("CPU", PolicyPoints.getInitialPoints(INIT_POLICY_POINTS));
		this.playerParty = new Party("Player", PolicyPoints.getInitialPoints(INIT_POLICY_POINTS));
		this.parties = new Party[] {cpuParty, playerParty};
		
		this.policyCollection = new PolicyCollection();
		this.currentRound = 0;
	}
	
	public PolicyCollection getPolicyCollection()
	{
		return this.policyCollection;
	}
	
	public Population getPopulation()
	{
		return this.population;
	}
	
	public Party getCPUParty()
	{
		return this.cpuParty;
	}
	
	public void startRound()
	{
		policyCollection.generateCollection(this.currentRound + 3);
		population.generatePreferences(policyCollection, random);
		
		//playerParty.spendPoints(12, policyCollection.getPolicies().get(1));
		
		// CURRENTLY BROKEN
		cpuParty.distributePoints(policyCollection, population, random);
	}
	
	public void runElection()
	{	
		ElectionResult result = population.vote(parties);
		System.out.println("CPU: " + result.getVotes(cpuParty));
		System.out.println("Player: " + result.getVotes(playerParty));
	}
}
