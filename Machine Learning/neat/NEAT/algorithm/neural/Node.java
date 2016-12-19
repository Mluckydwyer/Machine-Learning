package NEAT.algorithm.neural;

import java.util.ArrayList;

public class Node {

	public ArrayList<Connection> incoming;
	
	public static final int NODE_TYPE_INPUT = 1;
	public static final int NODE_TYPE_HIDDEN = 2;
	public static final int NODE_TYPE_OUTPUT = 3;
	public static final int NODE_TYPE_BIAS = 4;
	
	private int nodeType;
	private int nodeID;
	
	private double value;
	
	public Node(int nodeType, int ID) {
		this.nodeType = nodeType;
		this.nodeID = ID;
		incoming = new ArrayList<Connection>();
		setValue(0);
	}
	
	public static double randomWieght() {
		return Math.random() * 4 - 2;
	}
	
	public int getNodeID() {
		return nodeID;
	}
	
	public int getNodeType() {
		return nodeType;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Node [incoming=" + incoming + ", nodeType=" + nodeType + ", nodeID=" + nodeID + ", value=" + value + "]";
	}
}
