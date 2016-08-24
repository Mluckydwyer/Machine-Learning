package NEAT.algorithm.neural;

import NEAT.algorithm.hierarchy.Genome;
import NEAT.task.Objective;

public class Connection extends Genome {

	private int inNode;
	private int outNode;
	private int innovationNum;
	
	private double weight;
	
	private boolean isEnabled;
	
	public Connection(int inNode, int outNode, int innovationNum, double weight, boolean isEnabled, Objective obj) {
		super(obj);
		this.inNode = inNode;
		this.outNode = outNode;
		this.innovationNum = innovationNum;
		this.weight = weight;
		this.isEnabled = isEnabled;
	}
}
