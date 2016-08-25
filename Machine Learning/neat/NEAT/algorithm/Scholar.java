package NEAT.algorithm;

import java.util.ArrayList;

import NEAT.algorithm.hierarchy.Generation;
import NEAT.task.Objective;

public class Scholar {

	public static Objective obj;
	
	public ArrayList<Generation> generations;
	
	public static final int GENOME_COUNT_IN_POOL = 300;
	
	private int nodeIDCounter;
	private int innovationNumCounter;
	
	public Scholar(Objective obj) {
		Scholar.obj = obj;
		generations = new ArrayList<Generation>();
		nodeIDCounter = 1;
		innovationNumCounter = 1;
	}

	public int getNextNodeID() {
		return nodeIDCounter++;
	}
	
	public int getNextInnovationNum() {
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
