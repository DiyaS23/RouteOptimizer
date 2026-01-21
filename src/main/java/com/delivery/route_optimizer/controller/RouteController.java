package com.delivery.route_optimizer.controller;

import com.delivery.route_optimizer.algorithm.GraphNode;
import com.delivery.route_optimizer.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @PostMapping("/calculate")
    public List<String> calculateRoute(
            @RequestParam String source,
            @RequestParam String target) {

        List<GraphNode> path =
                routeService.calculateShortestPath(source, target);

        // Return only node codes (clean API)
        return path.stream()
                .map(GraphNode::getCode)
                .toList();
    }
}

