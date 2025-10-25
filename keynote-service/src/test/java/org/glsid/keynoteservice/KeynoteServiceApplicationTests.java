package org.glsid.keynoteservice;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.jwt.JwtDecoder;

@SpringBootTest
class KeynoteServiceApplicationTests {

    @Test
    void contextLoads() {
    }

    @TestConfiguration
    static class TestSecurityConfig {

        @Bean
        JwtDecoder jwtDecoder() {
            return mock(JwtDecoder.class);
        }
    }
}
