package com.delivery.route_optimizer.controller;

import com.delivery.route_optimizer.model.Assignment;
import com.delivery.route_optimizer.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AssignmentService assignmentService;

    @PostMapping("/assign")
    public Assignment assign(
            @RequestParam Long riderId,
            @RequestParam String destinationCode) {

        return assignmentService.assignDelivery(riderId, destinationCode);
    }
}
