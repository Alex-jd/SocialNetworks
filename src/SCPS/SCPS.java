package SCPS;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

import graph.CapGraph;
import graph.Graph;
import graph.SetCoverSol;
import util.GraphLoader;

public class SCPS {
	// Initializate the Cover data structure (Universe Set)
	// private TreeSet<Integer> universal;
	private Graph locGraph;

	public SCPS(Graph g) {
		this.locGraph = g;
	}

	public Set<Integer> getMinDomSet() {
		// Init the return data structure
		final Set<Integer> minDomSet = new HashSet<Integer>();
		// Init the current cover Graph
		Set<Integer> coverU = new TreeSet<Integer>();
		// Get the Universe Graph
		Set<Integer> universe = locGraph.getUnivers();
		// While coverU not equal universe continue
		Queue<Integer> toExplore = new LinkedList<Integer>(universe);
		while (!isEquals(coverU, universe)) {
			final int next = toExplore.poll();
			final Set<Integer> edgesCurNode = locGraph.getEdgesSort(next);


		}


		return minDomSet;
	}

	private boolean isEquals(Set<Integer> coverU, Set<Integer> universe) {
		if (coverU.size() != universe.size()) return false;
		final Iterator<Integer> coverVer = coverU.iterator();
		while (coverVer.hasNext()) {
			if (!universe.contains(coverVer.next()))
				return false;
		}
		return true;
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
