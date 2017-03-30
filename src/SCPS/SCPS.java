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
		int next = toExplore.poll();// Get the next vertex
		while (!isEquals(coverU, universe)) {
			// Get the edges of the next vertex
			Integer bestNode = getTheBestNode(next, toExplore, coverU);
			final Set<Integer> edgesCurNode = locGraph.getEdgesSort(next);
			getTheUnicEdges(edgesCurNode, coverU);
			toExplore.remove((int) 100500);// remove(new Integer(100500))
			next = toExplore.peek();// Get the next vertex
		}

		return minDomSet;
	}

	private Integer getTheBestNode(Integer next, Queue<Integer> toExplore, Set<Integer> coverU) {
		Integer theBestNode = next;
		// getTheUnicEdges(locGraph.getEdgesSort(next), coverU).size();
		final Iterator<Integer> nextToExplore = toExplore.iterator();
		int bestSize = getTheUnicEdges(locGraph.getEdgesSort(next), coverU).size();
		while (nextToExplore.hasNext()) {
			next = nextToExplore.next();
			int nextSize = getTheUnicEdges(locGraph.getEdgesSort(nextToExplore.next()), coverU).size();
			if (bestSize < nextSize) {
				bestSize = nextSize;
				theBestNode = next;
			}
		}
		return theBestNode;
	}

	private Set<Integer> getTheUnicEdges(Set<Integer> edgesCurNode, Set<Integer> coverU) {
		final Set<Integer> unicEdges;
		if (coverU == null) {
			unicEdges = edgesCurNode;
			return unicEdges;
		} else if (edgesCurNode == null) {
			return null;
		} else {
			unicEdges = new TreeSet<Integer>();
			final Iterator<Integer> curNodeIter = edgesCurNode.iterator();
			while (curNodeIter.hasNext()) {
				Integer next = curNodeIter.next();
				if (!coverU.contains(next)) {
					unicEdges.add(next);
				}
			}
		}
		return unicEdges;
	}

	private boolean isEquals(Set<Integer> coverU, Set<Integer> universe) {
		// If size of sets is not equal return false
		if (coverU.size() != universe.size())
			return false;
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
