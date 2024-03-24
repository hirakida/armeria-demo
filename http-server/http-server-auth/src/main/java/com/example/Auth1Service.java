package com.example;

import com.linecorp.armeria.server.annotation.Get;

public class Auth1Service {
    @Get("/auth1")
    public String auth1() {
        return "OK";
    }
}
