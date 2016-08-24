package neural.nodes;

import neural.Network;

public class InputNode extends Network {

	private double input;

	public InputNode() {
		input = 0;
	}

	public InputNode(double input) {
		setInput(input);
	}

	public double getInput() {
		return input;
	}
	
	public void setInput(double input) {
		this.input = input;
	}
	
	public void clearInput() {
		setInput(0);
	}
}
