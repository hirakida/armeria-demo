package com.example;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.common.AggregatedHttpResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GitHubApiClient {
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public CompletableFuture<User> getUser(String username) {
        return webClient.get("/users/" + username)
                        .aggregate()
                        .thenApply(this::readValue);
    }

    private User readValue(AggregatedHttpResponse response) {
        try {
            return objectMapper.readValue(response.content().toReaderUtf8(), User.class);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
