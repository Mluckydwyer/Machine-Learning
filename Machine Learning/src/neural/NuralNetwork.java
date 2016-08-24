package neural;

public class NuralNetwork {

	// Data
	private static final double[][]	inputData			= {{0, 0, 1}, {0, 1, 1}, {1, 0, 1}, {1, 1, 1}};
	private static final double[][]	desiredOutputData	= {{0}, {1}, {1}, {0}};

	// Node Count
	public static final int			outputNodes			= desiredOutputData[0].length;
	public static final int			inputNodes			= inputData[0].length;
	public static final int			hiddenNodes			= Math.round((outputNodes + inputNodes) / 2);

	private static Network			nw;

	public static void main(String[] args) {
		nw = new Network();

		nw.trainNetwork(inputData, desiredOutputData);
	}
}
