package com.example;

import com.linecorp.armeria.server.annotation.Get;

public class ThrottlingAnnotatedService {

    @Get("/")
    public String hello() {
        return "Hello!";
    }
}
