# ShortestPathProject-Java
**Finding the shortest path in an undirected graph using heuristic algorithms**<br>
**(This project does not use Dijkstra’s shortest path algorithm)**<br>
Idea by Dr.Jay Lee, implementation in Java by Iryna Chervachidze

## Brief Description
This program uses several data structures and relies heavily on OOP principles. Data structures used: Java arrays, ArrayList, Stack, and a custom-created adjacency list graph (see below for discussion of structures used).
The graph data is provided in a .txt file in a square matrix form, where each column/row name represents a vertex (node) on the graph and the value at the intersection of the row/column represents an edge (distance) between the vertices defined by the row name and column name. The user inputs the start node (such as A or any other available node), and the program recursively finds the shortest path to Z node using two algorithms. It prints out the total traversed path, the shortest path (those may differ depending on the dead ends encountered) such as A => B => K => Z, and the total distance travelled for each algorithm. The algorithms are programmed in such a way that if they reach a dead end, they retrace the path back until they find an alternative node to follow.

## How to Run
To run the program, go to package MyOwnGraph, class Project.java. Main method runs the program. Input files are found in the same main method of Project.java. The program needs two .txt files: a file containing direct distances from any vertex to Z; and a file containing a matrix of edges (distances) between all vertices. Package “resources” contains other such files.

## Major Data Structures Used
My graph uses an adjacency list structure. All vertices are stored in a Java array within the graph. Graph class has an instance variable vertices, which is a reference to the array of vertices. Each vertex is an instance of its own class Vertex (see below). Array’s capacity is 26, number of letters in English alphabet. My graph maintains a field size, which is the actual number of all vertices in the graph. Size of the graph depends on the input file I am using, but cannot be greater than 26. Many methods implemented in the Graph class were inspired by the book’s implementation of a graph on pp.627-629 (Data Structures and Algorithms by Michael Goodrich, Roberto Tamassa, and Michael H.Goldwasser).
Each vertex is an object of a Vertex class. When loading data from the file, vertex names (characters) are uploaded and recorded in the field name of the instance; direct distance is recorded in the dd field of the instance. Each instance contains a reference to a list of all incident edges to that vertex, implemented by Java’s ArrayList. Number of incident edges depends on the input. When loading data from the file, all incident edges are recorded in the edges field.
Each edge is an object of an Edge class and contains a reference to two endpoints, stored in a simple array endpoints. When loading data from the file, each instance records the weight of the edge in the field value.

## Graph Used
Note: this graph was created by Iryna Chervachidze for the pusposes of testing this project
<br>
<img src=https://ichervachidze.github.io/images/graph.png>
