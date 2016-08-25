package NEAT.algorithm.neural;

import NEAT.algorithm.hierarchy.Genome;

public class Connection extends Genome {

	private int inNode;
	private int outNode;
	private int innovationNum;
	
	private double weight;

	private boolean isEnabled;
	
	public Connection(int inNode, int outNode, int innovationNum, double weight, boolean isEnabled) {
		this.inNode = inNode;
		this.outNode = outNode;
		this.innovationNum = innovationNum;
		this.weight = weight;
		this.isEnabled = isEnabled;
	}
	
	public int getInnovationNum() {
		return innovationNum;
	}
	
	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public int getInNode() {
		return inNode;
	}

	public int getOutNode() {
		return outNode;
	}
}
