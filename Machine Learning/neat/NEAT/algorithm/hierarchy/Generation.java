package NEAT.algorithm.hierarchy;

import java.util.ArrayList;

import NEAT.algorithm.Scholar;
import NEAT.algorithm.neural.Node;
import NEAT.task.Objective;

public class Generation extends Scholar {

	public ArrayList<Species> species;
	
	public Generation(Objective obj) {
		super(obj);
		species = new ArrayList<Species>();
	}
	
	private void addPuts() {
		for (Species s : species)
			for (Genome g : s.genomes) {
				for (int i = 0; i < obj.inputNodeCount; i++)
					g.network.nodes.add(new Node(Node.NODE_TYPE_INPUT, getNextNodeID(), obj));
				for (int i = 0; i < obj.outputNodeCount; i++)
					g.network.nodes.add(new Node(Node.NODE_TYPE_OUTPUT, getNextNodeID(), obj));
			}
	}
	
}
