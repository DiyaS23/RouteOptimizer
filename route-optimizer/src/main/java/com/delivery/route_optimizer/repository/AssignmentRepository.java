package com.delivery.route_optimizer.repository;

import com.delivery.route_optimizer.model.Assignment;
import com.delivery.route_optimizer.model.Rider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    Optional<Assignment> findByRiderAndActiveTrue(Rider rider);
}
