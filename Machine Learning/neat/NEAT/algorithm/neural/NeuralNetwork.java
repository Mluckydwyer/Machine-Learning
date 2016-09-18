package NEAT.algorithm.neural;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import NEAT.algorithm.Scholar;

public class NeuralNetwork {

	public ArrayList<Node>			nodes;
	public ArrayList<Connection>	connections;
	private static int						nodeIDCounter;

	public NeuralNetwork() {
		nodes = new ArrayList<Node>();
		connections = new ArrayList<Connection>();
		nodeIDCounter = 0;
	}

	protected int getNextNodeID() {
		return nodeIDCounter++;
	}

	public double[] evaluate(double[] inputs) {
		double[] outputs = new double [Scholar.obj.outputNodeCount];
		int inputStart = findStartOfNodeType(Node.NODE_TYPE_INPUT);
		int outputStart = findStartOfNodeType(Node.NODE_TYPE_OUTPUT);

		// Set Input Values
		for (int i = inputStart; i < inputStart + inputs.length - 1; i++) {
			nodes.get(i).setValue(inputs[i - inputStart]);
		}

		// Calculate Values
		for (Node n : nodes) {
			double sum = 0;

			for (int i = 0; i < n.incoming.size(); i++) {
				Connection incoming = n.incoming.get(i);
				Node other = getNodeByID(incoming.getInNode());

				sum += incoming.getWeight() * other.getValue();
			}

			if (n.incoming.size() > 0) n.setValue(sigmoid(sum));
		}

		// Get Output Values
		for (int i = outputStart; i < outputStart + outputs.length - 1; i++) {
			outputs[i - outputStart] = nodes.get(i).getValue();
		}

		return outputs;
	}

	private double sigmoid(double num) {
		return 2 / (1 + Math.exp(-4.9 * num)) - 1;
	}

	private int findStartOfNodeType(int type) {
		for (int i = 0; i < nodes.size(); i++)
			if (nodes.get(i).getNodeType() == type) return i;
		return -1;
	}

	public void addPuts() {
		for (int i = 0; i < Scholar.obj.inputNodeCount; i++)
			addNode(new Node(Node.NODE_TYPE_INPUT, getNextNodeID()));
		for (int i = 0; i < Scholar.obj.outputNodeCount; i++)
			addNode(new Node(Node.NODE_TYPE_OUTPUT, getNextNodeID()));
		sort();
	}

	public void addNode(Node n) {
		nodes.add(n);
		sortNodes();
	}

	public void addConnection(Connection c) {
		connections.add(c);
		sortConnections();
	}

	public void sort() {
		sortNodes();
		sortConnections();
	}

	private void sortNodes() {
		Collections.sort(nodes, new Comparator<Node>() {

			@Override
			public int compare(Node n1, Node n2) {
				if (n1.getNodeID() < n2.getNodeID()) return -1;
				else if (n1.getNodeID() > n2.getNodeID()) return 1;
				else return 0;
			}
		});
	}

	private void sortConnections() {
		Collections.sort(connections, new Comparator<Connection>() {

			@Override
			public int compare(Connection c1, Connection c2) {
				if (c1.getInnovationNum() < c2.getInnovationNum()) return -1;
				else if (c1.getInnovationNum() > c2.getInnovationNum()) return 1;
				else return 0;
			}
		});
	}

	public boolean hasNode(Node n) {
		for (Node node : nodes)
			if (node.getNodeID() == n.getNodeID()) return true;

		return false;
	}

	public boolean hasConnection(Connection c) {
		for (Connection conn : connections)
			if (conn.getInnovationNum() == c.getInnovationNum()) return true;

		return false;
	}

	public Node getNodeByID(int ID) {
		for (Node n : nodes)
			if (n.getNodeID() == ID) return n;

		return null;
	}

	public Connection getConnectionByInnNum(int innNum) {
		for (Connection c : connections)
			if (c.getInnovationNum() == innNum) return c;

		return null;
	}

	@Override
	public String toString() {
		return "NeuralNetwork [Nodes = " + Arrays.toString(nodes.toArray()) + "]\n[Connections = " + Arrays.toString(connections.toArray()) + "]";
	}

}
