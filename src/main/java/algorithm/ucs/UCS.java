package algorithm.ucs;

import graph.Edge;
import graph.Graph;

import java.util.Stack;

/**
 * Using Uniform Cost Search to find the shortest path to the target
 *
 * @author qijiuzhi
 */
public class UCS {
    private boolean[] marked;
    private int[] edgeTo;
    private final int sourceVertex;
    private final int targetVertex;
    private boolean targetEnqueued;

    public UCS(Graph graph, int sourceVertex, int targetVertex, int maxSize) {
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
        MyPriorityQueue<QueueNode> queue = new MyPriorityQueue<>(maxSize);
        marked[sourceVertex] = true;
        queue.insert(new QueueNode(sourceVertex, 0));
        while (!queue.isEmpty()) {
            int v = queue.poll().getVertex();
            if (targetEnqueued && v == targetVertex) {
                break;
            }

            for (Edge w : graph.adj(v)) {
                if (!marked[w.getTarget()]) {
                    edgeTo[w.getTarget()] = v;
                    marked[w.getTarget()] = true;
                    queue.insert(new QueueNode(w.getTarget(), w.getWeight()));
                    if (w.getTarget() == targetVertex) {
                        targetEnqueued = true;
                    }
                }
            }
        }
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
