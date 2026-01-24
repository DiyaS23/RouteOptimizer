package com.delivery.route_optimizer.model;

import jakarta.persistence.*;
import lombok.* ;

@Entity
@Table(name = "deliveries")
<<<<<<< HEAD
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
=======
>>>>>>> parent of bf7d1ad (updated)
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String destinationCode;
    private boolean completed;
}

