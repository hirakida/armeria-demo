package com.example;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.example.model.Key;
import com.example.model.User;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubService {
    @GET("/users/{username}")
    CompletableFuture<User> getUser(@Path("username") String username);

    @GET("/users/{username}/keys")
    Single<List<Key>> getKeys(@Path("username") String username);

    default CompletableFuture<User> getUser() {
        return getUser("hirakida");
    }
}
