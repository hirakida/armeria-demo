package com.example;

import com.linecorp.armeria.server.annotation.Get;

public class Hello2Service {
    @Get("/hello2")
    public String hello() {
        return "Hello!!";
    }
}
