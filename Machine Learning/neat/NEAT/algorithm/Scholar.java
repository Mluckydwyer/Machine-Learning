package NEAT.algorithm;

import NEAT.algorithm.neural.NeuralNetwork;
import NEAT.task.Objective;

public class Scholar {

	public Objective obj;
	
	private int nodeIDCounter;
	private int innovationNumCounter;
	
	public Scholar(Objective obj) {
		this.obj = obj;
		nodeIDCounter = 1;
		innovationNumCounter = 1;
	}

	public int getNextNodeID() {
		return nodeIDCounter++;
	}
	
	public int getNextInnovationNum() {
		return innovationNumCounter++;
	}
	
	public void testGeneration() {
		obj.calculateFitness(new NeuralNetwork(obj));
	}
}
