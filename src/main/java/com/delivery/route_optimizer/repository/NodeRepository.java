package com.delivery.route_optimizer.repository;

import com.delivery.route_optimizer.model.Node;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NodeRepository extends JpaRepository<Node, Long> {
    Optional<Node> findByCode(String code);
}
