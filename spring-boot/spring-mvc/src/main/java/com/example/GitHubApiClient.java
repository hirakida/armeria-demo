package com.example;

import java.io.IOException;
import java.io.UncheckedIOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.common.AggregatedHttpResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GitHubApiClient {
    private static final String BASE_URL = "https://api.github.com";
    private final WebClient webClient = WebClient.of(BASE_URL);
    private final ObjectMapper objectMapper;

    public User getUser(String username) {
        return webClient.get("/users/" + username)
                        .aggregate()
                        .thenApply(this::readValue)
                        .join();
    }

    private User readValue(AggregatedHttpResponse response) {
        try {
            return objectMapper.readValue(response.content().toReaderUtf8(), User.class);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
