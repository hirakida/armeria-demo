package com.example;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.Post;
import com.linecorp.armeria.server.annotation.ProducesJson;
import com.linecorp.armeria.server.annotation.RequestObject;

@Component
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
}
