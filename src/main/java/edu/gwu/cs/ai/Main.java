package edu.gwu.cs.ai;

import edu.gwu.cs.ai.algorithm.astar.AStar;
import edu.gwu.cs.ai.algorithm.ucs.UniformCostSearch;
import edu.gwu.cs.ai.graph.Graph;
import edu.gwu.cs.ai.graph.UndirectedWeightedGraph;
import edu.gwu.cs.ai.graph.Vertex;

import java.io.*;
import java.util.Stack;

/**
 * The entrance for the entire program.
 *
 * @author qijiuzhi
 */
public class Main {
    private static Graph graph;

    public static void main(String[] args) throws IOException {

        // check the validation of the input
        if (!checkArgs(args)) {
            showUsage();
            return;
        }

        // get the input stream of the source file
        File file = new File(args[0]);
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

        // generate the graph
        int vertexNum = getVertexNum(file);
        generateGraph(vertexNum, in);

        int sourceVertex = Integer.parseInt(args[1]);
        int targetVertex = Integer.parseInt(args[2]);

        // execute the uninformed search
        uninformed(sourceVertex, targetVertex, vertexNum);

        // execute the informed search
        informed(sourceVertex, targetVertex, vertexNum);
        Vertex.clearVertices();
        in.close();
    }

    /**
     * checks if the args are valid
     */
    private static boolean checkArgs(String[] args) {
        return args.length == 3 && !args[1].equals(args[2]);
    }

    /**
     * get the number of vertices from the file name
     */
    private static int getVertexNum(File file) {
        String fileName = file.getName();
        String substring = fileName.substring(5, fileName.indexOf("_"));
        return Integer.parseInt(substring);
    }

    /**
     * generate the graph, vertices, and edges from the input file
     */
    private static void generateGraph(int vertexNum, BufferedReader in) throws IOException {

        // a flag indicating whether current line is a vertex or an edge
        boolean isVertex = true;
        // generate new graph
        graph = new UndirectedWeightedGraph(vertexNum);

        // read from txt file and generate vertices and edges
        for (String line = in.readLine(); line != null; line = in.readLine()) {
            // skip the line starts with "#" and "Vertices"
            if (line.startsWith("#") || "Vertices".equalsIgnoreCase(line)) {
                continue;
            }
            // skip the line starts with "edges" and set the isVertex flag false
            if ("Edges".equalsIgnoreCase(line)) {
                isVertex = false;
                continue;
            }
            // split the line
            String[] elements = line.split(",");

            // generate vertices and edges
            if (isVertex) {
                Vertex.addNode(Integer.parseInt(elements[0]), Integer.parseInt(elements[1]), Integer.parseInt(elements[2]));
            } else {
                graph.addEdge(Integer.parseInt(elements[0]), Integer.parseInt(elements[1]), Integer.parseInt(elements[2]));
            }
        }
    }

    /**
     * uses uninformed search exploring the shortest path from the source vertex to the target vertex.
     */
    private static void uninformed(int sourceVertex, int targetVertex, int vertexNum) {
        long start = System.currentTimeMillis();
        UniformCostSearch ucs = new UniformCostSearch(graph, sourceVertex, targetVertex, vertexNum);
        if (ucs.hasPathTo(targetVertex)) {
            System.out.println();
            showPath(graph, ucs.pathTo(targetVertex), false);
        } else {
            System.out.println();
            System.out.println("No path to vertex " + targetVertex);
        }
        long end = System.currentTimeMillis();
        System.out.println("Time consuming: " + (end - start) + " ms");
    }

    /**
     * uses informed search exploring the shortest path from the source vertex to the target vertex.
     */
    private static void informed(int sourceVertex, int targetVertex, int vertexNum) {
        long start = System.currentTimeMillis();
        AStar aStar = new AStar(graph, sourceVertex, targetVertex, vertexNum);
        if (aStar.hasPathTo(targetVertex)) {
            System.out.println();
            showPath(graph, aStar.pathTo(targetVertex), true);
        } else {
            System.out.println();
            System.out.println("No path to vertex " + targetVertex);
        }
        long end = System.currentTimeMillis();
        System.out.println("Time consuming: " + (end - start) + " ms");
    }

    /**
     * print the path to the standard output
     */
    private static void showPath(Graph graph, Iterable<Integer> iterable, boolean informed) {
        if (informed) {
            System.out.println("Result for informed search: ");
        } else {
            System.out.println("Result for uninformed search: ");
        }
        Stack<Integer> stack = (Stack<Integer>) iterable;
        int sum = 0;
        int lastV;
        int curV = stack.pop();
        System.out.print(curV + " -> ");
        while (true) {
            lastV = curV;
            curV = stack.pop();
            int weight = graph.getWeight(lastV, curV);
            if (weight == -1) {
                System.out.println("can not find the edge from vertex " + lastV + " to vertex " + curV);
            }
            sum += weight;
            System.out.print(curV);
            if (stack.empty()) {
                break;
            }
            System.out.print(" -> ");
        }
        System.out.println();
        System.out.println("The cost is " + sum);
    }

    /**
     * show the usage on the standard output
     */
    private static void showUsage() {
        String usage = "Usage:" + System.lineSeparator() +
                "java -jar ShortestPath.jar <input file> <source vertex> <target vertex>" + System.lineSeparator() +
                "Example:" + System.lineSeparator() +
                "java -jar ShortestPath.jar ~/Downloads/graph.txt 123 666" + System.lineSeparator() +
                "Source vertex should not be the same with target vertex.";
        System.out.println(usage);
    }
}
