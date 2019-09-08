package com.example;

import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.HttpResult;

public class DemoService {

    @Get("/")
    public HttpResult<String> get() {
        return HttpResult.of(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
