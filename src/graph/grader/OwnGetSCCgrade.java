package graph.grader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import graph.CapGraph;
import graph.Graph;
import util.GraphLoader;


public class OwnGetSCCgrade {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			Graph g = new CapGraph();
			GraphLoader.loadGraph(g, "data/twitter_combined.txt");

			/*
			 * HashMap<Long, HashSet<Long>> graphEgoNet =
			 * g.getEgonet(760011195751469056L).exportGraph(); for
			 * (HashSet<Long> temp : graphEgoNet.values()) { System.out.println(
			 * "EgoNet " + temp); }
			 */
			List<Graph> graphSCCs = g.getSCCs();

			System.out.println(g.getNumVertices());
			System.out.println(g.getNumEdges());

			List<Set<Integer>> sccs = new ArrayList<Set<Integer>>();
			for (Graph graph : graphSCCs) {
				HashMap<Integer, HashSet<Integer>> curr = graph.exportGraph();
				TreeSet<Integer> scc = new TreeSet<Integer>();
				for (Map.Entry<Integer, HashSet<Integer>> entry : curr.entrySet()) {
					scc.add(entry.getKey());
				}
				sccs.add(scc);

			}
			int countSCC = 0;
			for (Set<Integer> temp : sccs) {
				if (temp.size() > 1) {
					System.out.println(temp);
					countSCC++;
				}
			}
			System.out.println("countSCC " + countSCC);

		} catch (Exception e) {
			// feedback = "An error occurred during runtime.\n" + feedback +
			// "\nError during runtime: " + e;
			// testsPassed = 0;
			// totalTests = 1;
		}

	}

}
