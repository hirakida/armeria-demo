package com.example;

import org.springframework.stereotype.Component;

import com.linecorp.armeria.server.annotation.Get;

@Component
public class HelloService {
    @Get("/hello")
    public String hello() {
        return "Hello, Armeria!";
    }
}
