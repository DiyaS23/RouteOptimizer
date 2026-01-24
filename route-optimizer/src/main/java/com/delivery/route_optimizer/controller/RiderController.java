package com.delivery.route_optimizer.controller;

import com.delivery.route_optimizer.dto.RouteResponse;
import com.delivery.route_optimizer.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/riders")
@RequiredArgsConstructor
public class RiderController {

    private final RiderRouteService riderRouteService;

    @GetMapping("/{id}/route")
    public RouteResponse getRoute(@PathVariable Long id) {

        RouteResult result = riderRouteService.getCurrentRoute(id);

        List<String> nodes = result.getNodes();

        return RouteResponse.builder()
                .routeId(UUID.randomUUID().toString())
                .riderId(id)
                .nodes(result.getNodes())
                .source(nodes.get(0))
                .target(nodes.get(nodes.size() - 1))
                .totalDistance(result.getTotalDistance())
                .cacheHit(result.isCacheHit())
                .computedAt(Instant.now())
                .build();
    }
}
