package neural;

import java.util.Arrays;
import java.util.Scanner;

import neural.NeuralNetwork;
import neural.nodes.HiddenNode;
import neural.nodes.InputNode;
import neural.nodes.OutputNode;

public class Network implements Runnable {

	private static InputNode[] inputNodes;
	private static HiddenNode[] hiddenNodes;
	private static OutputNode[] outputNodes;

	private static double bias = 1;

	private double[][] inputs;
	private double[][] desiredOutputs;

	private int dataSet;

	private long runCount = 0;
	private int epoch = 0;
	private double lastTotalError = 0;

	public Network() {
		inputNodes = new InputNode[NeuralNetwork.inputNodes];
		hiddenNodes = new HiddenNode[NeuralNetwork.hiddenNodes];
		outputNodes = new OutputNode[NeuralNetwork.outputNodes];

		fillNodeArrays();
		dataSet = 0;
	}

	public void trainNetwork(double[][] inputs, double[][] desiredOutputs) {
		this.inputs = inputs;
		this.desiredOutputs = desiredOutputs;

		if (NeuralNetwork.TRAINING_EPOCHS > 0) {
			for (int i = 0; i < NeuralNetwork.TRAINING_EPOCHS; i++) {
				runTrainingSet();
				System.out.println("Epoch: " + epoch + " Reached Error: " + lastTotalError);
				epoch++;
			}
		} else {
			do {
				runTrainingSet();
				if (epoch % 100000 == 0) System.out.println("Epoch: " + epoch + " Reached Error: " + lastTotalError);
				epoch++;
			} while (lastTotalError > NeuralNetwork.DESIRED_ERROR);
			
			System.out.println("Epoch: " + epoch + " Reached Error: " + lastTotalError);
		}

		System.out.println("\nPlease enter some input data\n------------------------------------\n");
		Scanner s = new Scanner(System.in);

		while (true) {
			double[] testIn = new double[inputs[0].length];
			
			for (int i = 0; i < testIn.length; i++) 
				testIn[i] = s.nextDouble();
			
			System.out.println(output(testIn) + "\n");			
			}
	}

	private void runTrainingSet() {
		lastTotalError = 0;

		/*for (int j = 0; j < inputs.length; j++) {
			setInputs(inputs[j]);
			double out = propagate();

			System.out.println(out);
			
			if (lastTotalError < out)
				lastTotalError = out;
			// lastTotalError += out;
			// System.out.println("Last Total Error For Epoch: " + epoch + "
			// Data Set #" + dataSet + ": " + lastTotalError);
			backpropagate();*/
		
		
			for (int j = 0; j < inputs.length; j++) {
				setInputs(inputs[j]);
				dataSet = j;
				double out;
				
				//do {
					out = propagate();
					
					if (lastTotalError < out)
						lastTotalError = out;
					backpropagate();
				//} while(out > NeuralNetwork.DESIRED_ERROR);
			}
		}

	//}

	public void backpropagate() {
		double[][] newOutputWeights = new double[outputNodes.length][outputNodes[0].weights.length];
		double[][] newHiddenWeights = new double[hiddenNodes.length][hiddenNodes[0].weights.length];

		// Outputs

		for (int i = 0; i < newOutputWeights.length; i++)
			for (int j = 0; j < newOutputWeights[i].length; j++) {
				double w = 0;
				//w = -1 * NeuralNetwork.LEARNING_RATE * hiddenNodes[j].getValue()
				//		* (outputNodes[i].getValue() - desiredOutputs[dataSet][i]) * outputNodes[i].getValue()
				//		* (1 - outputNodes[i].getValue());
				
				w = NeuralNetwork.LEARNING_RATE * (-(desiredOutputs[dataSet][i] - outputNodes[i].getValue()) * (outputNodes[i].getValue() * (1 - outputNodes[i].getValue()) * hiddenNodes[j].getValue()));
				
				
				
				
				newOutputWeights[i][j] = w;
			}

		// Hidden

		for (int i = 0; i < newHiddenWeights.length; i++) {
			double sumation = 0;

			for (int x = 0; x < outputNodes.length; x++)
				sumation += -(desiredOutputs[dataSet][i] - outputNodes[i].getValue()) * (outputNodes[i].getValue() * outputNodes[x].weights[i]);
			
				sumation *= hiddenNodes[i].getValue() * (1 - hiddenNodes[i].getValue());
				
			for (int j = 0; j < newHiddenWeights[i].length; j++) {
				/*double w = 0;

				double sumation = 0;

				for (int x = 0; x < outputNodes.length; x++)
					sumation += outputNodes[x].weights[i]
							* (((newOutputWeights[x][i] / -1) / NeuralNetwork.LEARNING_RATE)
									/ hiddenNodes[i].getValue());

				w = -1 * NeuralNetwork.LEARNING_RATE * inputNodes[j].getInput() * sumation * hiddenNodes[i].getValue()
						* (1 - hiddenNodes[i].getValue());
				*/
				
				double w = NeuralNetwork.LEARNING_RATE * sumation * inputNodes[j].getInput();
				
				newHiddenWeights[i][j] = w;
			}
		}

		for (int i = 0; i < newOutputWeights.length; i++)
			for (int j = 0; j < newOutputWeights[0].length; j++)
				outputNodes[i].weights[j] -= newOutputWeights[i][j];

		for (int i = 0; i < newHiddenWeights.length; i++)
			for (int j = 0; j < newHiddenWeights[0].length; j++)
				hiddenNodes[i].weights[j] -= newHiddenWeights[i][j];

		/*
		 * Old // Output Nodes for (int i = 0; i < outputNodes.length; i++) {
		 * 
		 * OutputNode node = outputNodes[i];
		 * 
		 * for (int j = 0; j < node.weights.length; j++) {
		 * 
		 * double deltaOfNode = -1 * (desiredOutputs[dataSet][i] -
		 * node.getValue()) * node.getValue() * (-1 * node.getValue()) *
		 * node.inputs[j]; newOutputWeights[i][j] = node.weights[j] -
		 * NeuralNetwork.LEARNING_RATE * deltaOfNode;
		 * 
		 * } }
		 * 
		 * // InputNodes for (int i = 0; i < hiddenNodes.length; i++) {
		 * 
		 * HiddenNode node = hiddenNodes[i];
		 * 
		 * for (int j = 0; j < node.weights.length; j++) {
		 * 
		 * double deltaOfHiddenNode = 0;
		 * 
		 * for (int x = 0; x < NeuralNetwork.outputNodes; x++) {
		 * deltaOfHiddenNode += (-1 * (desiredOutputs[dataSet][x] -
		 * outputNodes[x].getValue())) * (outputNodes[x].getValue() * (1 -
		 * outputNodes[x].getValue())) * node.inputs[j]; }
		 * 
		 * double partDirOfTotErOverPartDirOfWeight = deltaOfHiddenNode *
		 * (node.getValue() * (1 - node.getValue())) * node.inputs[j];
		 * newHiddenWeights[i][j] = node.weights[j] -
		 * NeuralNetwork.LEARNING_RATE * partDirOfTotErOverPartDirOfWeight; }
		 * 
		 * }
		 * 
		 * for (int i = 0; i < newOutputWeights.length; i++) for (int j = 0; j <
		 * newOutputWeights[0].length; j++) outputNodes[i].weights[j] =
		 * newOutputWeights[i][j];
		 * 
		 * for (int i = 0; i < newHiddenWeights.length; i++) for (int j = 0; j <
		 * newHiddenWeights[0].length; j++) hiddenNodes[i].weights[j] =
		 * newHiddenWeights[i][j];
		 */

		/*
		 * // Code (update all at once at the end
		 * 
		 * // output nodes first deltaOfNode = -1 * (targetOutput -
		 * actualOutput) * actualOutput * (1 - actualOutput) *
		 * outputOfInputNode;
		 * 
		 * newWeight = oldWeight - learningRate * deltaOfNode;
		 * 
		 * // hidden nodes deltaOfNodeOutputGoesTo1 = (-1 * (targetOutput -
		 * actualOutput)) * (actualOutput * (1 - actualOutput)) *
		 * weightToOutputNode; deltaOfHiddenNode = deltaOfNodeOutputGoesTo1 +
		 * deltaOfNodeOutputGoesTo2 + ""3 ect;
		 * 
		 * partDirOfTotErOverPartDirOfWeight = deltaOFHiddenNode *
		 * (outputOfHiddenNode * (1 - outputOfHiddenNode)) *
		 * (inputFromPreviousNode);
		 * 
		 * newWeight = oldWeight - learningRate *
		 * partDirOfTotErOberPartDirOfWeight;
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
		double[] inputs = new double[NeuralNetwork.inputNodes];

		for (int i = 0; i < inputNodes.length; i++)
			inputs[i] = inputNodes[i].getInput();

		return inputs;
	}

	public static double[] getHiddenInputs() {
		double[] inputs = new double[NeuralNetwork.hiddenNodes];

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
		double[] outputs = new double[NeuralNetwork.outputNodes];

		for (int i = 0; i < outputNodes.length; i++) {
			outputNodes[i].calcValue();
			outputs[i] = outputNodes[i].getValue();
		}

		return outputs;
	}

	private double output(double inputs[]) {
		setInputs(inputs);
		double[] outputs = getOutputs();
		double sum = 0;

		for (double d : outputs)
			sum += d;

		return sum / outputs.length;
	}

	private double propagate() {
		double[] outputs = getOutputs();
		double[] error = new double[outputs.length];
		double totalError = 0;

		for (int i = 0; i < outputs.length; i++) {
			// error[i] = Math.abs(outputs[i] - desiredOutputs[dataSet][i]);
			error[i] = (.5 * Math.pow((desiredOutputs[dataSet][i] - outputs[i]), 2)); // Squared
																					// Error
																					// Function
			
			//System.out.println(Arrays.toString(outputs) + Arrays.toString(error) + desiredOutputs[dataSet][i]);
		}

		for (int i = 0; i < error.length; i++)
			totalError += error[i];

		return totalError;
	}

	public static double getBias() {
		return bias;
	}

}
