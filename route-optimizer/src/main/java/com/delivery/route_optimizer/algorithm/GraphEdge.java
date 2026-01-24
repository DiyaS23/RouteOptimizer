package com.delivery.route_optimizer.algorithm;

import lombok.*;

@Getter
@AllArgsConstructor
public class GraphEdge {
    private final GraphNode target;
    private final double weight;
}
