package neural.map;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import neural.NeuralNetwork;

public class Network {

	public static Map<String, Neuron> nodes;

	private double[][] inputs;
	private double[][] desiredOutputs;
	
	public Network() {
		nodes = new HashMap<String, Neuron>();
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
		}
		else {
			do {
				runTrainingSet();
				System.out.println("Epoch: " + epoch + " Reached Error: " + lastTotalError);
				epoch++;
			} while (lastTotalError > NeuralNetwork.DESIRED_ERROR);
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
}
