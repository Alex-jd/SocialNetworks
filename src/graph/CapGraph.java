/**
 * 
 */
package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

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
		return null;
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
