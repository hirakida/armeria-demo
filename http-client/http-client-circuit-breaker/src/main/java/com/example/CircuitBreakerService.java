package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

import com.linecorp.armeria.client.RestClient;

@Component
public class CircuitBreakerService {
    private static final Logger logger = LoggerFactory.getLogger(CircuitBreakerService.class);
    private final RestClient restClient;

    public CircuitBreakerService(RestClient restClient) {
        this.restClient = restClient;
    }

    @Scheduled(fixedRate = 1000)
    public void task() {
        restClient.get("/backend")
                  .execute(JsonNode.class)
                  .whenComplete((response, e) -> {
                      if (e != null) {
                          logger.warn("{}", e.getMessage());
                      } else {
                          logger.info("{}", response.content());
                      }
                  });
    }
}
