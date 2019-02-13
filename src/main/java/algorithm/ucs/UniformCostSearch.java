package algorithm.ucs;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * Using Uniform Cost Search to find the shortest path to the target vertex
 *
 * @author qijiuzhi
 */
public class UniformCostSearch {
    private boolean[] marked;
    private int[] edgeTo;
    private final int sourceVertex;
    private final int targetVertex;

    public UniformCostSearch(Graph graph, int sourceVertex, int targetVertex, int maxSize) {
        marked = new boolean[graph.getVertexNum()];
        edgeTo = new int[graph.getEdgeNum()];
        this.sourceVertex = sourceVertex;
        this.targetVertex = targetVertex;
        bfs(graph, maxSize);
    }

    /**
     * use breadth first search and priority queue to implement uniform cost search
     */
    private void bfs(Graph graph, int maxSize) {
        Vertex.getVertex(sourceVertex).setPathCost(0);
        PriorityQueue<Vertex> queue = new PriorityQueue<>(maxSize, Comparator.comparingInt(v -> v.getPathCost()));
        queue.add(Vertex.getVertex(sourceVertex));
        boolean found = false;

        do {
            int current = queue.poll().getVertexNum();
            marked[current] = true;

            if (current == targetVertex) {
                found = true;
            }

            for (Edge e : graph.adj(current)) {
                int neighbor = e.getTarget();
                int weight = e.getWeight();

                Vertex neighborV = Vertex.getVertex(neighbor);
                Vertex curV = Vertex.getVertex(current);
                if (!marked[neighbor] && !queue.contains(neighborV)) {
                    neighborV.setPathCost(curV.getPathCost() + weight);
                    edgeTo[neighbor] = current;
                    queue.add(neighborV);
                } else if (queue.contains(neighborV) && neighborV.getPathCost() > curV.getPathCost() + weight) {
                    edgeTo[neighbor] = current;
                    neighborV.setPathCost(curV.getPathCost() + weight);
                    queue.remove(neighborV);
                    queue.add(neighborV);
                }
            }
        } while (!queue.isEmpty() && !found);
    }

    /**
     * check if there is a path to a certain vertex
     */
    public boolean hasPathTo(int vertex) {
        return marked[vertex];
    }

    /**
     * put the path a certain vertex into a stack and return the stack
     */
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
