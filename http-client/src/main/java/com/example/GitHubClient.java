package com.example;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.Duration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.linecorp.armeria.client.ClientFactory;
import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.common.AggregatedHttpResponse;
import com.linecorp.armeria.common.logging.LogLevel;

public class GitHubClient {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());
    private final WebClient webClient = createClient();

    public User getUser(String username) {
        AggregatedHttpResponse response = webClient.get("/users/" + username).aggregate().join();
        try {
            return OBJECT_MAPPER.readValue(response.content().toReaderUtf8(), User.class);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    static WebClient createClient() {
        ClientFactory factory = ClientFactory.builder()
                                             .connectTimeout(Duration.ofSeconds(10))
                                             .idleTimeout(Duration.ofSeconds(10))
                                             .build();
        return WebClient.builder("https://api.github.com")
                        .decorator(LoggingClient.builder()
                                                .requestLogLevel(LogLevel.INFO)
                                                .successfulResponseLogLevel(LogLevel.INFO)
                                                .newDecorator())
                        .responseTimeout(Duration.ofSeconds(10))
                        .writeTimeout(Duration.ofSeconds(10))
                        .factory(factory)
                        .build();
    }
}
