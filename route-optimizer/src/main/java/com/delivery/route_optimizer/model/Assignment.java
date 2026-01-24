package com.delivery.route_optimizer.model;

import jakarta.persistence.*;
import jakarta.persistence.Id;

@Entity
@Table(name = "assignments")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Rider rider;

    @ManyToOne(optional = false)
    private Delivery delivery;

    private String routeId;
    private boolean active;
}

