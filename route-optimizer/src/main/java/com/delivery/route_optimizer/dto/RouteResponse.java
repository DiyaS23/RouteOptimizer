package com.delivery.route_optimizer.dto;

import lombok.*;
import java.time.Instant;
import java.util.List;

@Getter
@Builder
public class RouteResponse {
    private String routeId;
    private Long riderId;
    private String source;
    private String target;
    private List<String> nodes;
    private double totalDistance;
    private boolean cacheHit;
    private Instant computedAt;
}
