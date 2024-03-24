package com.example;

import com.linecorp.armeria.server.annotation.Get;

public class Auth2Service {
    @Get("/auth2")
    public String auth2() {
        return "OK";
    }
}
