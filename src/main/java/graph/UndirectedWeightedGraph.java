package graph;

import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of Graph interface.
 *
 * @author qijiuzhi
 */
public class UndirectedWeightedGraph implements Graph {
    private final int vertexNum;
    private int edgeNum;
    private Set<Edge>[] adj;

    public UndirectedWeightedGraph(int vertexNum) {
        this.vertexNum = vertexNum;
        this.edgeNum = 0;
        adj = (Set<Edge>[]) new Set[vertexNum];
        for (int v = 0; v < vertexNum; v++) {
            adj[v] = new HashSet<>();
        }
    }

    @Override
    public int getVertexNum() {
        return vertexNum;
    }

    @Override
    public int getEdgeNum() {
        return edgeNum;
    }

    @Override
    public void addEdge(int v, int w, int weight) {
        adj[v].add(new Edge(v, w, weight));
        adj[w].add(new Edge(w, v, weight));
        edgeNum++;
    }

    @Override
    public Iterable<Edge> adj(int vertex) {
        return adj[vertex];
    }

    @Override
    public int getWeight(int v, int w) {
        for (Edge e : adj(v)) {
            if (e.getTarget() == w) {
                return e.getWeight();
            }
        }
        return -1;
    }
}
