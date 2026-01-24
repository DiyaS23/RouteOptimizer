package com.delivery.route_optimizer.repository;

import com.delivery.route_optimizer.model.Edge;
import com.delivery.route_optimizer.model.Node;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EdgeRepository extends JpaRepository<Edge, Long> {
    List<Edge> findByFrom(Node from);
}
