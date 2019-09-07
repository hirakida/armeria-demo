package com.example.service;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.IntStream;

import com.example.model.User;

import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.Param;
import com.linecorp.armeria.server.annotation.ProducesJson;

public class UserService {

    @Get("/users")
    @ProducesJson
    public List<User> getUsers() {
        return IntStream.rangeClosed(0, 10)
                        .mapToObj(i -> new User(i, "user" + i))
                        .collect(toList());
    }

    @Get("/users/{userId}")
    @ProducesJson
    public User getUser(@Param long userId) {
        return new User(userId, "user" + userId);
    }
}
