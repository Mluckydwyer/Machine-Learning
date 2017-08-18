package neural.nodes;

import java.util.Random;

import neural.Network;
import neural.NeuralNetwork;

public class HiddenNode {

	public double[]		weights;
	public double[]		inputs;
	private double		biasWeight;
	private double		value;
	private Random		random;
	
	public HiddenNode() {
		weights = new double [NeuralNetwork.inputNodes];
		random = new Random(NeuralNetwork.RANDOM_SEED);
		randomizeWeights();
		setValue(0);
	}

	private void randomizeWeights() {
		for (int i = 0; i < weights.length; i++) {
			weights[i] = random.nextDouble();
			if (random.nextDouble() > .5) weights[i] *= -1;
		}

		biasWeight = random.nextDouble();
		if (random.nextDouble() > .5) biasWeight *= -1;
	}

	private void getInputs() {
		this.inputs = Network.getInputValues();
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

		total += Network.getBias() * biasWeight;

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
