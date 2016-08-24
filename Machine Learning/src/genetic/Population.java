package genetic;

import java.util.Arrays;

public class Population {

	private Chromosome[] chromosomes;

	public Population(int length) {
		chromosomes = new Chromosome [length];
	}

	public Population initializePopulation() {
		for (int i = 0; i < chromosomes.length; i++) {
			chromosomes[i] = new Chromosome(Driver.getGeneticAlgorithm().getTargetChromosome().length).initializeChromosome();
		}

		sortChromosomesByFitness();
		return this;
	}

	public void sortChromosomesByFitness() {
		Arrays.sort(chromosomes, (Chromosome1, Chromosome2) -> {
			int flag = 0;
			if (Chromosome1.getFitness() > Chromosome2.getFitness()) flag = -1;
			else if (Chromosome1.getFitness() < Chromosome2.getFitness()) flag = 1;
			return flag;
		});
	}

	public Chromosome[] getChromosomes() {
		return chromosomes;
	}

}
