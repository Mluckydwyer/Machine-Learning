package NEAT.algorithm.hierarchy;

import NEAT.algorithm.neural.NeuralNetwork;
import NEAT.task.Objective;

public class Genome extends Species {

	public NeuralNetwork network;
	
	public Genome(Objective obj) {
		super(obj);
		network = new NeuralNetwork(obj);
	}

}
