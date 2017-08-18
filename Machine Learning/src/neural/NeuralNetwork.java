package neural;

import java.util.Random;

public class NeuralNetwork {

	// Data
	//private static final double[][] inputData = { { 0, 1 }, { 1, 1 }, { 1, 0 }, { 0, 0 } };
	//private static final double[][] desiredOutputData = { { .01 }, { .01 }, { .01 }, { .99 } };
	private static final double[][] inputData = { { 0, 1 }, { 1, 0 } };
	private static final double[][] desiredOutputData = { { .99 }, { .01 } };
	//private static final double[][] inputData = { { 0, 1 } };
	//private static final double[][] desiredOutputData = { { .01 }  };
	public static final double LEARNING_RATE = 0.005;
	public static final int LEARNING_RATE_TYPE = 3; // 1 = Stationary Gradiant decent, 2 = Stationary Mean Squares, 3 = Variable
	public static final double DESIRED_ERROR = 0.01;
	public static final int TRAINING_EPOCHS = 10000000;
	public static int RANDOM_SEED = 12345;
	public static boolean isRanomSeed = true;

	// Node Count
	public static final int outputNodes = desiredOutputData[0].length;
	public static final int inputNodes = inputData[0].length;
	public static final int hiddenNodes = Math.round((outputNodes + inputNodes) / 2);

	private static Network nw;

	public static void main(String[] args) {
		if (isRanomSeed) RANDOM_SEED = new Random().nextInt(Integer.MAX_VALUE);
		
		nw = new Network();
		nw.trainNetwork(inputData, desiredOutputData);
	}
}
