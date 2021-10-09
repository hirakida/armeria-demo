package com.example;

import java.time.LocalDateTime;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.Post;
import com.linecorp.armeria.server.annotation.ProducesJson;
import com.linecorp.armeria.server.annotation.RequestObject;

import lombok.Data;
import lombok.Value;

@Component
@Validated
public class HelloService {
    @Get("/")
    @ProducesJson
    public HelloResponse hello() {
        return new HelloResponse("Hello!", LocalDateTime.now());
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
