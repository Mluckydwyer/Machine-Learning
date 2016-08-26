package NEAT.algorithm.neural;

import NEAT.algorithm.hierarchy.Genome;
import NEAT.task.Objective;

public class Node extends Genome {

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
		value = 0;
	}
	
	public static double randomWieght() {
		return Math.random() * 4 - 2;
	}
	
	private double sigmoid(double num) {
		return num;
	}
	
	public int getNodeID() {
		return nodeID;
	}
	
	public int getNodeType() {
		return nodeType;
	}
}
