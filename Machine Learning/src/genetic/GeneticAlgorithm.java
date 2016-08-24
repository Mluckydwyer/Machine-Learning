package genetic;

public class GeneticAlgorithm {

	// 10 long
	private int[]	targetChromosome;
	private double	crossoverRate;
	private double	mutationRate;
	private int		populationSize;
	private int		numberOfEliteChromosomes;
	private int		tournamentSelectionSize;

	public GeneticAlgorithm(int[] targetSolution, double crossoverRate, double mutationRate, int numberOfEliteChromosomes, int tournamentSelectionSize) {
		this.targetChromosome = targetSolution;
		this.crossoverRate = crossoverRate;
		this.mutationRate = mutationRate;
		this.numberOfEliteChromosomes = numberOfEliteChromosomes;
		this.tournamentSelectionSize = tournamentSelectionSize;
		this.populationSize = 8;
	}

	public Population evolve(Population population) {
		return mutatePopulation(crossoverPopulation(population));
	}

	private Population crossoverPopulation(Population population) {
		Population crossoverPopulation = new Population(population.getChromosomes().length);

		for (int i = 0; i < numberOfEliteChromosomes; i++) {
			crossoverPopulation.getChromosomes()[i] = population.getChromosomes()[i];
		}

		for (int i = numberOfEliteChromosomes; i < population.getChromosomes().length; i++) {
			Chromosome chromosome1 = selectTournamentPopulation(population).getChromosomes()[0];
			Chromosome chromosome2 = selectTournamentPopulation(population).getChromosomes()[0];
			crossoverPopulation.getChromosomes()[i] = crossoverChromosome(chromosome1, chromosome2);
		}

		return crossoverPopulation;
	}

	private Population mutatePopulation(Population population) {
		Population mutatePopulation = new Population(population.getChromosomes().length);

		for (int i = 0; i < numberOfEliteChromosomes; i++) {
			mutatePopulation.getChromosomes()[i] = population.getChromosomes()[i];
		}

		for (int i = numberOfEliteChromosomes; i < population.getChromosomes().length; i++) {
			mutatePopulation.getChromosomes()[i] = mutateChromosome(population.getChromosomes()[i]);
		}

		return mutatePopulation;
	}

	private Chromosome crossoverChromosome(Chromosome chromosome1, Chromosome chromosome2) {
		Chromosome crossoverChromosome = new Chromosome(targetChromosome.length);

		for (int i = 0; i < chromosome1.getGenes().length; i++) {
			if (Math.random() < 0.5) crossoverChromosome.getGenes()[i] = chromosome1.getGenes()[i];
			else crossoverChromosome.getGenes()[i] = chromosome2.getGenes()[i];
		}

		return crossoverChromosome;
	}

	private Chromosome mutateChromosome(Chromosome chromosome) {
		Chromosome mutateChromosome = new Chromosome(targetChromosome.length);

		for (int i = 0; i < chromosome.getGenes().length; i++) {
			if (Math.random() < mutationRate) {
				if (Math.random() < 0.5) mutateChromosome.getGenes()[i] = 1;
				else mutateChromosome.getGenes()[i] = 0;
			}
			else mutateChromosome.getGenes()[i] = chromosome.getGenes()[i];
		}

		return mutateChromosome;
	}

	private Population selectTournamentPopulation(Population population) {
		Population tournamentPopulation = new Population(tournamentSelectionSize);

		for (int i = 0; i < tournamentSelectionSize; i++) {
			tournamentPopulation.getChromosomes()[i] = population.getChromosomes()[(int) (Math.random() * population.getChromosomes().length)];
		}

		tournamentPopulation.sortChromosomesByFitness();
		return tournamentPopulation;
	}

	public int[] getTargetChromosome() {
		return targetChromosome;
	}

	public void setTargetChromosome(int[] targetChromosome) {
		this.targetChromosome = targetChromosome;
	}

	public double getCrossoverRate() {
		return crossoverRate;
	}

	public void setCrossoverRate(double crossoverRate) {
		this.crossoverRate = crossoverRate;
	}

	public double getMutationRate() {
		return mutationRate;
	}

	public void setMutationRate(double mutationRate) {
		this.mutationRate = mutationRate;
	}

	public int getPopulationSize() {
		return populationSize;
	}

	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}

	public int getNumberOfEliteChromosomes() {
		return numberOfEliteChromosomes;
	}

	public void setNumberOfEliteChromosomes(int numberOfEliteChromosomes) {
		this.numberOfEliteChromosomes = numberOfEliteChromosomes;
	}

	public int getTournamentSelectionSize() {
		return tournamentSelectionSize;
	}

	public void setTournamentSelectionSize(int tournamentSelectionSize) {
		this.tournamentSelectionSize = tournamentSelectionSize;
	}

}
