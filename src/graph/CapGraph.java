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
	public void addVertex(int vertex) {
		// TODO Auto-generated method stub
		//System.out.println(vertex);
		ArrayList<Integer> neighbors = new ArrayList<Integer>();
		if (!adjListsMap.containsKey(vertex)) {
			adjListsMap.put(vertex,  neighbors);
			numVertices++;
		}
		//System.out.println(numVertices);
	}
	
	@Overload
	public void addVertex(ArrayList<Integer> listOfVerteces) {
		for(Integer vertex:listOfVerteces) {
			if (!adjListsMap.containsKey(vertex)) {
				ArrayList<Integer> neighbors = new ArrayList<Integer>();
				adjListsMap.put(vertex,  neighbors);
				numVertices++;
			}
		}
	}
	

	/* (non-Javadoc)
	 * @see graph.Graph#addEdge(int, int)
	 */
	@Override
	public void addEdge(int from, int to) {
		// TODO Auto-generated method stub
		(adjListsMap.get(from)).add(to);
	}
	
	
	public Map<Integer,ArrayList<Integer>>  getMatrix() {
		return adjListsMap;
	}
	
	public ArrayList<Integer> getEdges(int vertex) {
		return adjListsMap.get(vertex);
	}
	
	// Get the Stack of Vertices (LinkedList)
	public Queue<Integer> getVerticesStack() {
		System.out.println("adjListsMap.keySet " +adjListsMap.keySet());
		TreeSet<Integer> sortSet = new TreeSet<Integer>(adjListsMap.keySet());
		System.out.println("sortSet " + new LinkedList<Integer>(sortSet));
		return new LinkedList<Integer>(sortSet);
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
		System.out.println("get New SCC");
		//Queue<Integer> vertices = new LinkedList<Integer>(adjListsMap.keySet()); //Get the primary stack - just list of all nodes
		//Set<Integer> visited = new HashSet<Integer>(); // Collection for visited nodes
		
		//Get the first DFS(G)
		//depthFirstSearch(this, getVerticesStack());
		depthFirstSearch(getVerticesStack());
		//Get the transposition graph G(t)
		//Map<Integer,ArrayList<Integer>> adjListsMapTrans = new HashMap<Integer,ArrayList<Integer>>(graphTranspose(adjListsMap).exportGraph() );
		Graph transGraph = graphTranspose(adjListsMap);
		//Get the DFS(G(t))
		//transGraph.depthFirstSearch(transGraph, transGraph.getVerticesStack());
		//SCC_List.clear();
		//finished.clear();
		//Queue<Integer> test = transGraph.depthFirstSearch(this.finished);
		//transGraph.depthFirstSearch(finished);
		//System.out.println("test_exportGraph "+ test);
		/*for (Graph temp : SCC_List) {
			System.out.println("temp_exportGraph " + temp.exportGraph());
		}*/
		final Queue<Integer> reversOrderFinished = new LinkedList<Integer>();
		final List<Integer> tempArrayList = new ArrayList<Integer>(finished);
		for (int i = tempArrayList.size() -1 ; i >= 0; i--) {
			reversOrderFinished.add(tempArrayList.get(i));
		}
		
		//System.out.println("finished " + finished);
		//System.out.println("reversOrderFinished " + reversOrderFinished);
		
		
		return transGraph.depthFirstSearch(reversOrderFinished);
		//return SCC_List;
	}
	
	//Method to do transposition the graph
	private Graph graphTranspose(Map<Integer,ArrayList<Integer>> graphNonTrans) {
		Graph graphTrans = new CapGraph();
		//System.out.println("graphTranspose " + graphNonTrans);
		for (int vertex : graphNonTrans.keySet()) {
			//System.out.println("graphNonTrans.keySet " + graphNonTrans.keySet());
			//graphTrans.addVertex(graphNonTrans.get(vertex));
			for (int neighbor : graphNonTrans.get(vertex)) {
				//System.out.println("vertex=" + vertex +"neighbor=" + neighbor + " " + "graphNonTrans.get(vertex)=" + graphNonTrans.get(vertex));
				graphTrans.addVertex(neighbor);
				//System.out.println(neighbor);
				graphTrans.addEdge(neighbor, vertex);
				//System.out.println(vertex);
				//System.out.println("GRAPH transpose in loop " + graphTrans.getMatrix());
			}
		}
		System.out.println("GRAPH transpose " + graphTrans.getMatrix());
		return graphTrans;
	}
	
		
	//Create the new collection required to depthFirsSearch
	private Set<Integer> visited = new HashSet<Integer>();
	private Queue<Integer> finished = new LinkedList<Integer>();
	//List<Graph> SCC_List = new LinkedList();
	
	//public Queue<Integer> depthFirstSearch(final Graph currentGraph, final Queue<Integer> vertices) {
	//public Queue<Integer> depthFirstSearch() {
	//public Queue<Integer> depthFirstSearch(final Queue<Integer> vertices) {
	public List<Graph> depthFirstSearch(final Queue<Integer> vertices) {
		List<Graph> SCC_List = new LinkedList<Graph>();
		int currVertex = 0;
		visited.clear();
		finished.clear();
		//System.out.println("vertrices  " + vertices);
		while (!vertices.isEmpty() ) {
			currVertex = vertices.poll();
			//System.out.println("vertrices-" + vertices);
			//System.out.println("currVertex " + currVertex);
			if (!visited.contains(currVertex) ) {
				Graph currCapGraph = new CapGraph(currVertex);
				SCC_List.add(currCapGraph); //Create and add object CapGraph with adding vertex
				depthFirstSearchVisit(currVertex, currCapGraph, -1, true);
			}
		}
		for (Graph temp : SCC_List) {
			//System.out.println("temp_exportGraph depth " + temp.exportGraph());
		}
		//System.out.println("finished " + finished);
		return SCC_List;	
	}
	
	private void depthFirstSearchVisit (final int currVertex, Graph currCapGraph, int toVertex, boolean trigger) {
		
		if (trigger) {
			visited.add(currVertex); // Add v to visited
			currCapGraph.addVertex(currVertex);
			//System.out.println("currCapGraph true" + currCapGraph.getMatrix());
			if (adjListsMap.containsKey(currVertex) && !adjListsMap.get(currVertex).isEmpty() ) {
				for (int currNeighb: adjListsMap.get(currVertex)) { // Iteration of all neighbors of v
					if (!visited.contains(currNeighb) ) {
						//System.out.println("currNeighb true " + currNeighb);
						depthFirstSearchVisit(currVertex, currCapGraph, currNeighb, false); // Recursion
					}
				}
			}
			finished.add(currVertex); // Add v to finished
			//System.out.println("finished true " + finished);
			
		}
		else {
			visited.add(toVertex); // Add v to visited
			//currCapGraph.addEdge(currVertex, toVertex);
			//System.out.println("currCapGraph false " + currCapGraph.getMatrix());
			if (adjListsMap.containsKey(toVertex) && !adjListsMap.get(toVertex).isEmpty()) {
				for (int currNeighb: adjListsMap.get(toVertex)) { // Iteration of all neighbors of v
					if (!visited.contains(currNeighb) ) {
						//System.out.println("I am here!");
						//System.out.println("currVertex false " + currVertex);
						//System.out.println("currNeighb false " + currNeighb);
						depthFirstSearchVisit(currVertex, currCapGraph, currNeighb, false); // Recursion
					}
				}
			}
			//currCapGraph.addEdge(currVertex, toVertex);
			currCapGraph.addVertex(toVertex);
			//System.out.println("currCapGraph false out " + currCapGraph.getMatrix());
			//System.out.println("currCapGraph false export " + currCapGraph.exportGraph());
			finished.add(toVertex); // Add v to finished
			//System.out.println("finished false out " + finished);
		}
			
		//finished.add(currVertex); // Add v to finished
	}

	/* (non-Javadoc)
	 * @see graph.Graph#exportGraph()
	 */
	@Override
	public HashMap<Integer, HashSet<Integer>> exportGraph() {
		// TODO Auto-generated method stub
		HashMap<Integer, HashSet<Integer>> mapTemp = new HashMap<Integer, HashSet<Integer>>(); 
		//System.out.println("KeySet: " + adjListsMap.keySet() );
		for (Integer i : adjListsMap.keySet()) {
			//HashSet<Integer> setTemp = new HashSet<Integer>();
			//mapTemp.put(i,  setTemp);
			//System.out.println("Current vertex: " + i);
			TreeSet<Integer> tempTree = new TreeSet<Integer>();
			for (Integer j : adjListsMap.get(i)) {
				tempTree.add(j);
			}
			mapTemp.put(i,  new HashSet<Integer>(tempTree));
			
		}
		return mapTemp;
	}

}
