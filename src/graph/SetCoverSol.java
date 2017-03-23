package graph;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeSet;

import util.GraphLoader;

public class SetCoverSol {

	private Map<Integer, ArrayList<Integer>> adjListsMap;
	private TreeSet<Integer> cover;
	// private int numOfVertices = 0;

	public SetCoverSol() {
		Graph g = new CapGraph();
		GraphLoader.loadGraph(g, "data/twitter_combined.txt");
		this.adjListsMap = g.getMatrix();
		this.setTheCover();
	}

	public void setTheCover() {
		cover = new TreeSet<Integer>();
		for (Map.Entry<Integer, ArrayList<Integer>> entry : adjListsMap.entrySet()) {
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

	public static void main(String[] args) {
		SetCoverSol coverTest = new SetCoverSol();
		System.out.println(coverTest.getTheCover());
		System.out.println(coverTest.cover.size());
	}

}
