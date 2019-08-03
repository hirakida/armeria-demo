package com.example;

import org.springframework.stereotype.Component;

import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.server.annotation.Get;

@Component
public class ApiService {

    @Get("/")
    public String get() {
        return HttpStatus.OK.toString();
    }
}
