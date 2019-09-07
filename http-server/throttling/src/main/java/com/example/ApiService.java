package com.example;

import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.server.annotation.Get;

public class ApiService {

    @Get("/")
    public String get() {
        return HttpStatus.OK.toString();
    }
}
