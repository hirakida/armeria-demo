package com.example;

import java.io.IOException;
import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.linecorp.armeria.client.ClientFactory;
import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.common.AggregatedHttpResponse;
import com.linecorp.armeria.common.logging.LogLevel;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    public static void main(String[] args) {
        ClientFactory factory = ClientFactory.builder()
                                             .connectTimeout(Duration.ofSeconds(10))
                                             .idleTimeout(Duration.ofSeconds(10))
                                             .build();
        WebClient webClient = WebClient.builder("https://api.github.com")
                                       .decorator(LoggingClient.builder()
                                                               .requestLogLevel(LogLevel.INFO)
                                                               .successfulResponseLogLevel(LogLevel.INFO)
                                                               .newDecorator())
                                       .responseTimeout(Duration.ofSeconds(10))
                                       .writeTimeout(Duration.ofSeconds(10))
                                       .factory(factory)
                                       .build();

        AggregatedHttpResponse response = webClient.get("/users/hirakida").aggregate().join();
        try {
            User user = OBJECT_MAPPER.readValue(response.content().toReaderUtf8(), User.class);
            LOGGER.info("{}", user);
        } catch (IOException ignored) { }
    }
}
