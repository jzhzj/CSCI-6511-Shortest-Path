package algorithm.ucs;

/**
 * Node for MyPriorityQueue storing vertexNum and the weight of the edge between this vertex and its parent node.
 *
 * @author qijiuzhi
 */
public class QueueNode implements Comparable<QueueNode> {
    private int vertex;
    private int weight;

    QueueNode(int vertex, int weight) {
        this.vertex = vertex;
        this.weight = weight;
    }

    int getVertex() {
        return vertex;
    }

    int getWeight() {
        return weight;
    }

    @Override
    public int compareTo(QueueNode o) {
        return this.getWeight() - o.getWeight();
    }
}
