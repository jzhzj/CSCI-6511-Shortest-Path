package edu.gwu.cs.ai.algorithm.astar;

import edu.gwu.cs.ai.graph.Edge;
import edu.gwu.cs.ai.graph.Graph;
import edu.gwu.cs.ai.graph.Vertex;

import java.util.*;

/**
 * Using A* edu.gwu.cs.ai.algorithm to find the shortest path to the target vertex
 *
 * @author qijiuzhi
 */
public class AStar {
    private boolean[] marked;
    private int[] edgeTo;
    private final int sourceVertex;
    private final int targetVertex;
    /**
     * backward cost + heuristic
     */
    private int[] fValue;
    /**
     * backward cost
     */
    private int[] gValue;
    /**
     * the set storing vertices to be explored
     */
    private final List<Integer> fringe;


    public AStar(Graph graph, int sourceVertex, int targetVertex, int maxSize) {
        marked = new boolean[graph.getVertexNum()];
        edgeTo = new int[graph.getEdgeNum()];
        this.sourceVertex = sourceVertex;
        this.targetVertex = targetVertex;
        fringe = new ArrayList<>(maxSize);
        fringe.add(sourceVertex);
        gValue = new int[maxSize];
        gValue[sourceVertex] = 0;
        fValue = new int[maxSize];
        Arrays.fill(fValue, Integer.MAX_VALUE);
        fValue[sourceVertex] = (int) heuristic(sourceVertex, targetVertex);
        astar(graph);
    }

    private void astar(Graph graph) {

        while (!fringe.isEmpty()) {
            int current = fringe.get(0);
            marked[current] = true;
            if (current == targetVertex) {
                return;
            }
            fringe.remove(0);


            for (Edge e : graph.adj(current)) {
                int neighbor = e.getTarget();
                if (marked[neighbor]) {
                    continue;
                }
                int tentativeGValue = gValue[current] + e.getWeight();
                if (!fringe.contains(neighbor)) {
                    fringe.add(neighbor);
                } else if (tentativeGValue >= gValue[neighbor]) {
                    continue;
                }
                edgeTo[neighbor] = current;
                gValue[neighbor] = tentativeGValue;
                int estimatedFScore = gValue[neighbor] + (int) heuristic(neighbor, targetVertex);
                fValue[neighbor] = estimatedFScore;
                Collections.sort(fringe, Comparator.comparingInt(v -> fValue[v]));
            }
        }
    }

    private double heuristic(int vertex1, int vertex2) {
        Vertex v1 = Vertex.getVertex(vertex1);
        Vertex v2 = Vertex.getVertex(vertex2);

        // the difference between v1.squareX and v2.squareX excluding the squares where both vertices stand
        double x = Math.abs(v1.getSquareX() - v2.getSquareX()) - 1;
        // the difference between v1.squareY and v2.squareY excluding the squares where both vertices stand
        double y = Math.abs(v1.getSquareY() - v2.getSquareY()) - 1;

        if (x >= 0) {
            if (y >= 0) {
                return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) * 100;
            } else {
                return x * 100;
            }
        } else {
            if (y >= 0) {
                return y * 100;
            } else {
                return 0;
            }
        }
    }

    public boolean hasPathTo(int vertex) {
        return marked[vertex];
    }

    public Iterable<Integer> pathTo(int vertex) {
        if (!hasPathTo(vertex)) {
            return null;
        }
        Stack<Integer> path = new Stack<>();
        for (int x = vertex; x != sourceVertex; x = edgeTo[x]) {
            path.push(x);
        }
        path.push(sourceVertex);
        return path;
    }
}
