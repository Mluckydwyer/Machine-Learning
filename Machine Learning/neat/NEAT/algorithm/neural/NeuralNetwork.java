package NEAT.algorithm.neural;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import NEAT.algorithm.hierarchy.Genome;

public class NeuralNetwork extends Genome {

	public ArrayList<Node>			nodes;
	public ArrayList<Connection>	connections;

	public NeuralNetwork() {
		nodes = new ArrayList<Node>();
		connections = new ArrayList<Connection>();
		addPuts(this);
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

	public void sortNodes() {
		Collections.sort(nodes, new Comparator<Node>() {

			@Override
			public int compare(Node n1, Node n2) {
				if (n1.getNodeID() < n2.getNodeID()) return -1;
				else if (n1.getNodeID() > n2.getNodeID()) return 1;
				else return 0;
			}
		});
	}

	public void sortConnections() {
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
	
}
