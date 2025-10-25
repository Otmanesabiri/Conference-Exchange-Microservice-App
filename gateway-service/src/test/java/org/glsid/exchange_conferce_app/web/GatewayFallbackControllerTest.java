package org.glsid.exchange_conferce_app.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(controllers = GatewayFallbackController.class)
@Import(org.glsid.exchange_conferce_app.config.SecurityConfig.class)
class GatewayFallbackControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void fallbackShouldReturnServiceUnavailable() {
        webTestClient.get()
            .uri("/fallback")
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.SERVICE_UNAVAILABLE)
            .expectBody()
            .jsonPath("$.message").isEqualTo("Service temporarily unavailable. Please try again later.")
            .jsonPath("$.status").isEqualTo(HttpStatus.SERVICE_UNAVAILABLE.value());
    }
}
