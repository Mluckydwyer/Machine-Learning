package neural;

import neural.NeuralNetwork;
import neural.nodes.HiddenNode;
import neural.nodes.InputNode;
import neural.nodes.OutputNode;

public class Network implements Runnable {

	private static InputNode[]	inputNodes;
	private static HiddenNode[]	hiddenNodes;
	private static OutputNode[]	outputNodes;

	private static double				bias			= 1;

	private double[][]			inputs;
	private double[][]			desiredOutputs;

	private int					dataSet;

	private long				runCount		= 0;
	private double				lastTotalError	= 0;

	public Network() {
		inputNodes = new InputNode [NeuralNetwork.inputNodes];
		hiddenNodes = new HiddenNode [NeuralNetwork.hiddenNodes];
		outputNodes = new OutputNode [NeuralNetwork.outputNodes];

		fillNodeArrays();
		dataSet = 0;
	}

	public void trainNetwork(double[][] inputs, double[][] desiredOutputs) {
		this.inputs = inputs;
		this.desiredOutputs = desiredOutputs;

		for (int i = 0; i < inputs.length; i++) {
			setInputs(inputs[i]);
			runTrainingSet();

			dataSet++;
			System.out.println("Data Set: " + dataSet + " Reached Error: " + lastTotalError + " in " + runCount + " runs!");

			runCount = 0;
		}
	}

	private void runTrainingSet() {
		do {
			lastTotalError = propagate();
			System.out.println("Last Total Error For Data Set #" + dataSet + ": " + lastTotalError);
			backpropagate(lastTotalError);
			runCount++;
		} while (lastTotalError >= NeuralNetwork.DESIRED_ERROR);		
	}

	public void backpropagate(double error) {
		double[][] newOutputWeights = new double [outputNodes.length] [outputNodes[0].weights.length];
		double[][] newHiddenWeights = new double [hiddenNodes.length] [hiddenNodes[0].weights.length];

		// Output Nodes
		for (int i = 0; i < outputNodes.length; i++) {

			OutputNode node = outputNodes[i];

			for (int j = 0; j < node.weights.length; j++) {

				double deltaOfNode = -1 * (desiredOutputs[dataSet][i] - node.getValue()) * node.getValue() * (-1 * node.getValue()) * node.inputs[j];
				newOutputWeights[i][j] = node.weights[j] - NeuralNetwork.LEARNING_RATE * deltaOfNode;

			}
		}

		// InputNodes
		for (int i = 0; i < hiddenNodes.length; i++) {

			HiddenNode node = hiddenNodes[i];

			for (int j = 0; j < node.weights.length; j++) {

				double deltaOfHiddenNode = 0;

				for (int x = 0; x < NeuralNetwork.outputNodes; x++) {
					deltaOfHiddenNode += (-1 * (desiredOutputs[dataSet][x] - outputNodes[x].getValue())) * (outputNodes[x].getValue() * (1 - outputNodes[x].getValue())) * node.inputs[j];
				}

				double partDirOfTotErOverPartDirOfWeight = deltaOfHiddenNode * (node.getValue() * (1 - node.getValue())) * node.inputs[j];
				newHiddenWeights[i][j] = node.weights[j] - NeuralNetwork.LEARNING_RATE * partDirOfTotErOverPartDirOfWeight;
			}

		}

		for (int i = 0; i < newOutputWeights.length; i++)
			for (int j = 0; j < newOutputWeights[0].length; j++)
				outputNodes[i].weights[j] = newOutputWeights[i][j];
		
		for (int i = 0; i < newHiddenWeights.length; i++)
			for (int j = 0; j < newHiddenWeights[0].length; j++)
				hiddenNodes[i].weights[j] = newHiddenWeights[i][j];
		
		/*
		 * // Code (update all at once at the end
		 * 
		 * // output nodes first
		 * deltaOfNode = -1 * (targetOutput - actualOutput) * actualOutput * (1 - actualOutput) * outputOfInputNode;
		 * 
		 * newWeight = oldWeight - learningRate * deltaOfNode;
		 * 
		 * // hidden nodes
		 * deltaOfNodeOutputGoesTo1 = (-1 * (targetOutput - actualOutput)) * (actualOutput * (1 - actualOutput)) * weightToOutputNode;
		 * deltaOfHiddenNode = deltaOfNodeOutputGoesTo1 + deltaOfNodeOutputGoesTo2 + ""3 ect;
		 * 
		 * partDirOfTotErOverPartDirOfWeight = deltaOFHiddenNode * (outputOfHiddenNode * (1 - outputOfHiddenNode)) * (inputFromPreviousNode);
		 * 
		 * newWeight = oldWeight - learningRate * partDirOfTotErOberPartDirOfWeight;
		 */
	}

	private void fillNodeArrays() {
		// Inputs
		for (int i = 0; i < inputNodes.length; i++)
			inputNodes[i] = new InputNode();

		// Hidden
		for (int i = 0; i < hiddenNodes.length; i++)
			hiddenNodes[i] = new HiddenNode();

		// Outputs
		for (int i = 0; i < outputNodes.length; i++)
			outputNodes[i] = new OutputNode();
	}

	private void setInputs(double[] puts) {
		for (int i = 0; i < puts.length; i++)
			Network.inputNodes[i].setInput(puts[i]);
	}

	public static double[] getInputValues() {
		double[] inputs = new double [NeuralNetwork.inputNodes];

		for (int i = 0; i < inputNodes.length; i++)
			inputs[i] = inputNodes[i].getInput();

		return inputs;
	}

	public static double[] getHiddenInputs() {
		double[] inputs = new double [NeuralNetwork.hiddenNodes];

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
		double[] outputs = new double [NeuralNetwork.outputNodes];

		for (int i = 0; i < outputNodes.length; i++) {
			outputNodes[i].calcValue();
			outputs[i] = outputNodes[i].getValue();
		}

		return outputs;
	}

	private double propagate() {
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

	public static double getBias() {
		return bias;
	}

}
