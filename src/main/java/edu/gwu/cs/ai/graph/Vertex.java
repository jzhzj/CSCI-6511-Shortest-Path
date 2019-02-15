package edu.gwu.cs.ai.graph;

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
    /**
     * The cost from the source vertex to this vertex
     */
    private int pathCost;
    private int gValue;
    private int fValue;
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

    public void setPathCost(int pathCost) {
        this.pathCost = pathCost;
    }

    public int getfValue() {
        return fValue;
    }

    public void setfValue(int fValue) {
        this.fValue = fValue;
    }

    public int getgValue() {
        return gValue;
    }

    public void setgValue(int gValue) {
        this.gValue = gValue;
    }

    public int getVertexNum() {
        return vertexNum;
    }

    public int getPathCost() {
        return pathCost;
    }

    public int getSquareX() {
        return squareX;
    }

    public int getSquareY() {
        return squareY;
    }

    public static void clearVertices() {
        vertices = new ArrayList<>();
    }

    public static Vertex getVertex(int nodeNum) {
        return vertices.get(nodeNum);
    }
}
