package com.example;

import java.util.concurrent.CompletableFuture;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubService {
    @GET("/users/{username}")
    CompletableFuture<User> getUser(@Path("username") String username);
}
