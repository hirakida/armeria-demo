package com.example;

import com.linecorp.armeria.server.annotation.Get;

public class HelloService {
    @Get("/")
    public String hello() {
        return "Hello!";
    }
}
