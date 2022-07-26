package com.example;

import org.springframework.stereotype.Component;

import com.linecorp.armeria.client.RestClient;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GitHubClient {
    private final RestClient restClient;

    public User getUser(String username) {
        return restClient.get("/users/{username}")
                         .pathParam("username", username)
                         .execute(User.class)
                         .join()
                         .content();
    }
}
