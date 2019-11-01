package com.example.service;

import com.linecorp.armeria.common.HttpHeaderNames;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.common.ResponseHeaders;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.HttpResult;

public class TextService {

    @Get("/text1")
    public String text1() {
        return "Hello, Armeria!";
    }

    @Get("/text2")
    public HttpResult<String> text2() {
        ResponseHeaders headers = ResponseHeaders.builder()
                                                 .status(HttpStatus.OK)
                                                 .add(HttpHeaderNames.CACHE_CONTROL, "no-cache")
                                                 .build();
        return HttpResult.of(headers, "Hello, Armeria!!");
    }
}
