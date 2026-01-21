package com.delivery.route_optimizer.service;


import com.delivery.route_optimizer.algorithm.*;
import com.delivery.route_optimizer.model.Edge;
import com.delivery.route_optimizer.model.Node;
import com.delivery.route_optimizer.repository.EdgeRepository;
import com.delivery.route_optimizer.repository.NodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final NodeRepository nodeRepository;
    private final EdgeRepository edgeRepository;

    public List<GraphNode> calculateShortestPath(String sourceCode, String targetCode) {

        Node sourceNode = nodeRepository.findByCode(sourceCode)
                .orElseThrow(() -> new RuntimeException("Source node not found"));

        Node targetNode = nodeRepository.findByCode(targetCode)
                .orElseThrow(() -> new RuntimeException("Target node not found"));

        List<Node> dbNodes = nodeRepository.findAll();
        List<Edge> dbEdges = edgeRepository.findAll();

        Map<Long, GraphNode> graphNodeMap = new HashMap<>();
        for (Node n : dbNodes) {
            graphNodeMap.put(
                    n.getId(),
                    new GraphNode(n.getId(), n.getCode())
            );
        }

        Graph graph = new Graph();
        graphNodeMap.values().forEach(graph::addNode);

        for (Edge e : dbEdges) {
            GraphNode from = graphNodeMap.get(e.getFrom().getId());
            GraphNode to = graphNodeMap.get(e.getTo().getId());

            double weight = e.getDistance() * e.getTrafficFactor();
            graph.addEdge(from, to, weight);
        }

        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm();
        return dijkstra.shortestPath(
                graph,
                graphNodeMap.get(sourceNode.getId()),
                graphNodeMap.get(targetNode.getId())
        );
    }
}

