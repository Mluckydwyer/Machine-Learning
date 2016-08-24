package neural;

import neural.nodes.HiddenNode;
import neural.nodes.InputNode;
import neural.nodes.OutputNode;

public class Network implements Runnable {

	private static InputNode[]	inputNodes;
	private static HiddenNode[]	hiddenNodes;
	private static OutputNode[]	outputNodes;

	private double				bias		= 1;

	private double[][]			inputs;
	private double[][]			desiredOutputs;

	private int					dataSet;
	private final int			runTimes	= 60000;	// 60,000; -1 for
														// infinite until target
														// error is reached TODO
	private final double		targetError	= .0005;
	private long				runCount	= 0;
	private double				lastError	= 0;

	public Network() {
		inputNodes = new InputNode [NuralNetwork.inputNodes];
		hiddenNodes = new HiddenNode [NuralNetwork.hiddenNodes];
		outputNodes = new OutputNode [NuralNetwork.outputNodes];

		fillNodeArrays();
		dataSet = 0;
	}

	public void trainNetwork(double[][] inputs, double[][] desiredOutputs) {
		this.inputs = inputs;
		this.desiredOutputs = desiredOutputs;

		/*for (int i = 0; i < inputs.length; i++) {
			setInputs();

			for (int j = 0; j < runTimes; j++)
				runSet();

			dataSet++;
		}*/
		
		for (runCount = 0; runCount < runTimes || targetError < lastError; runCount++) {
			setInputs();
			runSet();
			
			dataSet++;
			
			if (dataSet == inputs.length) dataSet = 0;
		}
	}

	private void runSet() {
		lastError = findError();
		backpropigation(lastError);
	}

	public void backpropigation(double error) {
		for (int i = 0; i < hiddenNodes.length; i++)
			hiddenNodes[i].backpropigation(error);
	}

	private void fillNodeArrays() {
		// Inputs
		for (InputNode in : inputNodes)
			in = new InputNode();

		// Hidden
		for (HiddenNode hid : hiddenNodes)
			hid = new HiddenNode();

		// Outputs
		for (OutputNode out : outputNodes)
			out = new OutputNode();
	}

	private void setInputs() {
		for (int i = 0; i < inputs.length; i++)
			inputNodes[i].setInput(inputs[dataSet][i]);
	}

	public double[] getInputValues() {
		double[] inputs = new double [NuralNetwork.inputNodes];

		for (int i = 0; i < inputNodes.length; i++)
			inputs[i] = inputNodes[i].getInput();

		return inputs;
	}

	public double[] getHiddenInputs() {
		double[] inputs = new double [NuralNetwork.hiddenNodes];

		for (int i = 0; i < hiddenNodes.length; i++) {
			hiddenNodes[i].calcValue();
			inputs[i] = hiddenNodes[i].getValue();
		}

		return inputs;
	}

	@Override
	public void run() {

	}

	private double[] getOutputs() {
		double[] outputs = new double [NuralNetwork.outputNodes];

		for (int i = 0; i < outputNodes.length; i++) {
			outputNodes[i].calcValue();
			outputs[i] = outputNodes[i].getValue();
		}

		return outputs;
	}

	private double findError() {
		double[] outputs = getOutputs();
		double[] error = new double [outputs.length];
		double totalError = 0;

		for (int i = 0; i < outputs.length; i++) {
			// error[i] = desiredOutputs[dataSet][i] - outputs[i];
			error[i] = (.5 * Math.pow(desiredOutputs[dataSet][i] - outputs[i], 2)); // Squared
																					// Error
																					// Function
		}

		for (int i = 0; i < error.length; i++)
			totalError += error[i];

		return totalError;
	}

	public double getBias() {
		return bias;
	}

}
