package com.example;

import com.linecorp.armeria.server.annotation.Get;

public class Hello1Service {
    @Get("/hello1")
    public String hello() {
        return "Hello!";
    }
}
