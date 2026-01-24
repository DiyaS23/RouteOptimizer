package com.delivery.route_optimizer.service;

import com.delivery.route_optimizer.model.Edge;
import com.delivery.route_optimizer.repository.EdgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.delivery.route_optimizer.websocket.RouteUpdatePublisher;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class TrafficService {

    private final EdgeRepository edgeRepository;
    private final RouteUpdatePublisher routeUpdatePublisher;

    public void updateTraffic(Long edgeId, double factor) {
        Edge edge = edgeRepository.findById(edgeId)
                .orElseThrow(() -> new RuntimeException("Edge not found"));
        edge.setTrafficFactor(factor);
        edgeRepository.save(edge);
        routeUpdatePublisher.publishRouteUpdate(
                "test-rider",
                Map.of("type", "TRAFFIC_UPDATE")
        );
    }
}
