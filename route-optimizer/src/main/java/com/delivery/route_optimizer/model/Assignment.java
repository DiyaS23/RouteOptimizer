package com.delivery.route_optimizer.model;

import jakarta.persistence.*;
import jakarta.persistence.Id;

@Entity
@Table(name = "assignments")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Rider rider;

    @ManyToOne
    private Delivery delivery;

    private String routeId;
}

