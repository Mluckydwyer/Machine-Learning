package NEAT.algorithm.hierarchy;

import java.util.Random;

import NEAT.algorithm.neural.NeuralNetwork;

public class Genome extends Species {

	public NeuralNetwork	network;
	public int fitness;

	/*
	 * Randomly updates weight of a selected connection. newWeight = oldWeight +/- Rand(0 to genome$MutationRate[[“Step”]]);? or newWeight = rand(-2 to 2);
	 */
	private double	pointMutatePercent;

	/*
	 * Adds new random connection (weight -2 to 2)
	 */
	private double	linkMutatePercent;

	/*
	 * Splits connection to add a new node
	 */
	private double	nodeMutatePercent;

	/*
	 * Enables/disables a random connection
	 */
	private double	toggleMutatePercent;	// 

	public Genome() {
		network = new NeuralNetwork();
		randomizeMutationChance();
	}

	private void mutate() {
		
		randomizeMutationChance();
	}
	
	private void randomizeMutationChance() {
		Random r = new Random();
		double sum = 0;
		
		pointMutatePercent = r.nextDouble();
		linkMutatePercent = r.nextDouble();
		nodeMutatePercent = r.nextDouble();
		toggleMutatePercent = r.nextDouble();
		
		sum += pointMutatePercent + linkMutatePercent + nodeMutatePercent + toggleMutatePercent;
		
		pointMutatePercent /= sum * 1;
		linkMutatePercent /= sum * 1;
		nodeMutatePercent /= sum * 1;
		toggleMutatePercent /= sum * 1;
	}
	
	private void pointMutate() {

	}

	private void linkMutate() {

	}

	private void nodeMutate() {

	}

	private void toggleMutate() {

	}
}
