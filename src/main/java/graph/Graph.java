package graph;

/**
 * The interface for an undirected weighted graph
 *
 * @author qijiuzhi
 */
public interface Graph {
    /**
     * Get the number of vertices
     *
     * @return the number of vertices
     */
    int getVertexNum();

    /**
     * Get the number of edges
     *
     * @return the number edges
     */
    int getEdgeNum();

    /**
     * Add an edge to this graph
     *
     * @param v the first vertex
     * @param w the second vertex
     */
    void addEdge(int v, int w, int weight);

    /**
     * Get the adjacent edges set of a certain vertex
     *
     * @param vertex the vertex whose set of edges you want to get
     * @return a Iterable object representing the set of edges
     */
    Iterable<Edge> adj(int vertex);

    /**
     * Get the weight of the edge from v to w
     *
     * @param v first vertex
     * @param w second vertex
     * @return the weight of the edge from v to w
     */
    int getWeight(int v, int w);
}
