package com.delivery.route_optimizer.exception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFound(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                        "error", ex.getMessage(),
                        "timestamp", Instant.now()
                )
        );
    }

    @ExceptionHandler(NoRouteFoundException.class)
    public ResponseEntity<?> handleNoRoute(NoRouteFoundException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                Map.of(
                        "error", ex.getMessage(),
                        "timestamp", Instant.now()
                )
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                Map.of(
                        "error", "Internal server error",
                        "timestamp", Instant.now()
                )
        );
    }
}
