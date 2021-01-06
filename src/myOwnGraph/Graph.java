/** MET CS 526
 * Term Project
 * Graph class
 * by Iryna Chervachidze
 * April 24, 2020
 * 
 */
package myOwnGraph;

import java.util.ArrayList;

public class Graph {
	private Vertex[] vertices = new Vertex[26]; // array of all vertices
	private int size = 0;

	
	//constructor
	public Graph() {} // new empty graph
	
	//getters
	/** returns an array of all vertices in the graph*/
	public Vertex[] getVertices() {
		if (size == vertices.length)
			return vertices;
		else{
			Vertex[] temp = new Vertex[size];
			for (int i = 0; i < size; i++)
				temp[i] = vertices[i];
			return temp;
		}
	}
	
	/**returns the number of vertices in the graph*/
	public int numVertices() {return size;}
	
	/**returns the vertex at the specified index*/
	public Vertex get(int index) {return vertices[index];}
	
	/** Inserts and returns a new vertex*/
	public Vertex insertVertex(Vertex v) {
		vertices[size++] = v;//increment size of vertices array
		return v;
	}
	
	
	/**Inserts and returns a new edge, if it does not already exists*/
	public Edge insertEdge(Edge e, Vertex u, Vertex v) 
									throws IllegalArgumentException {
		//if the edge already exists, throw exception
		if (getEdge(u, v) == null) {
			u.getEdges().add(e); //add to the list of incident edges of u
			v.getEdges().add(e); //add to the list of incident edges of v
			e.setEndpoints(u, v); //set end points for the edge
			return e;
		}
		else throw new IllegalArgumentException ("Edge from u to v exists");
	}
	
	/**Finds and returns a vertex with a given name, if exists or null.*/
	public Vertex findVertex(char ch) {
		for (int i = 0; i <= size - 1; i++) {
			Vertex v = vertices[i];
			if (v.getName() == ch)
				return v;
		}
		return null;
		
	}
	
	/**Returns the edge between two vertices, if exists or null if does not exist*/
	public Edge getEdge(Vertex u, Vertex v) {
		if (u.getEdges().isEmpty()) return null;
		else {
			for (Edge e : u.getEdges()) {//traverse all edges of u
				if (((e.getEndpoints()[0] == v) && (e.getEndpoints()[1] == u)) 
					|| ((e.getEndpoints()[1] == v) && (e.getEndpoints()[0] == u)))
					return e;
			}
		}
		return null;
	}
	
	/**Sorts incident edges for vertex u (using insertion sort)*/
	public void sortEdges(Vertex u) {
		ArrayList<Edge> edges = u.getEdges();
		ArrayList<Edge> temp = new ArrayList<>();
		for (int i = 0; i < edges.size(); i++) // make a copy of edges
			temp.set(i, edges.get(i));
		
		//sort temp according to the value of edge
		//uses insertion sort (efficient, since the size is small)
		for (int i = 1; i < temp.size(); i++)
			for (int j = i - 1; j >= 0; j--) {
				if (edges.get(j).getValue() < edges.get(i).getValue()) {
					//swap the elements
					edges.add(i, edges.get(j));
					edges.remove(j + 1);
			}
		}
	}
	
	
	/** Returns the vertex opposite to vertex v on edge e */
	public Vertex opposite(Vertex v, Edge e) 
										throws IllegalArgumentException {
		Vertex[] endpoints = e.getEndpoints();
		if (endpoints[0] == v) return endpoints[1];
		else if (endpoints[1] == v) return endpoints[0];
		else throw new IllegalArgumentException ("v is not incident to this edge");
	}
	
	/**Debugging only*/
	public String displayVertices() {
		StringBuilder sb = new StringBuilder();
		for (Vertex v : vertices)
			if (v != null)
				sb.append(v.getName() + " ");
		return "" + sb;
	}
}
