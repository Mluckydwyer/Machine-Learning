package NEAT.algorithm.hierarchy;

import java.util.ArrayList;
import java.util.Random;

import NEAT.algorithm.Scholar;
import NEAT.algorithm.neural.Connection;
import NEAT.algorithm.neural.NeuralNetwork;
import NEAT.algorithm.neural.Node;

public class Genome {

	public NeuralNetwork	network;
	public int				fitness;
	public int				globalRank;
	public int				genomeNum;

	/*
	 * Randomly updates weight of a selected connection. newWeight = oldWeight +/- Rand(0 to genome$MutationRate[[“Step”]]);? or newWeight = rand(-2 to 2);
	 */
	private double			perturbWeightMutatePercent;

	/*
	 * Adds new random connection (weight 0 to 2)
	 */
	private double			linkMutatePercent;

	/*
	 * Adds new random connection (weight 0 to 2)
	 * also inNode must be an input Node
	 */
	private double			forceInputBiasLinkMutatePercent;
	
	/*
	 * Splits connection to add a new node
	 */
	private double			nodeMutatePercent;

	/*
	 * Enables a random connection
	 */
	private double			enableMutatePercent;

	/*
	 * Disables a random connection
	 */
	private double			disableMutatePercent;
	
	private double			stepSize;

	public Genome(boolean basicGenome) {
		network = new NeuralNetwork();
		perturbWeightMutatePercent = Scholar.PERTURB_WEIGHT_CHANCE;
		linkMutatePercent = Scholar.LINK_MUTATE_CHANCE;
		nodeMutatePercent = Scholar.NODE_MUTATION_CHANCE;
		enableMutatePercent = Scholar.ENABLE_MUTAION_CHANCE;
		disableMutatePercent = Scholar.DISABLE_MUTAION_CHANCE;
		forceInputBiasLinkMutatePercent = Scholar.FORCE_INPUT_BIAS_LINK_MUTATE_CHANCE;
		stepSize = Scholar.STEP_SIZE;
		
		if (basicGenome) {
			network.addPuts();
			mutate();
		}
	}
	
	public void mutate() {
		perturbMutationChances();

		// AKA Point Mutate
		double p = perturbWeightMutatePercent;
		while (p > 0) {
			if (Math.random() < p) perturbWeightMutate();
			p -= 1;
		}

		p = linkMutatePercent;
		while (p > 0) {
			if (Math.random() < p) linkMutate(false);
			p -= 1;
		}

		p = forceInputBiasLinkMutatePercent;
		while (p > 0) {
			if (Math.random() < p) linkMutate(true);
			p -= 1;
		}
		
		p = nodeMutatePercent;
		while (p > 0) {
			if (Math.random() < p) nodeMutate();
			p -= 1;
		}

		p = enableMutatePercent;
		while (p > 0) {
			if (Math.random() < p) enableMutate();
			p -= 1;
		}

		p = disableMutatePercent;
		while (p > 0) {
			if (Math.random() < p) disableMutate();
			p -= 1;
		}
	}

	private void perturbMutationChances() {
		Random r = new Random();

		perturbWeightMutatePercent = perturb(perturbWeightMutatePercent, r.nextBoolean());
		linkMutatePercent = perturb(linkMutatePercent, r.nextBoolean());
		forceInputBiasLinkMutatePercent = perturb(forceInputBiasLinkMutatePercent, r.nextBoolean());
		nodeMutatePercent = perturb(nodeMutatePercent, r.nextBoolean());
		enableMutatePercent = perturb(enableMutatePercent, r.nextBoolean());
		disableMutatePercent = perturb(disableMutatePercent, r.nextBoolean());
	}

	private double perturb(double chance, boolean decrece) {
		if (decrece) return chance * 0.95;
		else return chance * 1.05263; // 1.05??????
	}

	// AKA Point Mutate
	private void perturbWeightMutate() {

		for (Connection c : network.connections) {
			if (Math.random() < perturbWeightMutatePercent) c.setWeight(c.getWeight() + Math.random() * stepSize * 2 - stepSize);
			else c.setWeight(Math.random() * 4 - 2);
		}
	
	}

	private void linkMutate(boolean forceBias) {
		ArrayList<Integer> possNodes = new ArrayList<Integer>();
		
		if (!forceBias) {
			for (int i = 0; i < network.nodes.size(); i++)
				if (network.nodes.get(i).getNodeType() != Node.NODE_TYPE_OUTPUT) possNodes.add(i);
		}
		else {
			for (int i = 0; i < network.nodes.size(); i++)
				if (network.nodes.get(i).getNodeType() == Node.NODE_TYPE_INPUT) possNodes.add(i);
		}

		int inNode = possNodes.get(new Random().nextInt(possNodes.size()));

		
		possNodes.clear();

		for (int i = 0; i < network.nodes.size(); i++)
			if (network.nodes.get(i).getNodeType() != Node.NODE_TYPE_BIAS && network.nodes.get(i).getNodeType() != Node.NODE_TYPE_INPUT) possNodes.add(i); // TODO MAYBE?
		int outNode = possNodes.get(new Random().nextInt(possNodes.size()));

		network.connections.add(new Connection(inNode, outNode, Scholar.getNextInnovationNum(), Node.randomWieght(), true));
	}

	private void nodeMutate() {
		if (!network.connections.isEmpty()) {
			Node n = new Node(Node.NODE_TYPE_HIDDEN, Scholar.getNextNodeID());
			Connection old = network.connections.get(new Random().nextInt(network.connections.size()));
			Connection new1 = new Connection(old.getInNode(), n.getNodeID(), Scholar.getNextInnovationNum(), 1, true);
			Connection new2 = new Connection(n.getNodeID(), old.getOutNode(), Scholar.getNextInnovationNum(), old.getWeight(), true);

			old.setEnabled(false);

			network.addNode(n);
			network.addConnection(new1);
			network.addConnection(new2);
		}
	}

	private void enableMutate() {
		ArrayList<Integer> enabled = new ArrayList<Integer>();

		for (int i = 0; i < network.connections.size(); i++)
			if (network.connections.get(i).isEnabled()) enabled.add(i);

		if (!enabled.isEmpty()) {
			int toDisable = enabled.get(new Random().nextInt(enabled.size()));
			network.connections.get(toDisable).setEnabled(false);
		}
	}

	private void disableMutate() {
		ArrayList<Integer> disabled = new ArrayList<Integer>();

		for (int i = 0; i < network.connections.size(); i++)
			if (!network.connections.get(i).isEnabled()) disabled.add(i);

		if (!disabled.isEmpty()) {
			int toEnable = disabled.get(new Random().nextInt(disabled.size()));
			network.connections.get(toEnable).setEnabled(true);
		}
	}
	
	public void setGlobalRank(int rank) {
		globalRank = rank;
	}

	public void resetNodes() {
		for (int i = 0; i < network.nodes.size(); i ++) {
			Node n = network.nodes.get(i);
			n.incoming.clear();
			n.setValue(0);
		}
	}
	
}
