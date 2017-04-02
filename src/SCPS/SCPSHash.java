package SCPS;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import graph.CapGraph;
import graph.Graph;
import util.GraphLoader;

/* SCPS - Set Cover Problem Solution, Hash - used the HashSet such as base sets
 * This class implements a method for solving the set cover problem with greedy approximation.
 */

public class SCPSHash {
	private Graph locGraph;
	private Set<Integer> minDomSet = new HashSet<Integer>();

	public SCPSHash(Graph g) {
		this.locGraph = g;
	}

	public Set<Integer> getUniverse() {
		return locGraph.getUniverse();
	}

	public Set<Integer> getMinDomSet(Set<Integer> universe) {
		Set<Integer> coverU = new HashSet<Integer>(); // Init the current cover Graph
		Queue<Integer> toExplore = new LinkedList<Integer>(universe); // Init the explorer queue
		int next = toExplore.peek(); // Get the first vertex
		// While coverU size is less than universe continue
		while (coverU.size() < universe.size()) {
			Integer bestNode = getTheBestNode(next, toExplore, coverU);	// Get the Best vertex (greedy algorithm)
			// Test: Is this vertex useful?
			Set<Integer> edgesCurNode = locGraph.getEdgesSort(bestNode);
			Set<Integer> unicEdges = getTheUnicEdges(edgesCurNode, coverU);
			// If the best vertex is not useful break from loop
			if (unicEdges.isEmpty()) {
				break;
			}
			minDomSet.add(bestNode); //Add best vertex to set of minimum dominating vertices
			coverU.addAll(unicEdges); // Add the edges of best vertex to the coverU
			toExplore.remove(new Integer(bestNode)); // Remove this vertex from explorer queue
			// If explorer queue is not empty get the next vertex, else break from loop
			if (!toExplore.isEmpty()) {
				next = toExplore.peek();
			} else {
				break;
			}
		}
		return minDomSet;
	}

	private Integer getTheBestNode(Integer next, Queue<Integer> toExplore, Set<Integer> coverU) {
		Integer theBestNode = next;
		final Iterator<Integer> nextToExplore = toExplore.iterator();
		int bestSize = getTheUnicEdges(locGraph.getEdgesSort(next), coverU).size();
		while (nextToExplore.hasNext()) {
			next = nextToExplore.next();
			int nextSize = getTheUnicEdges(locGraph.getEdgesSort(next), coverU).size();
			// Choosing the best vertex depending on the number of unique edges
			if (bestSize < nextSize) {
				bestSize = nextSize;
				theBestNode = next;
			}
		}
		return theBestNode;
	}

	private Set<Integer> getTheUnicEdges(Set<Integer> edgesCurNode, Set<Integer> coverU) {
		Set<Integer> unicEdges;
		// If the coverU is null return the full set of edges of current vertex
		if (coverU == null) {
			unicEdges = edgesCurNode;
			return unicEdges;
		// if the set of edges of current vertex is null return null
		} else if (edgesCurNode == null) {
			return null;
		// else get the set of unique edges 
		} else {
			unicEdges = new HashSet<Integer>();
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

	public int getMinDomSetSize() {
		return minDomSet.size();
	}

	public static void main(String[] args) {
		Graph g = new CapGraph();
		//GraphLoader.loadGraph(g, "data/twitter_combined.txt");
		GraphLoader.loadGraph(g, "data/facebook_ucsd.txt");
		SCPSHash minDomTest = new SCPSHash(g);
		long startTime = System.nanoTime();
		Set<Integer> minDomSet = minDomTest.getMinDomSet(minDomTest.getUniverse());
		long endTime = System.nanoTime();
		long timeAll = (endTime - startTime);
		System.out.println("size of minDomSet " + minDomTest.getMinDomSetSize());
		System.out.println("elapsed time " + timeAll);
		System.out.println("minDomSet " + minDomSet);
		
	}

}
