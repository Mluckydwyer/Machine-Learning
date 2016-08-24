package neural.nodes;

import neural.Network;
import neural.NuralNetwork;

public class HiddenNode extends Network {

	private double[]	weights;
	private double[]	inputs;
	private double		biasWeight;
	private double		value;

	public HiddenNode() {
		weights = new double [NuralNetwork.inputNodes];
		randomizeWeights();
		setValue(0);
	}

	private void randomizeWeights() {
		for (double l : weights) {
			l = Math.random();
			if (Math.random() > .5) l *= -1;
		}

		biasWeight = Math.random();
		if (Math.random() > .5) biasWeight *= -1;
	}

	private void getInputs() {
		this.inputs = getInputValues();
	}

	public void value() {
		double[] calculatedValues = new double [inputs.length];

		for (int i = 0; i < inputs.length; i++)
			calculatedValues[i] = inputs[i] * weights[i];

		setValue(average(calculatedValues));
	}

	private double average(double[] calculatedValues) {
		double total = 0;

		for (double v : calculatedValues)
			total += v;

		total += getBias() * biasWeight;

		return sigmoid(total);
	}

	public void calcValue() {
		getInputs();
		value();
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	private double sigmoid(double v) {
		return 1 / (1 + Math.pow(Math.E, -v));
	}

}
