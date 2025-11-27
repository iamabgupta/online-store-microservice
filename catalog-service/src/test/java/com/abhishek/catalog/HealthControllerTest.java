package com.abhishek.catalog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;
 
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HealthControllerTest {
 
    @LocalServerPort
    private int port;
 
    @Autowired
    private TestRestTemplate restTemplate;
 
    @Test
    void healthEndpointShouldReturnUp() {
        String response = this.restTemplate
                .getForObject("http://localhost:" + port + "/health", String.class);
 
        assertThat(response).contains("UP");
    }
}