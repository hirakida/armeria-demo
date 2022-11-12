package com.example;

import com.linecorp.armeria.client.RestClient;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.Param;
import com.linecorp.armeria.server.annotation.ProducesJson;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GitHubService {
    private final RestClient restClient;

    @Get("/github/{username}")
    @ProducesJson
    public User githubUser(@Param("username") String username) {
        return restClient.get("/users/{username}")
                         .pathParam("username", username)
                         .execute(User.class)
                         .join()
                         .content();
    }
}
