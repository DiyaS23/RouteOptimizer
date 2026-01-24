package com.delivery.route_optimizer.repository;

import com.delivery.route_optimizer.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
