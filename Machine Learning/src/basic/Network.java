package basic;

import java.util.Arrays;

public class Network {

	public static double[][] data = { { 0.0, 0.0, 0.0 }, { 1.0, 1.0, 1.0 }, { 1.0, 0.0, 1.0 }, { 0.0, 1.0, 0.0 }, { 0.0, 0.0, 1.0 },
			{ 1.0, 1.0, 0.0 }, { 0.0, 1.0, 1.0 } };
	public static double[][] summation;
	public static double[][] weightsHidden; // [hidden node][input node]
	public static double[] weightsHiddenBias;
	public static double[] hidden; // [hidden node]
	public static double[][] weightsOutput; // [output node][hidden node]
	public static double[] weightsOutputBias;
	public static double[] output; // [output node]
	public static double[][] expected = { { 0.0 }, { 0.0 }, { 1.0 }, { 1.0 }, { 1.0 }, { 1.0 }, { 1.0 } };
	public static int inputNodes;
	public static int hiddenNodes = 3;
	public static int outputNodes;
	public static double biasValue = 1;
	public static double learningRate = 0.05;

	public static void main(String[] args) {
		inputNodes = data[0].length;
		outputNodes = expected[0].length;

		summation = new double[hiddenNodes][];
		weightsHidden = Matrix.random(hiddenNodes, inputNodes);
		weightsHiddenBias = Matrix.random(1, hiddenNodes)[0];
		hidden = new double[hiddenNodes];

		weightsOutput = Matrix.random(outputNodes, hiddenNodes);
		weightsOutputBias = Matrix.random(1, outputNodes)[0];
		output = new double[outputNodes];

		while (true) {
			for (int i = 0; i < data.length; i++) {
				double[] error = propagate(i);
				System.out.println("Error: " + Arrays.toString(error));
				backpropagate(i, error);
			}
		}
	}

	public static double[] propagate(int dataIndex) {
		hidden = sigmoid(Matrix.add(Matrix.multiply(data[dataIndex], weightsHidden), Matrix.multiply(weightsHiddenBias, biasValue)));
		output = sigmoid(Matrix.add(Matrix.multiply(hidden, weightsOutput), Matrix.multiply(weightsOutputBias, biasValue)));
		return error(output, expected[dataIndex]);
	}

	public static void backpropagate(int dataIndex, double[] error) {
		weightsOutput = Matrix.multiply(Matrix.multiply1(Matrix.multiply(Matrix.multiply(weightsOutput, learningRate), -1D), Matrix.subtract(expected[dataIndex], output)), Matrix.subtract(1D, Matrix.multiply2(output, hidden)));
		summation = Matrix.multiply(Matrix.multiply1(Matrix.multiply(Matrix.multiply(weightsOutput, learningRate), -1D), Matrix.subtract(expected[dataIndex], output)), weightsOutput); //up to 
		weightsHidden = Matrix.multiply(Matrix.multiply1(Matrix.multiply(Matrix.multiply(weightsHidden, learningRate), data[dataIndex]), summation));

	public static double[] sigmoid(double[] nums) {
		double[] sigs = new double[nums.length];
		for (int i = 0; i < sigs.length; i++)
			sigs[i] = (2D / (1D + Math.exp(-4.9D * nums[i])) - 1D);
			// 1D / (1D + Math.pow(Math.E, -sum));
		return sigs;
	}

	public static double[] error(double[] values, double[] expected) {
		double[] errors = new double[values.length];
		for (int i = 0; i < errors.length; i++)
			errors[i] = 0.5D * Math.pow((expected[i] - values[i]), 2D);
		
		return errors;
	}
}
