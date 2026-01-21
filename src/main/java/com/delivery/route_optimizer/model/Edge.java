package com.delivery.route_optimizer.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "edges")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Edge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_node_id", nullable = false)
    private Node from;

    @ManyToOne
    @JoinColumn(name = "to_node_id", nullable = false)
    private Node to;

    private double distance;

    private double trafficFactor;
}

