package org.glsid.exchange_conferce_app.web;

import java.time.Instant;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class GatewayFallbackController {

    @GetMapping("/fallback")
    public Mono<ResponseEntity<Map<String, Object>>> fallback() {
        return Mono.just(
            ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(
                    "timestamp", Instant.now().toString(),
                    "status", HttpStatus.SERVICE_UNAVAILABLE.value(),
                    "message", "Service temporarily unavailable. Please try again later."
                )));
    }
}
