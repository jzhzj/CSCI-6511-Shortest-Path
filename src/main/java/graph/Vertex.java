package graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents vertices in the graph.
 *
 * @author qijiuzhi
 */
public class Vertex {
    private int vertexNum;
    private int squareX;
    private int squareY;
    private static List<Vertex> vertices = new ArrayList<>();

    private Vertex(int vertexNum, int squareX, int squareY) {
        this.vertexNum = vertexNum;
        this.squareX = squareX;
        this.squareY = squareY;
    }

    public static void addNode(int nodeNum, int squareX, int squareY) {
        Vertex newVertex = new Vertex(nodeNum, squareX, squareY);
        if (vertices.size() == nodeNum) {
            vertices.add(newVertex);
        } else {
            System.out.println("Please input nodes in order!");
        }
    }

    public static int getSize() {
        return vertices.size();
    }

    public static Vertex getVertex(int nodeNum) {
        return vertices.get(nodeNum);
    }
}
