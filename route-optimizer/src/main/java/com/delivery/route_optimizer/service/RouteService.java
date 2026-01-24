package com.delivery.route_optimizer.service;

import com.delivery.route_optimizer.algorithm.*;
import com.delivery.route_optimizer.metrics.RouteMetrics;
import com.delivery.route_optimizer.model.Edge;
import com.delivery.route_optimizer.model.Node;
import com.delivery.route_optimizer.repository.EdgeRepository;
import com.delivery.route_optimizer.repository.NodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final NodeRepository nodeRepository;
    private final EdgeRepository edgeRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final RouteMetrics metrics;
    private final DijkstraAlgorithm dijkstraAlgorithm;

    public RouteResult calculateRoute(String sourceCode, String targetCode) {

        metrics.routeRequests.increment();
        String cacheKey = "route:" + sourceCode + ":" + targetCode;

        // ---------- CACHE ----------
        String cached = redisTemplate.opsForValue().get(cacheKey);
        if (cached != null) {
            metrics.cacheHits.increment();

            List<String> nodes = List.of(cached.split(","));
            double distance = estimateDistance(nodes);

            return new RouteResult(nodes, distance, true);
        }

        metrics.cacheMisses.increment();

        // ---------- DB ----------
        Node sourceNode = nodeRepository.findByCode(sourceCode)
                .orElseThrow(() -> new RuntimeException("Source node not found"));

        Node targetNode = nodeRepository.findByCode(targetCode)
                .orElseThrow(() -> new RuntimeException("Target node not found"));

        List<Node> dbNodes = nodeRepository.findAll();
        List<Edge> dbEdges = edgeRepository.findAll();

        // ---------- GRAPH BUILD ----------
        Map<Long, GraphNode> graphNodeMap = new HashMap<>();
        for (Node n : dbNodes) {
            graphNodeMap.put(n.getId(), new GraphNode(n.getId(), n.getCode()));
        }

        Graph graph = new Graph();
        graphNodeMap.values().forEach(graph::addNode);

        Map<String, Double> edgeWeightMap = new HashMap<>();

        for (Edge e : dbEdges) {
            GraphNode from = graphNodeMap.get(e.getFrom().getId());
            GraphNode to = graphNodeMap.get(e.getTo().getId());

            double weight = e.getDistance() * e.getTrafficFactor();
            graph.addEdge(from, to, weight);

            edgeWeightMap.put(from.getCode() + "->" + to.getCode(), weight);
        }
        System.out.println("Graph nodes: " + graphNodeMap.keySet().size());
        System.out.println("Graph edges: " + dbEdges.size());
        System.out.println("Source node = " + graphNodeMap.get(sourceNode.getId()));
        System.out.println("Target node = " + graphNodeMap.get(targetNode.getId()));
        System.out.println("Graph = " + graph);

        // ---------- DIJKSTRA ----------
        List<GraphNode> path = dijkstraAlgorithm.shortestPath(
                graph,
                graphNodeMap.get(sourceNode.getId()),
                graphNodeMap.get(targetNode.getId())
        );

        if (path.isEmpty()) {
            throw new RuntimeException("No route found");
        }

        // ---------- RESULT ----------
        double totalDistance = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            String key = path.get(i).getCode() + "->" + path.get(i + 1).getCode();
            totalDistance += edgeWeightMap.getOrDefault(key, 0.0);
        }

        List<String> nodes = path.stream()
                .map(GraphNode::getCode)
                .toList();

        redisTemplate.opsForValue().set(
                cacheKey,
                String.join(",", nodes),
                Duration.ofMinutes(5)
        );

        return new RouteResult(nodes, totalDistance, false);
    }

    private double estimateDistance(List<String> nodes) {
        return nodes.size() > 1 ? nodes.size() - 1 : 0;
    }
}
