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
		adjListsMap.put(num,  neighbors);
		numVertices++;
		//System.out.println(numVertices);
	}

	/* (non-Javadoc)
	 * @see graph.Graph#addEdge(int, int)
	 */
	@Override
	public void addEdge(int from, int to) {
		// TODO Auto-generated method stub
		//System.out.println(from + ";" + to);
		//if (from == 1741) System.out.println("addEdge to 1741 ver, to=" + to);
		(adjListsMap.get(from)).add(to);
		//numEdges++;
	}

	/* (non-Javadoc)
	 * @see graph.Graph#getEgonet(int)
	 */
	@Override
	public Graph getEgonet(int center) {
		// TODO Auto-generated method stub
		Graph egoGraph = new CapGraph();
		
		for (int j : adjListsMap.get(center)) {
			System.out.println("egoNet center: " + center + "   j: " + j);
			for (int i : adjListsMap.get(j)) {
				System.out.println("simple i: " + i);
				if (i == center) {
					egoGraph.addVertex(j);
				} else {
					for (int ii : adjListsMap.get(i)) {
						if (center == 7) System.out.println("simple ii: " + ii);
						if (ii == center) {
							
							if (!adjListsMap.containsKey(j) ) {
								egoGraph.addVertex(j);
								System.out.println("I'm here");
							} else System.out.println("KeySet j=" + j + " " + adjListsMap.get(j));
							egoGraph.addEdge(j, i);
							//System.out.println("I'm here");
							System.out.println("egoNet i - j: "+ i + "-" + j);
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
		//System.out.println("size: " + adjListsMap.size());
		
		for (int i : adjListsMap.keySet()) {
			//System.out.println("print i " + i);
			HashSet<Integer> setTemp = new HashSet<Integer>();
			mapTemp.put(i,  setTemp);
			
			for (int j : adjListsMap.get(i)) {
				//System.out.println("print j " + j);
				(mapTemp.get(i)).add(j);
				//System.out.println("HashSet size: " + mapTemp.get(i).size());
			}
			
		}
		return mapTemp;
	}

}
