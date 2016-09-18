package NEAT.algorithm.hierarchy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Species {

	public ArrayList<Genome> genomes;
	public ArrayList<Genome> culledGenomes;
	
	public int topFitness;
	public int averageFitness;
	public int staleness;
	public int speciesNum;
	
	public Species() {
		genomes = new ArrayList<Genome>();
		genomes = new ArrayList<Genome>();
		topFitness = 0;
		averageFitness = 0;
		staleness = 0;
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
	
	public void calculateAverageFitness() {
		int sum = 0;
		
		for (Genome g : genomes)
			sum += g.fitness;
		
		averageFitness = sum / genomes.size();
	}
	
	public void cullGenome(int index) {
		culledGenomes.add(genomes.get(index));
		genomes.remove(index);
	}
	
	protected void renumberGenomes() {
		for (int i = 0; i < genomes.size(); i++)
			genomes.get(i).genomeNum = i + 1;
	}
	
}
