package com.example;

import org.springframework.stereotype.Component;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.annotation.Get;

@Component
public class HelloService {
    @Get("/")
    public HttpResponse hello() {
        return HttpResponse.of("Hello, Armeria!");
    }
}
