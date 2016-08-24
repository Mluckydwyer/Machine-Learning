package genetic;

import java.util.Arrays;

public class Driver {

	private static int[]	targetChromosome			= {1, 1, 1, 1, 1, 0, 0, 0, 0, 0};
	private static double	crossoverRate				= 0.75;
	private static double	mutationRate				= 0.25;
	private static int		numberOfEliteChromosomes	= 1;
	private static int		tournamentSelectionSize		= 4;

	private static GeneticAlgorithm geneticAlgorithm;
	
	public static void main(String[] args) {
		geneticAlgorithm = new GeneticAlgorithm(targetChromosome, crossoverRate, mutationRate, numberOfEliteChromosomes, tournamentSelectionSize);
		Population population = new Population(getGeneticAlgorithm().getPopulationSize()).initializePopulation();
		int generationNumber = 0;
		
		System.out.println("--------------------------------------");
		System.out.println("Generation # 0 | Fittest Chromosome Fitness: " + population.getChromosomes()[0].getFitness());
		printPopulation(population, "Target Chromosome: " + Arrays.toString(getGeneticAlgorithm().getTargetChromosome()));
		
		while (population.getChromosomes()[0].getFitness() < getGeneticAlgorithm().getTargetChromosome().length) {
			generationNumber++;
			System.out.println("\n--------------------------------------");
			population = getGeneticAlgorithm().evolve(population);
			population.sortChromosomesByFitness();
			System.out.println("Generation # " + generationNumber + " | Fittest Chromosome Fitness: " + population.getChromosomes()[0].getFitness());
			printPopulation(population, "Target Chromosome: " + Arrays.toString(getGeneticAlgorithm().getTargetChromosome()));
		}
		
		System.out.println("\nSolution Found in Generation # " + generationNumber);
		System.out.println("Chromosome # 0 : " + population.getChromosomes()[0]);
	}

	public static void printPopulation(Population population, String heading) {
		System.out.println(heading);
		System.out.println("--------------------------------------");
		for (int i = 0; i < population.getChromosomes().length; i++) {
			System.out.println("Chromosome # " + i + " : " + Arrays.toString(population.getChromosomes()[i].getGenes()) + "| Fitness: " + population.getChromosomes()[i].getFitness());
		}
	}

	public static GeneticAlgorithm getGeneticAlgorithm() {
		return geneticAlgorithm;
	}

}
