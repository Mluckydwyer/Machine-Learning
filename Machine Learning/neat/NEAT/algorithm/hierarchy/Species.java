package NEAT.algorithm.hierarchy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import NEAT.algorithm.neural.Node;

public class Species extends Generation{

	public ArrayList<Genome> genomes;
	
	public Species() {
		genomes = new ArrayList<Genome>();
	}

	public void sortSpecies() {
		Collections.sort(genomes, new Comparator<Genome>() {

			@Override
			public int compare(Genome g1, Genome g2) {
				if (g1.fitness > g2.fitness) return -1;
				else if (g1.fitness < g2.fitness) return 1;
				else return 0;
			}
		});
	}
	
}
