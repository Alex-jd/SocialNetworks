package SCPS;

import java.util.TreeSet;

import graph.CapGraph;
import graph.Graph;
import graph.SetCoverSol;
import util.GraphLoader;

public class SCPS {
	// Initializate the Cover data structure (Universe Set)
	private TreeSet<Integer> universal;

	public SCPS(TreeSet<Integer> universal) {
		this.universal = universal;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Graph g = new CapGraph();
		GraphLoader.loadGraph(g, "data/twitter_combined.txt");
		SetCoverSol coverTest = new SetCoverSol(g);
		System.out.println(coverTest.getTheCover());
		System.out.println(g.getGraphValueSort());
	}

}
