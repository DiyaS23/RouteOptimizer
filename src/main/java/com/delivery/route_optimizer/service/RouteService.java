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

import java.util.*;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final NodeRepository nodeRepository;
    private final EdgeRepository edgeRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final RouteMetrics metrics;


    public List<GraphNode> calculateShortestPath(String sourceCode, String targetCode) {
        String cacheKey = "route:" + sourceCode + ":" + targetCode;

        String cached = redisTemplate.opsForValue().get(cacheKey);
        if (cached != null) {
            metrics.cacheHits.increment();
            return Arrays.stream(cached.split(","))
                    .map(code -> new GraphNode(null, code))
                    .toList();
        }
        else {
            metrics.cacheMisses.increment();
        }
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
        List<GraphNode> path = dijkstra.shortestPath(
                graph,
                graphNodeMap.get(sourceNode.getId()),
                graphNodeMap.get(targetNode.getId())
        );

        String value = path.stream()
                .map(GraphNode::getCode)
                .reduce((a, b) -> a + "," + b)
                .orElse("");

        redisTemplate.opsForValue()
                .set(cacheKey, value, java.time.Duration.ofMinutes(5));

        return path;
    }
}

