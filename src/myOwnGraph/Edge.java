/** MET CS 526
 * Term Project
 * Edge class
 * by Iryna Chervachidze
 * April 24, 2020
 */

package myOwnGraph;

public class Edge {

	private int value; // integer value of the edge
	private Vertex[] endPoints; // endpoints of the edge
	
	//Constructor
	public Edge() {
		endPoints = new Vertex[2];
	}
	
	public Edge(int value) {
		this.value = value;
		endPoints = new Vertex[2];
		}
	
	//getters
	public int getValue() {return value;}
	public Vertex[] getEndpoints() {return endPoints;}
	
	//setter
	public void setEndpoints(Vertex u, Vertex v) {
		endPoints[0] = u;
		endPoints[1] = v;
	}
}
