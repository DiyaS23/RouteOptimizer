package com.delivery.route_optimizer.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class RouteMetrics {

    public final Counter routeRequests;
    public final Counter cacheHits;
    public final Counter cacheMisses;

    public RouteMetrics(MeterRegistry registry) {
        this.routeRequests = registry.counter("routes.requests.total");
        this.cacheHits = registry.counter("routes.cache.hits");
        this.cacheMisses = registry.counter("routes.cache.misses");
    }
}
