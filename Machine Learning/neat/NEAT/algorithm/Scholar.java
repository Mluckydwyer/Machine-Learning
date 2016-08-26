package NEAT.algorithm;

import java.util.ArrayList;

import NEAT.algorithm.hierarchy.Generation;
import NEAT.task.Objective;

public class Scholar {

	public static Objective obj;
	
	public ArrayList<Generation> generations;
	
	public static final int GENOME_COUNT_IN_POOL = 300;
	
	public static final double PERTURB_WEIGHT_CHANCE = 0.90;
	public static final double LINK_MUTATE_CHANCE = 2.0;
	public static final double FORCE_INPUT_BIAS_LINK_MUTATE_CHANCE = 0.40;
	public static final double NODE_MUTATION_CHANCE = 0.50;
	public static final double DISABLE_MUTAION_CHANCE = 0.40;
	public static final double ENABLE_MUTAION_CHANCE = 0.20;
	
	public static final double CROSSOVER_CHANCE = 0.75;
	public static final double STEP_SIZE = 0.10;


	
	private static int nodeIDCounter;
	private static int innovationNumCounter;
	
	public Scholar(Objective obj) {
		Scholar.obj = obj;
		generations = new ArrayList<Generation>();
		nodeIDCounter = 1;
		innovationNumCounter = 1;
	}

	public static int getNextNodeID() {
		return nodeIDCounter++;
	}
	
	public static int getNextInnovationNum() {
		return innovationNumCounter++;
	}
	
	public void learn() {
		Generation firstGen = new Generation();
		firstGen.setFirstGen(true);
		generations.add(firstGen.genNextGen());
	}
	
	public void evolve() {
		generations.add(generations.get(generations.size() - 1).genNextGen());
	}
}
