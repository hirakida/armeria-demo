package com.example.controller;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.example.ExceptionHandlerImpl;

import com.linecorp.armeria.server.annotation.ExceptionHandler;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.Param;
import com.linecorp.armeria.server.annotation.Post;
import com.linecorp.armeria.server.annotation.ProducesJson;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Component
@Validated
@ExceptionHandler(ExceptionHandlerImpl.class)
public class HelloService {
    @Get("/hello1")
    public String hello1(@Param String name) {
        return "Hello, %s!".formatted(name);
    }

    @Get("/hello2")
    public String hello2(@Param @Min(1) int number) {
        return "Hello, %d!".formatted(number);
    }

    @Post("/hello3")
    @ProducesJson
    public HelloResponse hello3(@Valid HelloRequest request) {
        return new HelloResponse(request.message, LocalDateTime.now());
    }

    public record HelloRequest(@NotNull String message) {}

    public record HelloResponse(String message, LocalDateTime dateTime) {}
}
