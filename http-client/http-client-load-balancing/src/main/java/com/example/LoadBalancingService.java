package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

import com.linecorp.armeria.client.RestClient;

@Component
public class LoadBalancingService {
    private static final Logger logger = LoggerFactory.getLogger(LoadBalancingService.class);
    private final RestClient restClient;

    public LoadBalancingService(RestClient restClient) {
        this.restClient = restClient;
    }

    @Scheduled(initialDelay = 1000, fixedRate = 1000)
    public void task() {
        final JsonNode response = restClient.get("/")
                                            .execute(JsonNode.class)
                                            .join()
                                            .content();
        logger.info("{}", response);
    }
}
