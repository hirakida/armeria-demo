package com.example;

import static com.example.decorator.LoggingDecorator.USERNAME_ATTR;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.example.decorator.LoggingDecorator;
import com.example.model.Repo;
import com.example.model.User;
import com.fasterxml.jackson.core.type.TypeReference;

import com.linecorp.armeria.client.ClientFactory;
import com.linecorp.armeria.client.ConnectionPoolListener;
import com.linecorp.armeria.client.RestClient;
import com.linecorp.armeria.client.logging.ContentPreviewingClient;
import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.common.ResponseEntity;
import com.linecorp.armeria.common.logging.LogLevel;

public class GitHubClient {
    private final TypeReference<List<Repo>> typeReference = new TypeReference<>() {};
    private final RestClient restClient;

    public GitHubClient() {
        final ClientFactory factory = ClientFactory.builder()
                                                   .connectTimeout(Duration.ofSeconds(10))
                                                   .idleTimeout(Duration.ofSeconds(10))
                                                   .connectionPoolListener(ConnectionPoolListener.logging())
                                                   .build();
        restClient = RestClient.builder("https://api.github.com")
                               .decorator(LoggingDecorator::new)
                               .decorator(ContentPreviewingClient.newDecorator(1000))
                               .decorator(LoggingClient.builder()
                                                       .requestLogLevel(LogLevel.INFO)
                                                       .successfulResponseLogLevel(LogLevel.INFO)
                                                       .newDecorator())
                               .responseTimeout(Duration.ofSeconds(10))
                               .writeTimeout(Duration.ofSeconds(10))
                               .factory(factory)
                               .build();
    }

    public CompletableFuture<ResponseEntity<User>> getUser(String username) {
        return restClient.get("/users/{username}")
                         .pathParam("username", username)
                         .execute(User.class);
    }

    public CompletableFuture<ResponseEntity<User>> getUserWithAttr(String username) {
        return restClient.get("/users/{username}")
                         .pathParam("username", username)
                         .attr(USERNAME_ATTR, username)
                         .execute(User.class);
    }

    public CompletableFuture<ResponseEntity<List<Repo>>> getRepos(String username) {
        return restClient.get("/users/{username}/repos")
                         .pathParam("username", username)
                         .execute(typeReference);
    }
}
