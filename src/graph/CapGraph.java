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

/**
 * @author Your name here.
 * 
 * For the warm up assignment, you must implement your Graph in a class
 * named CapGraph.  Here is the stub file.
 *
 */
public class CapGraph implements Graph {
	
	private int numVertices;
	private int numEdges;
	
	private Map<Integer,ArrayList<Integer>> adjListsMap;
	
	public CapGraph() {
		numVertices = 0;
		numEdges = 0;
		adjListsMap = new HashMap<Integer,ArrayList<Integer>>();
	}
	
	public CapGraph(int vert) {
		numVertices = 0;
		numEdges = 0;
		adjListsMap = new HashMap<Integer,ArrayList<Integer>>();
		addVertex(vert);
	}

	/* (non-Javadoc)
	 * @see graph.Graph#addVertex(int)
	 */
	@Override
	public void addVertex(int num) {
		// TODO Auto-generated method stub
		//System.out.println(num);
		ArrayList<Integer> neighbors = new ArrayList<Integer>();
		if (!adjListsMap.containsKey(num)) {
			adjListsMap.put(num,  neighbors);
			numVertices++;
		}
		//System.out.println(numVertices);
	}

	/* (non-Javadoc)
	 * @see graph.Graph#addEdge(int, int)
	 */
	@Override
	public void addEdge(int from, int to) {
		// TODO Auto-generated method stub
		(adjListsMap.get(from)).add(to);

	}
	
	public ArrayList<Integer> getEdges(int vertex) {
		return adjListsMap.get(vertex);
	}

	/* (non-Javadoc)
	 * @see graph.Graph#getEgonet(int)
	 */
	@Override
	public Graph getEgonet(int center) {
		// TODO Auto-generated method stub
		Graph egoGraph = new CapGraph();
		
		for (final int firstNeighb : adjListsMap.get(center)) {	//iterator neighbors for center (current) vertex
			for (final int secNeighb : adjListsMap.get(firstNeighb)) { //iterator neighbors for vertex j (neighbors of center neighbors)
				if (secNeighb == center) {	// if neighbor of center neighbors equals to center 
					egoGraph.addVertex(firstNeighb);	// add the vertex to Object egoGraph
				} else {					// 
					for (final int firdNeighb : adjListsMap.get(secNeighb)) { // iterator neighbors of neighbors neighbor
						if (firdNeighb == center) {	//if neighbor of double neighbors (two hope way) equals to center
							egoGraph.addVertex(firstNeighb);	// add the vertex to Object egoGraph
							egoGraph.addEdge(firstNeighb, secNeighb); // add the edges to Object egoGraph
						}
					}
				}
			}	
		}
			
		return egoGraph;
	}

	/* (non-Javadoc)
	 * @see graph.Graph#getSCCs()
	 */
	@Override
	public List<Graph> getSCCs() {
		// TODO Auto-generated method stub
		Queue<Integer> virtices = new LinkedList<Integer>(adjListsMap.keySet()); //Get the primary stack - just list of all nodes
		//Set<Integer> visited = new HashSet<Integer>(); // Collection for visited nodes
		
		//Get the first DFS(G)
		deepFirstSearch(adjListsMap, virtices);
		//Get the transposition graph G(t)
		Map<Integer,ArrayList<Integer>> adjListsMapTrans = new HashMap<Integer,ArrayList<Integer>>(graphTranspose(adjListsMap) );
		//Get the DFS(G(t))
		
		System.out.println(finished);
		return null;
	}
	
	//Method to do transposition the graph
	private Map<Integer,ArrayList<Integer>> graphTranspose(Map<Integer,ArrayList<Integer>> graphNonTrans) {
		for (int vertex: graphNonTrans.get(key)) {
			
		}
		
		
		return null;
	}
	
	private Set<Integer> visited = new HashSet<Integer>();
	private Queue<Integer> finished = new LinkedList<Integer>();
	
	private Queue<Integer> deepFirstSearch(final Map<Integer, ArrayList<Integer>> adjListsMap2, final Queue<Integer> virtices) {
		int currVertex = 0;
		visited.clear();
		finished.clear();
		while (!virtices.isEmpty() ) {
			currVertex = virtices.poll();
			if (!visited.contains(currVertex) ) {
				deepFirstSearchVisit(currVertex);
			}
		}
		return finished;
		

	}
	
	private void deepFirstSearchVisit (final int currVertex) {
		visited.add(currVertex); // Add v to visited
		for (int currNeighb: adjListsMap.get(currVertex)) { // Iteration of all neighbors of v
			if (!visited.contains(currNeighb) ) {
				deepFirstSearchVisit(currNeighb); // Recursion
			}
		}
		finished.add(currVertex); // Add v to finished
		
	}

	/* (non-Javadoc)
	 * @see graph.Graph#exportGraph()
	 */
	@Override
	public HashMap<Integer, HashSet<Integer>> exportGraph() {
		// TODO Auto-generated method stub
		HashMap<Integer, HashSet<Integer>> mapTemp = new HashMap<Integer, HashSet<Integer>>(); 
		//System.out.println("KeySet: " + adjListsMap.keySet() );
		for (int i : adjListsMap.keySet()) {
			HashSet<Integer> setTemp = new HashSet<Integer>();
			mapTemp.put(i,  setTemp);
			//System.out.println("Current vertex: " + i);
			
			for (int j : adjListsMap.get(i)) {
				(mapTemp.get(i)).add(j);
			}
			
		}
		return mapTemp;
	}

}
