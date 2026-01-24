package com.delivery.route_optimizer.websocket;


import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RouteUpdatePublisher {

    private final SimpMessagingTemplate messagingTemplate;

    public void publishRouteUpdate(String riderId, Object payload) {
        messagingTemplate.convertAndSend(
                "/topic/routes/" + riderId, payload);
    }
}
