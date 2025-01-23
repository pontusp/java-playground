package se.pontus.play.jgrapht;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.nio.json.JSONExporter;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;


class JGraphTest {

    private record Node(String id, String label) {

        @Override
        public String toString() {
            return label;
        }

    }

    @Test
    void simpleGraphTest() {
        Graph<Node, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);

        Node n1 = new Node("node1", "Node A");
        Node n2 = new Node("node2", "Node B");
        Node n3 = new Node("node3", "Node C");
        Node n4 = new Node("node4", "Node D");

        graph.addVertex(n1);
        graph.addVertex(n2);
        graph.addVertex(n3);
        graph.addVertex(n4);

        graph.addEdge(n1, n2);
        graph.addEdge(n2, n3);
        graph.addEdge(n2, n4);
        graph.addEdge(n3, n4);

        // Print graph in some kind of text representation
        System.out.println(graph);

        // Export graph to JSON
        JSONExporter<Node, DefaultEdge> exporter = new JSONExporter<>(Node::id);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        exporter.exportGraph(graph, out);
        String jsonString = new String(out.toByteArray(), StandardCharsets.UTF_8);

        JSONObject json = new JSONObject(jsonString);
        System.out.println(json.toString(2));

        assertEquals(4, json.getJSONArray("nodes").length());
        assertEquals(4, json.getJSONArray("edges").length());
    }

}
