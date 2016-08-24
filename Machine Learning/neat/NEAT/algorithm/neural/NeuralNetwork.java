package NEAT.algorithm.neural;

import java.util.ArrayList;

import NEAT.algorithm.hierarchy.Genome;
import NEAT.task.Objective;

public class NeuralNetwork extends Genome {
	
	public ArrayList<Node> nodes;
	public ArrayList<Connection> connections;
	
	public NeuralNetwork(Objective obj) {
		super(obj);
		nodes = new ArrayList<Node>();
		connections = new ArrayList<Connection>();
	}
	
}
