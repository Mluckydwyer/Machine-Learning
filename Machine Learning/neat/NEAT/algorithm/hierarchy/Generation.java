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
			s.calculateAverageFitness();
			sum += s.averageFitness;
		}

		averageFitness = (sum / species.size());
	}

	private ArrayList<Genome> breedChildren(ArrayList<Genome> children) {
		int numToBreed;

		for (Species s : species) {
			s.calculateAverageFitness();
			numToBreed = (int) (Math.floor(s.averageFitness / averageFitness * Scholar.GENOME_POPULATION) - 1);

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

		while (children.size() + species.size() < Scholar.GENOME_POPULATION)
			children.add(breedChild(species.get(new Random().nextInt(species.size()))));

		return children;
	}

	// HAS BUG THAT CAUSES DOUBLE ADDING OF NODES CAUSING TWO WITH THE SAME CONNECTION TODO TODO TODO TODO
	
	private Genome crossover(Genome g1, Genome g2) {
		Genome child = new Genome(false);
		g1.network.sort();
		g2.network.sort();

		
		if (g1.fitness < g2.fitness) {
			Genome temp = g1;
			g1 = g2;
			g2 = temp;
		}
		
		for (int i = 0; i < g1.network.nodes.size(); i++) {
			Node n1 = g1.network.nodes.get(i);
			Node n2 = g2.network.getNodeByID(n1.getNodeID());
			
			if (!n2.equals(null) && Math.random() < 0.5)
				if (!child.network.hasNode(n1)) child.network.addNode(n2);
			else
				if (!child.network.hasNode(n1)) child.network.addNode(n1);
		}
		
		child.resetNodes();
		
		for (int i = 0; i < g1.network.connections.size(); i++) {
			Connection c1 = g1.network.connections.get(i);
			Connection c2 = g2.network.getConnectionByInnNum(c1.getInnovationNum());
			
			if (!c2.equals(null) && Math.random() < 0.5)
				child.network.addConnection(c2);
			else
				child.network.addConnection(c1);
		}
		
/*		for (int i = 0; i < g1.network.nodes.size(); i++) {
			if (!g2.network.hasNode(g1.network.nodes.get(i))) child.network.addNode(g1.network.nodes.get(i));
			else {
				if (g1.fitness > g2.fitness) child.network.addNode(g1.network.nodes.get(i));
				else if (g1.fitness < g2.fitness) child.network.addNode(g2.network.getNodeByID(g1.network.nodes.get(i).getNodeID()));
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
				if (g1.fitness > g2.fitness) child.network.addConnection(g1.network.connections.get(i));
				else if (g1.fitness < g2.fitness) child.network.addConnection(g2.network.getConnectionByInnNum(g1.network.connections.get(i).getInnovationNum()));
				else {
					if (Math.random() < 0.5) child.network.addConnection(g1.network.connections.get(i));
					else child.network.addConnection(g2.network.getConnectionByInnNum(g1.network.connections.get(i).getInnovationNum()));
				}

			}
		}

		for (int i = 0; i < g2.network.connections.size(); i++)
			if (!g1.network.hasConnection(g2.network.connections.get(i))) child.network.addConnection(g2.network.connections.get(i));
*/
		child.mutate();

		return child;
	}

	private ArrayList<Species> cullSpeciesForNextGen(ArrayList<Species> species) {

		for (Species s : species) {
			s.sortSpecies();

			for (int i = s.genomes.size() - 1; i > 0; i--) {
				s.genomes.remove(i);
			}

		}

		return species;
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
		double breed;
		calculateAverageFitness();

		for (int i = species.size() - 1; i > -1; i--) {
			species.get(i).calculateAverageFitness();
			breed = Math.floor(species.get(i).averageFitness / averageFitness * Scholar.GENOME_POPULATION);

			if (breed < 1) {
				weakSpecies.add(species.get(i));
				species.remove(i);
			}
		}
	}

	public void runFitnessTests(ArrayList<Genome> genomes) {
		if (Scholar.obj.simultainiousTests) {
			species = Scholar.obj.calculateFitness(species);
		}
		else {
			for (Genome genome : genomes) {
				genome.fitness = Scholar.obj.calculateFitness(genome.network, Scholar.generations.size() + 1, -1, genome.genomeNum);
				System.out.println("Tested Genome");
			}
		}
	}

	public ArrayList<Species> speciateGenomes(ArrayList<Species> species, ArrayList<Genome> genomes) {

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

		return species;
	}

	private boolean sameSpeceies(Genome genome1, Genome genome2) {

		double dd = Scholar.DELTA_DISJOINT * disjoint(genome1.network.connections, genome2.network.connections); // DeltaDisjoint
		double dw = Scholar.DELTA_WEIGHTS * weights(genome1.network.connections, genome2.network.connections); // DeltaWeights

		return dd + dw < Scholar.DELTA_THRESHOLD;
	}

	private double disjoint(ArrayList<Connection> cons1, ArrayList<Connection> cons2) {
		int numDisjointed = 0;

		for (Connection c : cons1)
			if (containsInovation(c, cons2) == -1) numDisjointed++;

		for (Connection c : cons2)
			if (containsInovation(c, cons1) == -1) numDisjointed++;

		return numDisjointed / Math.max(cons1.size(), cons2.size());
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

	private void renumberSpecies() {
		for (int i = 0; i < species.size(); i++) {
			species.get(i).speciesNum = i + 1;
			species.get(i).renumberGenomes();
		}
	}
	
	private void numberChildren(ArrayList<Genome> children) {
		for (int i = 0; i < children.size(); i++)
			children.get(i).genomeNum = i + 1;
	}

	public Generation genNextGen() {
		Generation nextGen = new Generation();
		ArrayList<Genome> children = new ArrayList<Genome>();

		System.out.println("Generating New Species");
		
		if (isFirstGen) {
			for (int i = 0; i < Scholar.GENOME_POPULATION; i++)
				children.add(new Genome(true));
			System.out.println("Generated First Gen Children");
		}
		else {
			System.out.println("Culling Species");
			cullAllSpecies();
			globalRank();

			System.out.println("Removing Stale Species");
			removeStaleSpecies();
			globalRank();

			System.out.println("Removing Weak Species");
			removeWeakSpeceies();
			globalRank();

			calculateAverageFitness();

			System.out.println("Breeding Children");
			children = breedChildren(children); // Check over and test formula for calc. num of children to gen

			System.out.println("Filling Childs To Population Size");
			children = fillChildrenToPop(children); // while # of species and children are < pop. size; pick random species and breed children form them

			// TODO Backup/save
		}

		System.out.println("Numbering Children and Running Fitness Tests");
		numberChildren(children);
		runFitnessTests(children);
		
		System.out.println("Speciating Children");
		nextGen.species = speciateGenomes(cullSpeciesForNextGen(species), children);	// Cull all but top member of each species and then sort the children genomes into species based on a similarity
																						// formula within a certain threshold
		nextGen.renumberSpecies();
		
		System.out.println("Generation Generation Finished");
		return nextGen;
	}

	public void setFirstGen(boolean isFirstGen) {
		this.isFirstGen = isFirstGen;
	}

}
