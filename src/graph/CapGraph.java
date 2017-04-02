/**
 * 
 */
package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Your name here.
 * 
 *         For the warm up assignment, you must implement your Graph in a class
 *         named CapGraph. Here is the stub file.
 *
 */
public class CapGraph implements Graph {

	private int numVertices;
	private int numEdges;

	private Map<Integer, ArrayList<Integer>> adjListsMap;

	public CapGraph() {
		numVertices = 0;
		numEdges = 0;
		adjListsMap = new HashMap<Integer, ArrayList<Integer>>();
	}

	public CapGraph(int vert) {
		numVertices = 0;
		numEdges = 0;
		adjListsMap = new HashMap<Integer, ArrayList<Integer>>();
		addVertex(vert);
	}

	public CapGraph(Map<Integer, ArrayList<Integer>> adjListsMap, int numVertices, int numEdges) {
		this.numVertices = numVertices;
		this.numEdges = numEdges;
		this.adjListsMap = adjListsMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see graph.Graph#addVertex(int)
	 */
	@Override
	public void addVertex(int vertex) {
		// TODO Auto-generated method stub
		// System.out.println(vertex);
		ArrayList<Integer> neighbors = new ArrayList<Integer>();
		if (!adjListsMap.containsKey(vertex)) {
			adjListsMap.put(vertex, neighbors);
			numVertices++;
		}
		// System.out.println(numVertices);
	}

	@Overload
	public void addVertex(ArrayList<Integer> listOfVerteces) {
		for (Integer vertex : listOfVerteces) {
			if (!adjListsMap.containsKey(vertex)) {
				ArrayList<Integer> neighbors = new ArrayList<Integer>();
				adjListsMap.put(vertex, neighbors);
				numVertices++;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see graph.Graph#addEdge(int, int)
	 */
	@Override
	public void addEdge(int from, int to) {
		// TODO Auto-generated method stub
		if (!(adjListsMap.get(from)).contains(to)) {
			(adjListsMap.get(from)).add(to);
			numEdges++;
		}
	}

	public Map<Integer, ArrayList<Integer>> getGraph() {
		return adjListsMap;
	}

	public ArrayList<Integer> getEdges(int vertex) {
		return adjListsMap.get(vertex);
	}

	public Set<Integer> getEdgesSort(int vertex) {
		return new TreeSet<Integer>(adjListsMap.get(vertex));
	}

	public int getNumVertices() {
		return numVertices;
	}

	public int getNumEdges() {
		return numEdges;
	}

	// Get the Stack of Vertices (LinkedList)
	public Queue<Integer> getVerticesStack() {
		TreeSet<Integer> sortSet = new TreeSet<Integer>(adjListsMap.keySet());
		return new LinkedList<Integer>(sortSet);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see graph.Graph#getEgonet(int)
	 */
	@Override
	public Graph getEgonet(int center) {
		// TODO Auto-generated method stub
		Graph egoGraph = new CapGraph();

		for (final int firstNeighb : adjListsMap.get(center)) {
			for (final int secNeighb : adjListsMap.get(firstNeighb)) {
				if (secNeighb == center) { // if neighbor of center neighbors equals to center
					egoGraph.addVertex(firstNeighb); // add the vertex to Object egoGraph
				} else { //
					for (final int firdNeighb : adjListsMap.get(secNeighb)) {
						if (firdNeighb == center) {
							egoGraph.addVertex(firstNeighb);
							egoGraph.addEdge(firstNeighb, secNeighb);
						}
					}
				}
			}
		}

		return egoGraph;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see graph.Graph#getSCCs()
	 */
	@Override
	public List<Graph> getSCCs() {
		// TODO Auto-generated method stub
		// Get the first DFS(G)
		depthFirstSearch(getVerticesStack());
		// Get the transposition graph G(t)
		final Graph transGraph = graphTranspose(adjListsMap);
		// Get the DFS(G(t))
		final Queue<Integer> reversOrderFinished = new LinkedList<Integer>();
		final List<Integer> tempArrayList = new ArrayList<Integer>(finished);
		// Create the reverse order finished list
		for (int i = tempArrayList.size() - 1; i >= 0; i--) {
			reversOrderFinished.add(tempArrayList.get(i));
		}
		return transGraph.depthFirstSearch(reversOrderFinished);
	}

	// Method to do transposition the graph
	private Graph graphTranspose(final Map<Integer, ArrayList<Integer>> graphNonTrans) {
		final Graph graphTrans = new CapGraph();
		for (int vertex : graphNonTrans.keySet()) {
			for (int neighbor : graphNonTrans.get(vertex)) {
				graphTrans.addVertex(neighbor);
				graphTrans.addEdge(neighbor, vertex);
			}
		}
		return graphTrans;
	}

	// Create the new collections required for depthFirsSearch
	private Set<Integer> visited = new HashSet<Integer>();
	private Queue<Integer> finished = new LinkedList<Integer>();

	public List<Graph> depthFirstSearch(final Queue<Integer> vertices) {
		final List<Graph> SCC_List = new LinkedList<Graph>();
		int currVertex = 0;
		visited.clear();
		finished.clear();
		while (!vertices.isEmpty()) {
			currVertex = vertices.poll();
			if (!visited.contains(currVertex)) {
				final Graph currCapGraph = new CapGraph(currVertex);
				SCC_List.add(currCapGraph); // Create and add object CapGraph with adding vertex
				depthFirstSearchVisit(currVertex, currCapGraph, -1, true);
			}
		}
		return SCC_List;
	}

	private void depthFirstSearchVisit(final int currVertex, final Graph currCapGraph, final int toVertex,
			boolean trigger) {

		if (trigger) {
			visited.add(currVertex); // Add v to visited
			currCapGraph.addVertex(currVertex); // add the vertex to currCapGraph
			if (adjListsMap.containsKey(currVertex) && !adjListsMap.get(currVertex).isEmpty()) {
				for (int currNeighb : adjListsMap.get(currVertex)) {
					if (!visited.contains(currNeighb)) {
						depthFirstSearchVisit(currVertex, currCapGraph, currNeighb, false); // Recursion
					}
				}
			}
			finished.add(currVertex); // Add v to finished
		} else {
			visited.add(toVertex); // Add v to visited
			if (adjListsMap.containsKey(toVertex) && !adjListsMap.get(toVertex).isEmpty()) {
				for (int currNeighb : adjListsMap.get(toVertex)) {
					if (!visited.contains(currNeighb)) {
						depthFirstSearchVisit(currVertex, currCapGraph, currNeighb, false); // Recursion
					}
				}
			}

			currCapGraph.addVertex(toVertex); // add the vertex to currCapGraph
			finished.add(toVertex); // Add v to finished
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see graph.Graph#exportGraph()
	 */
	@Override
	public HashMap<Integer, HashSet<Integer>> exportGraph() {
		// TODO Auto-generated method stub
		final HashMap<Integer, HashSet<Integer>> mapTemp = new HashMap<Integer, HashSet<Integer>>();
		for (final Map.Entry<Integer, ArrayList<Integer>> entry : adjListsMap.entrySet()) {
			mapTemp.put(entry.getKey(), new HashSet<Integer>(entry.getValue()));
		}
		return mapTemp;
	}

	public Set<Integer> getUniverse() {
		Set<Integer> universe = new TreeSet<Integer>();
		for (Map.Entry<Integer, ArrayList<Integer>> entry : adjListsMap.entrySet()) {
			if (!universe.contains(entry.getKey())) {
				universe.add(entry.getKey());
			}
			for (int i : entry.getValue()) {
				if (!universe.contains(i)) {
					universe.add(i);
				}
			}
		}
		return universe;
	}

}
