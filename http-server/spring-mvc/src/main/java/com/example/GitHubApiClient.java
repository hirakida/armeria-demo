package com.example;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.common.AggregatedHttpResponse;

@Component
public class GitHubApiClient {
    private static final String BASE_URL = "https://api.github.com";
    private final ObjectMapper objectMapper;
    private final WebClient webClient;

    public GitHubApiClient(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        webClient = WebClient.of(BASE_URL);
    }

    public CompletableFuture<User> getUser(String username) {
        return webClient.get("/users/" + username)
                        .aggregate()
                        .handle((response, e) -> readValue(response));
    }

    private User readValue(AggregatedHttpResponse response) {
        try {
            return objectMapper.readValue(response.content().toReaderUtf8(), User.class);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
