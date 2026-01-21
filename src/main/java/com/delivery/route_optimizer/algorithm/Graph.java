package com.delivery.route_optimizer.algorithm;

import java.util.*;

public class Graph {

    private final Map<GraphNode, List<GraphEdge>> adjList = new HashMap<>();

    public void addNode(GraphNode node) {
        adjList.putIfAbsent(node, new ArrayList<>());
    }

    public void addEdge(GraphNode from, GraphNode to, double weight) {
        adjList.get(from).add(new GraphEdge(to, weight));
    }

    public List<GraphEdge> getEdges(GraphNode node) {
        return adjList.getOrDefault(node, List.of());
    }

    public Set<GraphNode> getNodes() {
        return adjList.keySet();
    }
}

