/** MET CS 526
 * Term Project
 * Driver Program
 * by Iryna Chervachidze
 * April 24, 2020
 * 
 */

package myOwnGraph;

import java.io.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Pattern;

public class Project {
	
	/**Main method, runs the program */
	public static void main(String[] args) {
		
		//    1. SPECIFY INPUT FILES AND LOAD DATA:
		// ===========================================
		
		Graph myGraph = new Graph();//create an empty graph
		File inputFile = new File("./src/resources/my_test_graph.txt");//graph input file
		File ddFile = new File("./src/resources/my_test_dd.txt");//direct distance file
		
		//read input data from a file into the graph
		try {
			readData(myGraph, inputFile);
		}
		catch (IOException e){
			System.out.println("File not found");
		}
		
		//read direct distance data from file into the graph
		try {
			readDirectDistance(myGraph, ddFile);
		}
		catch(IOException e) {
			System.out.println("File not found");
		}
	
		
		//get user input for the start node
		char start = getUserInput(myGraph);
		System.out.println("\nUser enters node " + start 
				+ " as the start node");
		
		//    2. RUN TWO ALGORITHMS AND DISPLAY RESULTS:
		// ================================================
		
		//run algorithm 1 for the given start node
		runAlgo1(myGraph, start);
		//reset all vertices to non-visited and non-deadends
		//this is done so that algorithm 2 can still use those vertices that 
		//have been visited while running algorithm 1
		resetVertices(myGraph);
		//run algorithm 2 for the given start node
		runAlgo2(myGraph, start);

	}
	
	//          UTILITY METHODS:
	//============================================
	
	/**Returns an index of a given character (node) in the nodeArray 
	 * (implementation borrowed from Prof.Lee)
	 * 
	 * @param nodeArray: array of nodes(characters)
	 * @param node: character to match to an index
	 * @return: index in nodeArray
	 */
	public static int nodeToIndex(char[] nodeArray, char node) {
		int index = 0;
		for (int i = 0; i < nodeArray.length; i++) {
			if (nodeArray[i] == node) {
				index = i;
				break;
			}
		}
		return index;
	}
	
	/**Read direct distance data from the file
	 *  
	 * @param myGraph: data is fed into this graph
	 * @param ddFile: direct distance input file
	 * @throws IOException: if file not found
	 */
	public static void readDirectDistance(Graph myGraph, File ddFile) throws IOException{
		Scanner fileInput = new Scanner(ddFile);
		
		while (fileInput.hasNext()) {
			//read one line at a time 
			String readline = fileInput.nextLine();
			
			//parse the line into tokens
			Scanner tokens = new Scanner(readline).useDelimiter("\\s");
			String strName = tokens.next();
			int dd = tokens.nextInt();//direct distance value
			char name = strName.charAt(0);//name of the vertex
			
			//set direct distance for the given vertex
			Vertex vertex = myGraph.findVertex(name);//find vertex with given name
			vertex.setDD(dd);//set direct distance value
			tokens.close();
			
		}
		fileInput.close();
	}
	
	/**Read input data from the file (implementation is borrowed from Prof.Lee, 
	 * with minor changes)
	 * 
	 * @param myGraph: data is fed into this graph
	 * @param inputFile: graph input file
	 * @throws IOException: if file is not found
	 */
	public static void readData(Graph myGraph, File inputFile) throws IOException{
		Scanner fileInput = new Scanner (inputFile);
		
		// read the top line in the input file and create nodeArray
		String[] aRow;
		aRow = fileInput.nextLine().trim().split("\\s+");
		int numNodes = aRow.length;
		
		char[] nodeArray = new char[numNodes];
		int j = 0;
		for (j = 0; j < aRow.length; j++) {
			nodeArray[j] = aRow[j].charAt(0);
		}
		
		//create vertices and insert them into the graph
		for (int i = 0; i < nodeArray.length; i++) {
			//create and insert a vertex into the graph
			Vertex vertex = new Vertex(nodeArray[i]);
			myGraph.insertVertex(vertex);
		}

		// read subsequent lines in the input file and create adjacency matrix
		int i = 0;
		while (fileInput.hasNext()){
			aRow = fileInput.nextLine().split("\\s+");
			Vertex u = myGraph.get(i); //vertex u
			for (j=1; j<aRow.length; j++) {
				Vertex v = myGraph.get(j - 1); // vertex v
			
				int e = Integer.parseInt(aRow[j]);
				//insert only non-zero values and 
				//only if the edge does not already exists
				if ((e != 0) &&  (myGraph.getEdge(u, v) == null)) {
					//create an edge
					Edge edge = new Edge(e);
					//insert the edge between vertex u and v
					try {
					myGraph.insertEdge(edge, u, v);
					}
					catch(IllegalArgumentException exception) {
						System.out.println(exception);
					}
				}
			}
			i++;
		}
		fileInput.close();
	}
	
	/**Debugging purposes only*/
	public static void displayMatrix(Graph myGraph) {
		System.out.println("\nPrint Adjacency matrix:");
		
		for (int i = 0; i < myGraph.numVertices(); i++) {
			
			for (int j = 0; j < myGraph.numVertices(); j++) {
				//try to find edge between u and v
				Edge edge = myGraph.getEdge(myGraph.get(i), myGraph.get(j));
				//if edge between u and v does not exists, print 0
				if (edge == null){
					System.out.printf("%3d", 0);
				}
				else {
				//if edge exists, print the value of it
				System.out.printf("%3d", edge.getValue());
			
				}
			}	
			System.out.printf("\n");
		}
	}
	
	/**Debugging purposes only*/
	public static void printAdjacencyList(Graph myGraph) {
		System.out.println("\n\nPrint Adjacency List:");
		
		for (int i = 0; i < myGraph.numVertices(); i++) {
			Vertex vertex = myGraph.get(i); //get a vertex from the graph
			//get all edges for the vertex
			ArrayList<Edge> edges = vertex.getEdges();
			
			//iterate through all the edges incident to the vertex
			for (int j = 0; j < edges.size(); j++) {
				//find the opposite vertex
				Vertex opposite = myGraph.opposite(vertex, (edges.get(j)));
				System.out.printf("%2c -->%2c\n", vertex.getName(), 
						opposite.getName());
			}	
		}
	}
	
	/**Helper method to run Algorithm 1
	 * 
	 * @param myGraph: graph
	 * @param k: starting point of traversal
	 */
	public static void runAlgo1(Graph myGraph, char k) {
		//find the vertex associated with point k
		Vertex start = myGraph.findVertex(k);
		Vertex finish = myGraph.findVertex('Z');//finish, as given
		Stack<Vertex> path = new Stack<>(); //stack to keep track of all visited vertices
		Stack<Vertex> shortestPath = new Stack<>();//stack to keep track of shortest path
		ArrayList<Vertex> deadEnds = new ArrayList<>();//list to keep track of deadends
		
		//push the starting vertex into the path and shortest path
		if (start != null) {
			start.setVisited(true);//mark this vertex as visited
			path.push(start);//add to path
			shortestPath.push(start);//add to shortest path
			algo1(myGraph, start, finish, path, shortestPath, deadEnds);
			printReport("Algorithm 1", start, path, shortestPath, deadEnds);
		}
	}
	
	/**Algorithm 1 method. Recursively compares direct distances of the 
	 * adjacent vertices and picks the vertex with the smallest direct distance.
	 * 
	 * @param myGraph: graph
	 * @param start: starting vertex
	 * @param finish: finish vertex, here hard coded to Z
	 * @param path: stack of all vertices visited, including dead-ends
	 * @param shortestPath: stack of all vertices on the shortest path
	 */
	public static void algo1(Graph myGraph, Vertex start, Vertex finish, 
			Stack <Vertex> path, Stack<Vertex> shortestPath, List<Vertex> deadEnds) {
		
		//base condition, reached the end
		if (start == finish) return;
		
		//find the next adjacent vertex with the shortest direct distance
		else {
			//get all incident edges for the start vertex
			ArrayList<Edge> edges = start.getEdges();
			//get a list of adjacent vertices that are
			//not deadends or have been visited
			ArrayList<Vertex> opposites = new ArrayList<>();//empty now
			for (int i = 0; i < edges.size(); i++) {
				Vertex adjacent = myGraph.opposite(start, edges.get(i));
				//only add those vertices that have not been visited or deadends
				if (!adjacent.isVisited() && !adjacent.isDeadEnd())
					opposites.add(myGraph.opposite(start, edges.get(i)));
			}
			
			//if all adjacent vertices have been visited or deadends
			//mark the current vertex as deadend and backtrack
			if (opposites.isEmpty()) {
				start.setDeadEnd(true);//mark this vertex as a deadend
				deadEnds.add(start);
				shortestPath.pop();//backtrack one step
				start = shortestPath.peek();//set start to the previously visited vertex
				path.push(start);//keep track of second visit in the path
				algo1(myGraph, start, finish, path, shortestPath, deadEnds);
			}
			else {
			//find a vertex with the shortest direct distance
			Vertex nextVertex = opposites.get(0);//temporarily assign to first adjacent vertex
			for (int i = 1; i < opposites.size(); i++)
				//compare direct distances
				if(opposites.get(i).getDD() < nextVertex.getDD()) {
					nextVertex = opposites.get(i);
				}
			
			//add the vertex with the shortest direct distance to the stack
			nextVertex.setVisited(true);//mark as visited
			path.push(nextVertex);
			shortestPath.push(nextVertex);
			//repeat the steps starting with this new vertex
			start = nextVertex;
			algo1(myGraph, start, finish, path, shortestPath, deadEnds);	
			}	
		}
	}
	
	/**Displays formatted report on path, shortest path and length
	 *  
	 * @param message: type of algorithm (string)
	 * @param start: start vertex
	 * @param path: path consisting of all vertices
	 * @param shortestPath: shortest path
	 */
	public static void printReport(String message, Vertex start, 
					Stack<Vertex> path, Stack<Vertex> shortestPath, ArrayList<Vertex> deadEnds) {
		System.out.println("\n\t" + message + ":");
		
		//display the whole path
		System.out.print("\n\t\tSequence of all nodes: ");
		for (Vertex v : path)
			System.out.print(v.getName() + (v.getName() == 'Z' ? "": " -->"));
		
		//display shortest path
		System.out.print("\n\t\tShortest path: ");
		for (Vertex v : shortestPath)
			System.out.print(v.getName() + (v.getName() == 'Z' ? "": " -->"));
		
		//calculate and display the shortest path distance
		System.out.print("\n\t\tShortest path length: ");
		int totalLength = 0;
		int pathLength = shortestPath.size();
		for (int i = 0; i < pathLength - 1; i++) {
			Vertex u = shortestPath.pop();
			Vertex v = shortestPath.peek();
			int distanceUtoV = u.getDistanceTo(v);
			totalLength += distanceUtoV;
		}
		System.out.print(totalLength);
		
		//display all found deadends
		System.out.print("\n\t\tDeadends found: ");
		if (deadEnds.isEmpty()) {System.out.println("None");}
		else {
			for (Vertex v : deadEnds) {
			System.out.print(v.getName() + ", ");
			}}
		
	}
	
	
	public static void runAlgo2(Graph myGraph, char k) {
		//find the vertex associated with point k
		Vertex start = myGraph.findVertex(k);
		Vertex finish = myGraph.findVertex('Z');//finish, hardcoded to 'Z'
		Stack<Vertex> path2 = new Stack<>(); //stack to keep track of all visited vertices
		Stack<Vertex> shortestPath2 = new Stack<>();//stack to keep track of shortest path
		ArrayList<Vertex> deadEnds = new ArrayList<>();//list to keep track of deadends
		
		//push the starting vertex into the path and shortest path
		if (start != null) {
			start.setVisited(true);//mark this vertex as visited
			path2.push(start);//add to path
			shortestPath2.push(start);//add to shortest path
			algo2(myGraph, start, finish, path2, shortestPath2, deadEnds);
			printReport("Algorithm 2", start, path2, shortestPath2, deadEnds);
		}
	}
	
	
	public static void algo2(Graph myGraph, Vertex start, Vertex finish, 
			Stack <Vertex> path, Stack<Vertex> shortestPath, ArrayList<Vertex> deadEnds) {
		//base condition, reached the end
		if (start == finish) return;
		
		//find the next adjacent vertex with the shortest direct distance
		else {
			//get all incident edges for the given vertex
			ArrayList<Edge> edges = start.getEdges();
			//get a list of adjacent vertices that are
			//not deadends nor have been visited
			ArrayList<Vertex> opposites = new ArrayList<>();
			for (int i = 0; i < edges.size(); i++) {
				Vertex adjacent = myGraph.opposite(start, edges.get(i));
				//only add those vertices that have not been visited or deadends
				if (!adjacent.isVisited() && !adjacent.isDeadEnd())
					opposites.add(myGraph.opposite(start, edges.get(i)));
			}
			
			//if all adjacent vertices have been visited or deadends
			//mark the current vertex as deadend and backtrack
			if (opposites.isEmpty()) {
				start.setDeadEnd(true);//mark this vertex as a deadend
				deadEnds.add(start);
				shortestPath.pop();//backtrack one step
				start = shortestPath.peek();//set start to the previously visited vertex
				path.push(start);//keep of second visit in the path
				algo2(myGraph, start, finish, path, shortestPath, deadEnds);
			}
			else {
				//find an adjacent vertex with the smallest sum of direct 
				//distance and distance between the given and adjacent vertex
				Vertex nextVertex = opposites.get(0);//temporarily assign to first adjacent vertex
				//calculate the sum of length of incident edges and direct distance
				int smallestSum = start.getDistanceTo(nextVertex) + nextVertex.getDD();
				for (int i = 1; i < opposites.size(); i++) {
					//compare sums of direct distance and length of incident edges
					int sumOfVertexI = opposites.get(i).getDD() 
							+ start.getDistanceTo(opposites.get(i));
					if (sumOfVertexI < smallestSum) {
						smallestSum = sumOfVertexI;
						nextVertex = opposites.get(i);
					}
				}
				
				//add the vertex with the shortest direct distance to the stack
				nextVertex.setVisited(true);//mark as visited
				path.push(nextVertex);
				shortestPath.push(nextVertex);
				//repeat the steps starting with this new vertex
				start = nextVertex;
				algo2(myGraph, start, finish, path, shortestPath, deadEnds);
			}
		}
	}
	
	/**Debugging only*/
	public static void displayDirectDistance(Graph myGraph) {
		System.out.println("\nDirect distances: ");
		for (Vertex v : myGraph.getVertices()) {
			System.out.println(v.getName() + " " + v.getDD());
		}
	}
	
	/**Resets all vertices as not visited and non-deadends
	 * 
	 * @param myGraph: graph we are using
	 */
	public static void resetVertices(Graph myGraph) {
		for (Vertex v: myGraph.getVertices()) {
			v.setVisited(false);
			//v.setDeadEnd(false);
		}
	}
	
	/**Prompts the user for input and validates it
	 * 
	 * @param myGraph: graph we are using
	 * @return: valid name of the start Vertex (an uppercase character)
	 */
	public static char getUserInput(Graph myGraph) {
		
		Scanner input = new Scanner(System.in);
		char startNode = '0';//temporarily set to zero literal
		if (acceptKey()) {
		
		//prompt the user until a valid input is entered
		while (startNode == '0') {
			System.out.println("Please enter a valid starting node: ");
			String userInput = input.next();
			startNode = userInput.charAt(0);
			startNode = validateInput(startNode, myGraph);
			}
		input.close();
		}
		return startNode;
	}
	
	/**Validates user input
	 * 
	 * @param startNode: start node that user entered (character)
	 * @param myGraph: graph we are using
	 * @return: valid name of the start node or '0'
	 */
	public static char validateInput(char startNode, Graph myGraph) {
		if (startNode < 'A' 
				|| (startNode > 'Z' && startNode < 'a') 
				|| startNode > 'z') {
			System.out.println(startNode + " is not a valid starting point");
			return '0';
		}
		//entered lowercase letter, convert to uppercase
		if (startNode > 'a' && startNode < 'z') 
			startNode -= 32;
		
		if (myGraph.findVertex(startNode) == null) {
			System.out.println("This node does not exists on the graph");
			return '0';
		}
		return startNode;
	}
	
	public static boolean acceptKey() {
		boolean passed = false;
		System.out.println("Please enter a key: ");
		Scanner keyInput = new Scanner(System.in);
		String key = keyInput.next();
		//keyInput.close();
		
		passed = Pattern.matches("[^a-z][0-9]{4}[a-z]*uo$", key);
		
		if (passed == false) {
			System.out.println("You do not have the right key. "
					+ "This solution was written by Iryna Chervachidze. "
					+ "Please ask for permission.");
			System.exit(1);
		}
		return passed;
//		return true;
	}
}