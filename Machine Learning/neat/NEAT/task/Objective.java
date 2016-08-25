package NEAT.task;

import NEAT.algorithm.Scholar;
import NEAT.algorithm.neural.NeuralNetwork;

public abstract class Objective {

	public int		inputNodeCount;
	public int		outputNodeCount;
	public int		targetFitness;
	public boolean	simultainiousTests;
	private Scholar			neat;

	public Objective(int inputNodes, int outputNodes, int targetFitness, boolean simTests) {
		inputNodeCount = inputNodes;
		outputNodeCount = outputNodes;
		simultainiousTests = simTests;
		this.targetFitness = targetFitness;
		
		setupNetwork();
	}

	public abstract int calculateFitness(NeuralNetwork n);

	public abstract Object[] getData();
	
	public abstract Object[] pushGameData();

	private void setupNetwork() {
		neat = new Scholar(this);
	}
	
	public Scholar getScholar() {
		return neat;
	}
}
