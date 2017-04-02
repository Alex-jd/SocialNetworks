package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import util.GraphLoader;

public class SetCoverSol {

	private TreeSet<Integer> cover;
	// private int numOfVertices = 0;
	private Graph g;

	public SetCoverSol(Graph g) {
		this.g = g;
		// this.setTheCover();
	}

	public void setTheCover() {
		cover = new TreeSet<Integer>();
		for (Map.Entry<Integer, ArrayList<Integer>> entry : g.getGraph().entrySet()) {
			if (!cover.contains(entry.getKey())) {
				cover.add(entry.getKey());
			}
			for (int i : entry.getValue()) {
				if (!cover.contains(i)) {
					cover.add(i);
				}
			}
		}
	}

	public TreeSet<Integer> getTheCover() {
		return cover;
	}

	public List<Set<Integer>> getListOfSCC() {
		List<Graph> graphSCCs = g.getSCCs();
		// System.out.println(g.getNumVertices());
		// System.out.println(g.getNumEdges());
		// int temp1 = 0;
		List<Set<Integer>> sccs = new ArrayList<Set<Integer>>();
		for (Graph graph : graphSCCs) {
			HashMap<Integer, HashSet<Integer>> curr = graph.exportGraph();
			Set<Integer> scc = new TreeSet<Integer>();
			for (Map.Entry<Integer, HashSet<Integer>> entry : curr.entrySet()) {
				scc.add(entry.getKey());
				// temp1++;
			}
			sccs.add(scc);
		}
		return sccs;
	}


	public static void main(String[] args) {
		Graph g = new CapGraph();
		GraphLoader.loadGraph(g, "data/twitter_combined.txt");
		// GraphLoader.loadGraph(g, "data/facebook_ucsd.txt");
		SetCoverSol coverTest = new SetCoverSol(g);
		List<Set<Integer>> listSet = coverTest.getListOfSCC();
		// Collections.sort(listSet, new SortedBySize());
		listSet.sort(new SortedBySize());
		for (Set<Integer> tempSet : listSet)
			System.out.println(tempSet);
	}

}
