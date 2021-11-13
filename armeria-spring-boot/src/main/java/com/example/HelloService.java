package com.example;

import java.time.LocalDateTime;

import javax.validation.constraints.Min;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.Param;
import com.linecorp.armeria.server.annotation.Post;
import com.linecorp.armeria.server.annotation.ProducesJson;
import com.linecorp.armeria.server.annotation.RequestObject;

import lombok.Data;
import lombok.Value;

@Component
@Validated
public class HelloService {
    @Get("/hello1")
    @ProducesJson
    public HelloResponse hello(@Param String name) {
        return new HelloResponse(String.format("Hello, %s!", name), LocalDateTime.now());
    }

    @Get("/hello2")
    @ProducesJson
    public HelloResponse hello(@Param @Min(1) int size) {
        return new HelloResponse(String.format("Hello, %d!", size), LocalDateTime.now());
    }

    @Post("/")
    @ProducesJson
    public HelloResponse hello(@RequestObject HelloRequest request) {
        return new HelloResponse(request.getMessage(), LocalDateTime.now());
    }

    @Data
    public static class HelloRequest {
        @NotNull
        private String message;
    }

    @Value
    public static class HelloResponse {
        String message;
        LocalDateTime dateTime;
    }
}
