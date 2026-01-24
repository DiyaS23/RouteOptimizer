package com.delivery.route_optimizer.service;

import com.delivery.route_optimizer.model.*;
import com.delivery.route_optimizer.repository.*;
import lombok.* ;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final RiderRepository riderRepository;
    private final DeliveryRepository deliveryRepository;
    private final AssignmentRepository assignmentRepository;

    public Assignment assignDelivery(Long riderId, String destinationCode) {

        Rider rider = riderRepository.findById(riderId)
                .orElseThrow(() -> new RuntimeException("Rider not found"));

        // deactivate old assignment if exists
        assignmentRepository.findByRiderAndActiveTrue(rider)
                .ifPresent(a -> {
                    a.setActive(false);
                    assignmentRepository.save(a);
                });

        Delivery delivery = deliveryRepository.save(
                Delivery.builder()
                        .destinationCode(destinationCode)
                        .completed(false)
                        .build()
        );

        Assignment assignment = Assignment.builder()
                .rider(rider)
                .delivery(delivery)
                .active(true)
                .build();

        return assignmentRepository.save(assignment);
    }
}
