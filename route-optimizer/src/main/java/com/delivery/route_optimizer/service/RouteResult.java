package com.delivery.route_optimizer.service;

import lombok.*;
import java.util.List;

@Getter
@AllArgsConstructor
public class RouteResult {
    private List<String> nodes;
    private double totalDistance;
    private boolean cacheHit;
}

