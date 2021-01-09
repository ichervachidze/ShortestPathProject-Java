package myOwnGraph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

import org.junit.Before;
import org.junit.Test;

public class GraphTest {
	
    Edge edge ;
	
	Vertex vertexU;
	
	Vertex vertexV;
	
	Graph graph;
	
	@Before
	public void init(){
		
	edge = new Edge();
	
	vertexU = new Vertex("u".charAt(0));
	
	vertexV = new Vertex("v".charAt(0));
	
	graph = new Graph();
	}

	
	@Test
	public void insertEdgeTest() {
		
		assertEquals(edge,graph.insertEdge(edge, vertexU, vertexV));
		
	}
	
	@Test
	public void insertEdgeExceptionTest(){
		
		graph.insertEdge(edge, vertexU, vertexV);
			
		assertThrows(IllegalArgumentException.class,() ->graph.insertEdge(edge, vertexU, vertexV));
		
	}
	
	@Test
	public void getEdgeTest(){
			
		graph.insertEdge(edge, vertexU, vertexV);
		
		assertEquals(edge, graph.getEdge(vertexU, vertexV));
		
		
	}
	
	@Test
	public void getEdgeNullTest(){
		
		Vertex vertexA = new Vertex("a".charAt(0));
		
		Vertex vertexB = new Vertex("b".charAt(0));
		
		Edge edge = new Edge();
		
		Graph graph = new Graph();
		
		graph.insertEdge(edge, vertexA, vertexB);
			
		assertNull(graph.getEdge(vertexU, vertexV));
		
	}
	

}
