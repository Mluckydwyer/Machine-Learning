package NEAT.algorithm.hierarchy;

import NEAT.algorithm.neural.NeuralNetwork;
import NEAT.task.Objective;

public class Genome extends Species {

	public NeuralNetwork	network;

	private double			pointMutatePercent;		// Randomly updates weight
													// of a selected connection.
													// newWeight = oldWeight +/-
													// Rand(0 to
													// genome$MutationRate[[“Step”]]);?
													// or newWeight = rand(-2 to
													// 2);
	
	private double			linkMutatePercent;		// Adds new random
													// connection (weight -2 to
													// 2)
	
	private double			nodeMutatePercent;		// Splits connection to add
													// new node
	
	private double			toggleMutatePercent;	// Enables/disables a random
													// connection

	public Genome(Objective obj) {
		super(obj);
		network = new NeuralNetwork(obj);
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
