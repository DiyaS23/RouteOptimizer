package com.delivery.route_optimizer.algorithm;

import java.util.*;

public class DijkstraAlgorithm {

    public List<GraphNode> shortestPath(Graph graph, GraphNode source, GraphNode target) {

        Map<GraphNode, Double> distances = new HashMap<>();
        Map<GraphNode, GraphNode> previous = new HashMap<>();
        PriorityQueue<GraphNode> pq =
                new PriorityQueue<>(Comparator.comparing(distances::get));

        for (GraphNode node : graph.getNodes()) {
            distances.put(node, Double.MAX_VALUE);
        }

        distances.put(source, 0.0);
        pq.add(source);

        while (!pq.isEmpty()) {
            GraphNode current = pq.poll();

            if (current.equals(target)) break;

            for (GraphEdge edge : graph.getEdges(current)) {
                double newDist = distances.get(current) + edge.getWeight();

                if (newDist < distances.get(edge.getTarget())) {
                    distances.put(edge.getTarget(), newDist);
                    previous.put(edge.getTarget(), current);
                    pq.add(edge.getTarget());
                }
            }
        }

        return buildPath(previous, source, target);
    }

    private List<GraphNode> buildPath(
            Map<GraphNode, GraphNode> previous,
            GraphNode source,
            GraphNode target) {

        List<GraphNode> path = new LinkedList<>();
        GraphNode current = target;

        while (current != null) {
            path.add(0, current);
            current = previous.get(current);
        }

        if (!path.get(0).equals(source)) {
            return List.of(); // no path
        }

        return path;
    }
}
