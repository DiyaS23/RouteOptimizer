package com.delivery.route_optimizer.repository;

import com.delivery.route_optimizer.model.Rider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiderRepository extends JpaRepository<Rider, Long> {
}

