package com.delivery.route_optimizer.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "riders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private boolean active;

    @ManyToOne
    private Node currentNode;
}

