package myOwnGraph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class GraphTest {

	
	@Test
	public void insertEdgeTest() {
		
		Edge edge = new Edge();
		
		Vertex vertexU = new Vertex("u".charAt(0));
		
		Vertex vertexV = new Vertex("v".charAt(0));
		
		Graph graph = new Graph();
		
		assertEquals(edge,graph.insertEdge(edge, vertexU, vertexV));
		
	}
	
	@Test
	public void insertEdgeExceptionTest(){
		
		Edge edge = new Edge();
		
		Vertex vertexU = new Vertex("u".charAt(0));
		
		Vertex vertexV = new Vertex("v".charAt(0));
		
		Graph graph = new Graph();
		
		graph.insertEdge(edge, vertexU, vertexV);
		
		assertThrows(IllegalArgumentException.class,() ->graph.insertEdge(edge, vertexU, vertexV));
		
		
	}
	
	@Test
	public void getEdgeTest(){
		
		Edge edge = new Edge();
		
		Vertex vertexU = new Vertex("u".charAt(0));
		
		Vertex vertexV = new Vertex("v".charAt(0));
		
		Graph graph = new Graph();
		
		graph.insertEdge(edge, vertexU, vertexV);
		
		assertEquals(edge, graph.getEdge(vertexU, vertexV));
		
		
	}
	
	@Test
	public void getEdgeNullTest(){
		
		Vertex vertexU = new Vertex("u".charAt(0));
		
		Vertex vertexV = new Vertex("v".charAt(0));
		
		Vertex vertexA = new Vertex("a".charAt(0));
		
		Vertex vertexB = new Vertex("b".charAt(0));
		
		Edge edge = new Edge();
		
		Graph graph = new Graph();
		
		graph.insertEdge(edge, vertexA, vertexB);
			
		assertNull(graph.getEdge(vertexU, vertexV));
		
	}
	

}
