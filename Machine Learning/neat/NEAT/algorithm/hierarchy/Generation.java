package NEAT.algorithm.hierarchy;

import java.util.ArrayList;

import NEAT.algorithm.Scholar;
import NEAT.algorithm.neural.Node;

public class Generation {

	public ArrayList<Species>	species;
	private boolean				isFirstGen	= false;

	public Generation() {
		species = new ArrayList<Species>();
	}

	private void addPuts(Genome g) {
		for (int i = 0; i < Scholar.obj.inputNodeCount; i++)
			g.network.addNode(new Node(Node.NODE_TYPE_INPUT, -1));
		for (int i = 0; i < Scholar.obj.outputNodeCount; i++)
			g.network.addNode(new Node(Node.NODE_TYPE_OUTPUT, -2));
	}

	private Genome crossover(Genome g1, Genome g2) {
		Genome child = new Genome();
		g1.network.sort();
		g2.network.sort();

		for (int i = 0; i < g1.network.nodes.size(); i++) {
			if (!g2.network.hasNode(g1.network.nodes.get(i))) child.network.addNode(g1.network.nodes.get(i));
			else {
				if (g1.network.fitness > g2.network.fitness) child.network.addNode(g1.network.nodes.get(i));
				else if (g1.network.fitness < g2.network.fitness) child.network.addNode(g2.network.getNodeByID(g1.network.nodes.get(i).getNodeID()));
				else {
					if (Math.random() < 0.5) child.network.addNode(g1.network.nodes.get(i));
					else child.network.addNode(g2.network.getNodeByID(g1.network.nodes.get(i).getNodeID()));
				}

			}
		}

		for (int i = 0; i < g2.network.nodes.size(); i++)
			if (!g1.network.hasNode(g2.network.nodes.get(i))) child.network.addNode(g2.network.nodes.get(i));

		for (int i = 0; i < g1.network.connections.size(); i++) {
			if (!g2.network.hasConnection(g1.network.connections.get(i))) child.network.addConnection(g1.network.connections.get(i));
			else {
				if (g1.network.fitness > g2.network.fitness) child.network.addConnection(g1.network.connections.get(i));
				else if (g1.network.fitness < g2.network.fitness) child.network.addConnection(g2.network.getConnectionByInnNum(g1.network.connections.get(i).getInnovationNum()));
				else {
					if (Math.random() < 0.5) child.network.addConnection(g1.network.connections.get(i));
					else child.network.addConnection(g2.network.getConnectionByInnNum(g1.network.connections.get(i).getInnovationNum()));
				}

			}
		}

		for (int i = 0; i < g2.network.connections.size(); i++)
			if (!g1.network.hasConnection(g2.network.connections.get(i))) child.network.addConnection(g2.network.connections.get(i));

		return child;
	}

	private ArrayList<Genome> mutateGenomes(ArrayList<Genome> genomes) {

		for (Genome g : genomes)
			g.mutate();

		return genomes;
	}

	private Genome mutateGenome(Genome g) {
		g.mutate();
		return g;
	}
	
	private void cullAllSpecies() {
		
	}

	public void runFitnessTests(ArrayList<Genome> genomes) {
		if (Scholar.obj.simultainiousTests) {
			// TODO
		}
		else {
			for (Genome genome : genomes)
				genome.fitness = Scholar.obj.calculateFitness(genome.network);
		}
	}

	public ArrayList<Species> speciateGenomes(ArrayList<Genome> genomes) {
		ArrayList<Species> species = new ArrayList<Species>();

		return species;
	}

	public Generation genNextGen() {
		Generation nextGen = new Generation();
		ArrayList<Genome> genomes = new ArrayList<Genome>();

		if (isFirstGen) {
			for (int i = 0; i < Scholar.GENOME_COUNT_IN_POOL; i++)
				genomes.add(new Genome());
			genomes = mutateGenomes(genomes);
		}
		else {
			cullAllSpecies(); // TODO Cull old species (Remove x worst genomes in each)
			// TODO Crossover Genomes
			genomes = mutateGenomes(genomes);
		}

		runFitnessTests(genomes);
		nextGen.species = speciateGenomes(genomes); // TODO sort genomes into species based on a similarity formula within a certain threshold

		return nextGen;
	}

	public void setFirstGen(boolean isFirstGen) {
		this.isFirstGen = isFirstGen;
	}

}
