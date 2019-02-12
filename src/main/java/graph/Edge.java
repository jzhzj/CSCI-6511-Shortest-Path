package graph;

/**
 * Represent the edges between vertices.
 *
 * @author qijiuzhi
 */
public class Edge {
    private int source;
    private int target;
    private int weight;

    Edge(int source, int target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
    }

    public int getSource() {
        return source;
    }

    public int getTarget() {
        return target;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public int hashCode() {
        return new Integer(target).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Edge) {
            Edge anotherEdge = (Edge) obj;
            return ((this.source + this.target) == (anotherEdge.source + anotherEdge.target)) &&
                    (Math.abs(this.source - this.target) == Math.abs(anotherEdge.source - anotherEdge.target)) &&
                    this.weight == anotherEdge.weight;
        }
        return false;
    }
}
