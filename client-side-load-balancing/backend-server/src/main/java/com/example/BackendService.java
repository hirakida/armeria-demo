package com.example;

import com.linecorp.armeria.server.annotation.Get;

public class BackendService {
    private final int port;

    public BackendService(int port) {
        this.port = port;
    }

    @Get("/")
    public String hello() {
        return "localhost:" + port;
    }
}
