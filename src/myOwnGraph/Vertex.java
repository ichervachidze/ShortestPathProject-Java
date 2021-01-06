/** MET CS 526
 * Term Project
 * Vertex class
 * by Iryna Chervachidze
 * April 24, 2020
 */
 

package myOwnGraph;

import java.util.ArrayList;

public class Vertex {
	private char name;
	private int dd; //direct distance to Z
	private ArrayList<Edge> edges; //list of incident edges
	private boolean deadEnd;
	private boolean visited;
	
	//Constructors
	public Vertex(char name) {this(name, 0);}
	
	public Vertex(char name, int dd) {
		this.name = name;
		this.dd = dd;
		edges = new ArrayList<>(); //empty list of incident edges
		deadEnd = false;//temporarily set to false
		visited = false;//temporarily set to false
	}
	
	//getters
	public char getName() {return name;}
	public int getDD() {return dd;}
	public ArrayList<Edge> getEdges() {return edges;}
	public boolean isDeadEnd() {return deadEnd;}
	public boolean isVisited() {return visited;}
	
	//setters
	public void setDD(int dd) {
		this.dd = dd; //set direct distance
	}
	 public void setDeadEnd(boolean deadEnd) {
		 this.deadEnd = deadEnd;
	 }
	 public void setVisited(boolean visited) {
		 this.visited = visited;
	 }
	 
	 /**Returns distance between this vertex and vertex v.
	  * 
	  * @param v: opposite vertex v
	  */
	 public int getDistanceTo(Vertex v) {
		 int distance = 0;//temporarily set to zero
		 for (Edge edge : edges) {
			 //find the edge that connects to vertex v
			 if (edge.getEndpoints()[0] == v || edge.getEndpoints()[1] == v)
				distance = edge.getValue();		 
		 }
		 return distance;
	 }
	
	
	
	
	
	
	
	
	

}
