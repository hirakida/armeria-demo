package com.example.service;

import com.linecorp.armeria.common.HttpHeaderNames;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.common.ResponseHeaders;
import com.linecorp.armeria.server.annotation.Description;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.HttpResult;

public class TextService {
    @Get("/hello1")
    @Description("Hello1")
    public String hello1() {
        return "Hello!";
    }

    @Get("/hello2")
    @Description("Hello2")
    public HttpResult<String> hello2() {
        final ResponseHeaders headers = ResponseHeaders.builder()
                                                       .status(HttpStatus.OK)
                                                       .add(HttpHeaderNames.CACHE_CONTROL, "no-cache")
                                                       .build();
        return HttpResult.of(headers, "Hello!!");
    }
}
