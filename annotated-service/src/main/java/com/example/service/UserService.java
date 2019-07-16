package com.example.service;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.IntStream;

import com.example.model.User;
import com.example.support.JsonResponseConverter;

import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.Param;
import com.linecorp.armeria.server.annotation.ResponseConverter;

@ResponseConverter(JsonResponseConverter.class)
public class UserService {

    @Get("/users")
    public List<User> getUsers() {
        return IntStream.rangeClosed(0, 10)
                        .mapToObj(i -> new User(i, "user" + i))
                        .collect(toList());
    }

    @Get("/users/{userId}")
    public User getUser(@Param long userId) {
        return new User(userId, "user" + userId);
    }
}
