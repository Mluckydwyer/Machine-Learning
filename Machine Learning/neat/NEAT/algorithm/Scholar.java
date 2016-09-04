package NEAT.algorithm;

import java.util.ArrayList;

import NEAT.algorithm.hierarchy.Generation;
import NEAT.task.Objective;

public class Scholar {

	public static Objective			obj;

	public static ArrayList<Generation>	generations;

	public static final int			GENOME_POPULATION					= 300;
	public static final int			STALE_SPECIES						= 15;
	public static final double		REMAING_AFTER_CULL_PERCENT			= 0.50;

	public static final double		PERTURB_WEIGHT_CHANCE				= 0.90;
	public static final double		LINK_MUTATE_CHANCE					= 2.0;
	public static final double		FORCE_INPUT_BIAS_LINK_MUTATE_CHANCE	= 0.40;
	public static final double		NODE_MUTATION_CHANCE				= 0.50;
	public static final double		DISABLE_MUTAION_CHANCE				= 0.40;
	public static final double		ENABLE_MUTAION_CHANCE				= 0.20;
	public static final double		STEP_SIZE							= 0.1;

	public static final double		CROSSOVER_CHANCE					= 0.75;

	public static final double		DELTA_THRESHOLD						= 1.0;
	public static final double		DELTA_DISJOINT						= 2.0;
	public static final double		DELTA_WEIGHTS						= 0.4;

	private static int				innovationNumCounter;

	public Scholar(Objective obj) {
		Scholar.obj = obj;
		generations = new ArrayList<Generation>();
		innovationNumCounter = 1;
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
