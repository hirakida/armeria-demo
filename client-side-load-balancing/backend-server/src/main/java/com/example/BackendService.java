package com.example;

import com.linecorp.armeria.server.annotation.Get;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BackendService {
    private final int port;

    @Get("/")
    public String hello() {
        return "localhost:" + port;
    }
}
