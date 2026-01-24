package com.delivery.route_optimizer.service;

import com.delivery.route_optimizer.model.*;
import com.delivery.route_optimizer.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RiderRouteService {

    private final RiderRepository riderRepository;
    private final AssignmentRepository assignmentRepository;
    private final RouteService routeService;

    public RouteResult getCurrentRoute(Long riderId) {

        Rider rider = riderRepository.findById(riderId)
                .orElseThrow(() -> new RuntimeException("Rider not found"));

        Assignment assignment = assignmentRepository
                .findByRiderAndActiveTrue(rider)
                .orElseThrow(() -> new RuntimeException("No active assignment"));

        RouteResult result = routeService.calculateRoute(
                rider.getCurrentNode().getCode(),
                assignment.getDelivery().getDestinationCode()
        );

        assignment.setRouteId("generated");
        assignmentRepository.save(assignment);

        return result;
    }
}
