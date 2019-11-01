package com.example.service;

import java.time.LocalDateTime;

import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.ProducesJson;

public class ThrottleService {

    @Get("/datetime")
    @ProducesJson
    public LocalDateTime getDateTime() {
        return LocalDateTime.now();
    }
}
