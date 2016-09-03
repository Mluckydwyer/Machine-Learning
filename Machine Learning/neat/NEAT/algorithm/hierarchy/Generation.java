package NEAT.algorithm.hierarchy;

import java.util.ArrayList;
import java.util.Random;

import NEAT.algorithm.Scholar;
import NEAT.algorithm.neural.Connection;
import NEAT.algorithm.neural.Node;

public class Generation {

	public ArrayList<Species>	species;
	public ArrayList<Species>	staleSpecies;
	public ArrayList<Species>	weakSpecies;

	public int					maxFitness;
	public int					averageFitness;

	private boolean				isFirstGen	= false;

	public Generation() {
		species = new ArrayList<Species>();
		staleSpecies = new ArrayList<Species>();
		weakSpecies = new ArrayList<Species>();
		maxFitness = 0;
	}

	private void globalRank() {
		ArrayList<Genome> genomes = new ArrayList<Genome>();

		for (Species s : species)
			for (Genome g : s.genomes)
				genomes.add(g);

		for (int i = 0; i < genomes.size(); i++)
			genomes.get(i).setGlobalRank(i);
	}

	private void calculateAverageFitness() {
		int sum = 0;

		for (Species s : species) {
			sum += s.averageFitness;
		}

		averageFitness = sum;
	}

	protected void addPuts(Genome g) {
		for (int i = 0; i < Scholar.obj.inputNodeCount; i++)
			g.network.addNode(new Node(Node.NODE_TYPE_INPUT, -1));
		for (int i = 0; i < Scholar.obj.outputNodeCount; i++)
			g.network.addNode(new Node(Node.NODE_TYPE_OUTPUT, -2));
	}

	private ArrayList<Genome> breedChildren(ArrayList<Genome> children) {
		int numToBreed;

		for (Species s : species) {
			s.calculateAverageFitness();
			numToBreed = (int) (Math.floor(s.averageFitness / averageFitness * Scholar.GENOME_COUNT_IN_POOL) - 1);

			for (int i = 0; i < numToBreed; i++)
				children.add(breedChild(s));
		}

		return children;
	}

	private Genome breedChild(Species s) {
		Genome child;

		if (Math.random() < Scholar.CROSSOVER_CHANCE) {
			Genome g1 = s.genomes.get(new Random().nextInt(s.genomes.size()));
			Genome g2 = s.genomes.get(new Random().nextInt(s.genomes.size()));
			child = crossover(g1, g2);
		}
		else child = s.genomes.get(new Random().nextInt(s.genomes.size()));

		child.mutate();

		return child;
	}

	private ArrayList<Genome> fillChildrenToPop(ArrayList<Genome> children) {

		while (children.size() + species.size() < Scholar.GENOME_COUNT_IN_POOL)
			children.add(breedChild(species.get(new Random().nextInt(species.size()))));

		return children;
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

	private void cullSpeciesForNextGen() {

		for (Species s : species) {
			s.sortSpecies();

			for (int i = s.genomes.size() - 1; i > 0; i--) {
				s.genomes.remove(i);
			}

		}

	}

	private void cullAllSpecies() {
		for (Species s : species) {
			int remainingGenomes = (int) Math.ceil(s.genomes.size() * Scholar.REMAING_AFTER_CULL_PERCENT);
			s.sortSpecies();

			for (int i = s.genomes.size() - 1; i > remainingGenomes; i--) {
				s.cullGenome(i);
			}

		}

	}

	private void removeStaleSpecies() {
		for (int i = species.size() - 1; i >= 0; i--) {
			species.get(i).sortSpecies();

			if (species.get(i).genomes.get(0).fitness > species.get(i).topFitness) {
				species.get(i).topFitness = species.get(i).genomes.get(0).fitness;
				species.get(i).staleness = 0;
			}
			else species.get(i).staleness++;

			if (!(species.get(i).staleness < Scholar.STALE_SPECIES) && !(species.get(i).topFitness >= maxFitness)) {
				staleSpecies.add(species.get(i));
				species.remove(i);
			}
		}
	}

	private void removeWeakSpeceies() {
		calculateAverageFitness();

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

	public void speciateGenomes(ArrayList<Genome> genomes) {

		for (Genome c : genomes) {
			boolean foundSpecies = false;

			for (int i = 0; i < species.size(); i++)
				if (!foundSpecies && sameSpeceies(c, species.get(i).genomes.get(0))) species.get(i).genomes.add(c);

			if (!foundSpecies) {
				Species newSpecies = new Species();
				newSpecies.genomes.add(c);
				species.add(newSpecies);
			}
		}

	}

	private boolean sameSpeceies(Genome genome1, Genome genome2) {

		double dd = Scholar.DELTA_DISJOINT * disjoint(genome1.network.connections, genome2.network.connections); // DeltaDisjoint
		double dw = Scholar.DELTA_WEIGHTS * weights(genome1.network.connections, genome2.network.connections); // DeltaWeights
		
		return dd + dw < Scholar.DELTA_THRESHOLD;
	}

	private double disjoint(ArrayList<Connection> cons1, ArrayList<Connection> cons2) {

		//TODO
		
		
		return 0;
	}
	
	private double weights(ArrayList<Connection> cons1, ArrayList<Connection> cons2) {
		int numShared = 0;
		double sum = 0;
		
		for (int i = 0; i < cons1.size(); i++) {
			int indexNum = containsInovation(cons1.get(i), cons2);
			
			if (indexNum != -1) {
				numShared++;
				sum += Math.abs(cons1.get(i).getWeight() - cons2.get(indexNum).getWeight());
			}
		}
		
		return sum / numShared;
	}

	private int containsInovation(Connection c, ArrayList<Connection> cons) {
		int innovationNum = c.getInnovationNum();
		
		for (int i = 0; i < cons.size(); i++)
			if (cons.get(i).getInnovationNum() == innovationNum) return i;
		
		return -1;
	}
	
	public Generation genNextGen() {
		Generation nextGen = new Generation();
		ArrayList<Genome> children = new ArrayList<Genome>();

		if (isFirstGen) {
			for (int i = 0; i < Scholar.GENOME_COUNT_IN_POOL; i++)
				children.add(new Genome());
			children = mutateGenomes(children);
		}
		else {
			cullAllSpecies();
			globalRank();

			removeStaleSpecies(); // TODO Check over
			globalRank();

			removeWeakSpeceies(); // TODO Check over
			globalRank();

			calculateAverageFitness();

			children = breedChildren(children); // Check over and test formula for calc. num of children to gen

			cullSpeciesForNextGen(); // Cull all but top member of each species

			children = fillChildrenToPop(children); // while # of species and children are < pop. size; pick random species and breed children form them

			speciateGenomes(children); // TODO add children to species (speciate)
			// Finish Disjoint Calculation Method

			// Backup/save
			// genomes = mutateGenomes(genomes); change to when breed or created
		}

		runFitnessTests(children);
		nextGen.species = speciateGenomes(children); // TODO sort genomes into species based on a similarity formula within a certain threshold

		return nextGen;
	}

	public void setFirstGen(boolean isFirstGen) {
		this.isFirstGen = isFirstGen;
	}

}
