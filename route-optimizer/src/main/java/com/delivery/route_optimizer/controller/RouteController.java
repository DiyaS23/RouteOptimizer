package com.delivery.route_optimizer.controller;

import com.delivery.route_optimizer.algorithm.GraphNode;
import com.delivery.route_optimizer.dto.RouteResponse;
import com.delivery.route_optimizer.service.RouteResult;
import com.delivery.route_optimizer.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @GetMapping("/calculate")
    public RouteResponse calculateRoute(
            @RequestParam String source,
            @RequestParam String target) {

        RouteResult result = routeService.calculateRoute(source, target);

        return RouteResponse.builder()
                .routeId(UUID.randomUUID().toString())
                .riderId(null)
                .source(source)
                .target(target)
                .nodes(result.getNodes())
                .totalDistance(result.getTotalDistance())
                .cacheHit(result.isCacheHit())
                .computedAt(Instant.now())
                .build();
    }
}
