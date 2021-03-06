package NEAT.algorithm.neural;

public class Connection extends NeuralNetwork {

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

	@Override
	public String toString() {
		return "Connection [inNode=" + inNode + ", outNode=" + outNode + ", innovationNum=" + innovationNum + ", weight=" + weight + ", isEnabled=" + isEnabled + "]";
	}
}
