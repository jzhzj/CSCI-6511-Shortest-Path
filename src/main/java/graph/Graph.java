package graph;

public interface Graph {
    int getVertexNum();

    int getEdgeNum();

    void addEdge(int v, int w, int weight);

    Iterable<Edge> adj(int vertex);

    int getWeight(int v, int w);
}
