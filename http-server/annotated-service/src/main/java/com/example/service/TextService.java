package com.example.service;

import com.linecorp.armeria.server.annotation.Get;

public class TextService {

    @Get("/text")
    public String text() {
        return "Hello, Armeria!";
    }
}
