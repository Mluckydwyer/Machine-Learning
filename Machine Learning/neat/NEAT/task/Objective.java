package NEAT.task;

import java.util.ArrayList;

import NEAT.algorithm.Scholar;
import NEAT.algorithm.hierarchy.Species;
import NEAT.algorithm.neural.NeuralNetwork;

public abstract class Objective {

	public int		inputNodeCount;
	public int		outputNodeCount;
	public int		targetFitness;
	public boolean	simultainiousTests;
	private Scholar			neat;
	
	protected int currentGeneration;
	protected int currentSpecies;
	protected int currentGenome;

	public Objective(int inputNodes, int outputNodes, int targetFitness, boolean simTests) {
		inputNodeCount = inputNodes;
		outputNodeCount = outputNodes;
		simultainiousTests = simTests;
		this.targetFitness = targetFitness;
		
		setupNetwork();
	}

	public abstract int calculateFitness(NeuralNetwork n, int cGen, int cSpec, int cGenome);
	
	public ArrayList<Species> calculateFitness(ArrayList<Species> s) {
		return null;
	}

	public abstract double[] getData();
	
	public abstract void pushGameData();

	private void setupNetwork() {
		neat = new Scholar(this);
	}
	
	public Scholar getScholar() {
		return neat;
	}
}
