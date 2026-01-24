package com.delivery.route_optimizer.model;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "assignments")
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

