package SCPS;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import graph.CapGraph;
import graph.Graph;
import util.GraphLoader;

public class SCPS {
	// Initializate the Cover data structure (Universe Set)
	// private TreeSet<Integer> universal;
	private Graph locGraph;
	private Set<Integer> minDomSet = new HashSet<Integer>();

	public SCPS(Graph g) {
		this.locGraph = g;
	}

	public Set<Integer> getMinDomSet() {
		// Init the return data structure

		// Init the current cover Graph
		Set<Integer> coverU = new TreeSet<Integer>();
		// Get the Universe Graph
		Set<Integer> universe = locGraph.getUniverse();
		// While coverU not equal universe continue
		Queue<Integer> toExplore = new LinkedList<Integer>(universe);
		// List<Integer> temp1 = (List) toExplore;
		// int next = temp1.get(getRandomIndex(temp1.size()));
		int next = toExplore.peek();
		// while (!isEquals(coverU, universe)) {
		while (coverU.size() < universe.size()) {
			// Get the edges of the next vertex
			Integer bestNode = getTheBestNode(next, toExplore, coverU);
			minDomSet.add(bestNode);
			// System.out.println("size of the minDomSet " + minDomSet.size());
			// System.out.println("bestNode " + bestNode);
			Set<Integer> edgesCurNode = locGraph.getEdgesSort(bestNode);
			Set<Integer> unicEdges = getTheUnicEdges(edgesCurNode, coverU);
			// System.out.println("Unicque edges are " + unicEdges);
			coverU.addAll(unicEdges);
			// System.out.println("size of coverU " + coverU.size());
			toExplore.remove(new Integer(bestNode));
			// System.out.println("size of the toExplore " + toExplore.size());
			// toExplore.remove((int) 100500);// remove(new Integer(100500))
			if (!toExplore.isEmpty()) {
				// List<Integer> temp = (List) toExplore;
				// next = temp.get(getRandomIndex(temp.size()));
				next = toExplore.peek();
				// System.out.println("get the next " + next);
			} else {
				break;
			}

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
			int nextSize = getTheUnicEdges(locGraph.getEdgesSort(next), coverU).size();
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
		// System.out.println("print the Unicque edges (getTheUnicEdges " +
		// unicEdges);
		return unicEdges;
	}

	private boolean isEquals(Set<Integer> coverU, Set<Integer> universe) {
		// If size of sets is not equal return false
		if (coverU.size() != universe.size()) {
			System.out.println("size is not equal - false, size of coverU is " + coverU.size() + "| size of univers is "
					+ universe.size());
			return false;
		}
		Iterator<Integer> coverVer = coverU.iterator();
		System.out.println("Go to test the coverU and univers");
		while (coverVer.hasNext()) {
			System.out.println("Go to while (isEqual)");
			Integer next = coverVer.next();
			if (!universe.contains(next))
				System.out.println("cover is not equal to universe");
			return false;
		}
		System.out.println("cover is equal to universe");
		return true;
	}

	private int getRandomIndex(int size) {
		Random r = new Random();
		return r.nextInt(size);
	}

	public int getMinDomSetSize() {
		return minDomSet.size();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Graph g = new CapGraph();
		// GraphLoader.loadGraph(g, "data/twitter_combined.txt");
		GraphLoader.loadGraph(g, "data/facebook_ucsd.txt");
		// SetCoverSol coverTest = new SetCoverSol(g);
		SCPS minDomTest = new SCPS(g);
		long startTime = System.nanoTime();
		System.out.println("minDomSet " + minDomTest.getMinDomSet());
		long endTime = System.nanoTime();
		long timeAll = (endTime - startTime);
		System.out.println("elapsed time " + timeAll);
		System.out.println("size of minDomSet " + minDomTest.getMinDomSetSize());
		// System.out.println(coverTest.getTheCover());
		// System.out.println(g.getGraphValueSort());
	}

}
