package com.example.service;

import org.springframework.util.DigestUtils;

import com.linecorp.armeria.common.HttpHeaderNames;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.common.ResponseHeaders;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.HttpResult;
import com.linecorp.armeria.server.annotation.Param;

public class TextService {

    @Get("/md5")
    public String getMd5(@Param("text") String text) {
        return DigestUtils.md5DigestAsHex(text.getBytes());
    }

    @Get("/hello")
    public HttpResult<String> hello() {
        ResponseHeaders headers = ResponseHeaders.builder()
                                                 .status(HttpStatus.OK)
                                                 .add(HttpHeaderNames.CACHE_CONTROL, "no-cache")
                                                 .build();
        return HttpResult.of(headers, "Hello, Armeria!!");
    }
}
