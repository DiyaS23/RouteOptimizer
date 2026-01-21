package com.delivery.route_optimizer.controller;


import com.delivery.route_optimizer.service.TrafficService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/traffic")
@RequiredArgsConstructor
public class TrafficController {

    private final TrafficService trafficService;

    @PostMapping("/update")
    public void updateTraffic(
            @RequestParam Long edgeId,
            @RequestParam double factor) {
        trafficService.updateTraffic(edgeId, factor);
    }
}
