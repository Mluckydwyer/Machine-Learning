package neural;

public class NeuralNetwork {

	// Data
	//private static final double[][] inputData = { { 0, 1 }, { 1, 1 }, { 1, 0 }, { 0, 0 } };
	//private static final double[][] desiredOutputData = { { .01 }, { .01 }, { .01 }, { .99 } };
	private static final double[][] inputData = { { 0, 1 }, { 0, 0 } };
	private static final double[][] desiredOutputData = { { .01 }, { .99 } };
	//private static final double[][] inputData = { { 0, 1 } };
	//private static final double[][] desiredOutputData = { { .01 }  };
	public static final double LEARNING_RATE = 5;
	public static final double DESIRED_ERROR = 0.05;
	public static final int TRAINING_EPOCHS = 0;

	// Node Count
	public static final int outputNodes = desiredOutputData[0].length;
	public static final int inputNodes = inputData[0].length;
	public static final int hiddenNodes = Math.round((outputNodes + inputNodes) / 2);

	// private static Network nw;
	private static Network nw;

	public static void main(String[] args) {
		// nw = new Network();
		nw = new Network();
		nw.trainNetwork(inputData, desiredOutputData);
	}
}
