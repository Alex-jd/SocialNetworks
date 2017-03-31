package SCPS;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import graph.CapGraph;
import graph.Graph;
import graph.SetCoverSol;
import util.GraphLoader;

public class SCPSHash {
	// Initializate the Cover data structure (Universe Set)
	// private HashSet<Integer> universal;
	private Graph locGraph;
	private Set<Integer> minDomSet = new HashSet<Integer>();

	public SCPSHash(Graph g) {
		this.locGraph = g;
	}

	public Set<Integer> getUniverse() {
		return locGraph.getUniverse();
	}

	public Set<Integer> getMinDomSet(Set<Integer> universe) {
		// Init the current cover Graph
		Set<Integer> coverU = new HashSet<Integer>();
		// Get the Universe Graph
		// Set<Integer> universe = locGraph.getUnivers();
		// While coverU not equal universe continue
		Queue<Integer> toExplore = new LinkedList<Integer>(universe);
		int next = toExplore.peek();
		while (coverU.size() < universe.size()) {
			// Get the edges of the next vertex
			Integer bestNode = getTheBestNode(next, toExplore, coverU);
			minDomSet.add(bestNode);
			Set<Integer> edgesCurNode = locGraph.getEdgesSort(bestNode);
			Set<Integer> unicEdges = getTheUnicEdges(edgesCurNode, coverU);
			coverU.addAll(unicEdges);
			toExplore.remove(new Integer(bestNode));
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
		Set<Integer> unicEdges;
		if (coverU == null) {
			unicEdges = edgesCurNode;
			return unicEdges;
		} else if (edgesCurNode == null) {
			return null;
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
		SetCoverSol coverSol = new SetCoverSol(g);
		SCPSHash minDomTest = new SCPSHash(g);
		long startTime = System.nanoTime();
		// System.out.println("minDomSet " +
		minDomTest.getMinDomSet(minDomTest.getUniverse());
		long endTime = System.nanoTime();
		long timeAll = (endTime - startTime);
		System.out.println("size of minDomSet " + minDomTest.getMinDomSetSize());
		System.out.println("elapsed time " + timeAll);
		// ==============================================
		g = new CapGraph();
		// GraphLoader.loadGraph(g, "data/twitter_combined.txt");
		GraphLoader.loadGraph(g, "data/facebook_ucsd.txt");
		coverSol = new SetCoverSol(g);
		minDomTest = new SCPSHash(g);
		Set<Integer> minDomSet = new HashSet<Integer>();
		startTime = System.nanoTime();
		for (Set<Integer> set : coverSol.getListOfSCC()) {
			// System.out.println("curr list of SCC " + set);
			Set<Integer> tempSet = minDomTest.getMinDomSet(set);
			// System.out.println("temp MIN DOM SET " + tempSet);
			minDomSet.addAll(tempSet);
		}
		endTime = System.nanoTime();
		timeAll = (endTime - startTime);
		// System.out.println("minDomSet " + minDomSet);
		System.out.println("minDomSet size " + minDomSet.size());
		System.out.println("elapsed time " + timeAll);
	}

}
