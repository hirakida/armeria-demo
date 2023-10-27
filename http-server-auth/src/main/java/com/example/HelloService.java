package com.example;

import com.linecorp.armeria.server.annotation.Get;

public class HelloService {
    @Get("/hello")
    public String hello() {
        return "Hello!";
    }
}
