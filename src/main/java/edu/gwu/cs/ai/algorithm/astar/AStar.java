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

    public AStar(Graph graph, int sourceVertex, int targetVertex, int maxSize) {
        marked = new boolean[graph.getVertexNum()];
        edgeTo = new int[graph.getEdgeNum()];
        this.sourceVertex = sourceVertex;
        this.targetVertex = targetVertex;
        astar(graph, maxSize);
    }

    private void astar(Graph graph, int maxSize) {
        Vertex.getVertex(sourceVertex).setgValue(0);
        Vertex.getVertex(sourceVertex).setfValue((int) heuristic(sourceVertex, targetVertex));
        PriorityQueue<Vertex> queue = new PriorityQueue<>(maxSize, Comparator.comparingInt(v -> v.getfValue()));
        queue.add(Vertex.getVertex(sourceVertex));
        boolean found = false;

        while (!queue.isEmpty() && !found) {
            Vertex curV = queue.poll();
            marked[curV.getVertexNum()] = true;
            if (curV.getVertexNum() == targetVertex) {
                found = true;
            }

            for (Edge e : graph.adj(curV.getVertexNum())) {


                int neighbor = e.getTarget();
                if (marked[neighbor]) {
                    continue;
                }
                Vertex neighborV = Vertex.getVertex(neighbor);
                int tentativeGValue = curV.getgValue() + e.getWeight();
                int estimatedFScore = tentativeGValue + (int) heuristic(neighbor, targetVertex);
                if (!marked[neighbor] && !queue.contains(neighborV)) {
                    edgeTo[neighbor] = curV.getVertexNum();
                    neighborV.setfValue(estimatedFScore);
                    neighborV.setgValue(tentativeGValue);
                    queue.add(neighborV);
                } else if (queue.contains(neighborV) && estimatedFScore < neighborV.getfValue()) {
                    edgeTo[neighbor] = curV.getVertexNum();
                    neighborV.setgValue(tentativeGValue);
                    neighborV.setfValue(estimatedFScore);
                    queue.remove(neighborV);
                    queue.add(neighborV);
                }
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
