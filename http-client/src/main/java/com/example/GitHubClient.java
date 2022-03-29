package com.example;

import static com.example.decorator.AttributeKeys.USERNAME_ATTR;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import com.example.decorator.DateTimeDecorator;
import com.example.decorator.LoggingDecorator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.linecorp.armeria.client.ClientFactory;
import com.linecorp.armeria.client.ConnectionPoolListener;
import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.common.AggregatedHttpResponse;
import com.linecorp.armeria.common.logging.LogLevel;

public class GitHubClient {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());
    private final WebClient webClient;

    public GitHubClient() {
        webClient = createClient();
    }

    public CompletableFuture<User> getUser(String username) {
        return webClient.get("/users/" + username)
                        .aggregate()
                        .thenApply(GitHubClient::toUser);
    }

    public CompletableFuture<User> getUser2(String username) {
        return webClient.prepare()
                        .get("/users/" + username)
                        .attr(USERNAME_ATTR, username)
                        .execute()
                        .aggregate()
                        .thenApply(GitHubClient::toUser);
    }

    private static User toUser(AggregatedHttpResponse response) {
        try {
            return OBJECT_MAPPER.readValue(response.content().toReaderUtf8(), User.class);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static WebClient createClient() {
        final ClientFactory factory = ClientFactory.builder()
                                                   .connectTimeout(Duration.ofSeconds(10))
                                                   .idleTimeout(Duration.ofSeconds(10))
                                                   .connectionPoolListener(ConnectionPoolListener.logging())
                                                   .build();
        return WebClient.builder("https://api.github.com")
                        .decorator(LoggingDecorator::new)
                        .decorator(DateTimeDecorator::new)
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