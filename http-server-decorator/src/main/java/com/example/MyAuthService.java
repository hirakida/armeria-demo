package com.example;

import com.linecorp.armeria.server.annotation.Get;

public class MyAuthService {
    @Get("/auth")
    public String auth() {
        return "OK";
    }
}
