package NEAT.algorithm.hierarchy;

import java.util.ArrayList;

import NEAT.task.Objective;

public class Species extends Generation{

	public ArrayList<Genome> genomes;
	
	public Species(Objective obj) {
		super(obj);
		genomes = new ArrayList<Genome>();
	}

}
