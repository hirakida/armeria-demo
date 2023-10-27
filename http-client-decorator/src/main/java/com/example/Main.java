package com.example;

import static com.example.HelloDecorator.USERNAME_ATTR;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;

import com.linecorp.armeria.client.RestClient;
import com.linecorp.armeria.client.logging.LoggingClient;

public final class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        final RestClient restClient = RestClient.builder("https://api.github.com")
                                                .decorator(HelloDecorator::new)
                                                .decorator(LoggingClient.newDecorator())
                                                .build();
        final JsonNode response = restClient.get("/users/hirakida")
                                            .attr(USERNAME_ATTR, "hirakida")
                                            .execute(JsonNode.class)
                                            .join()
                                            .content();
        logger.info("{}", response);
    }
}
