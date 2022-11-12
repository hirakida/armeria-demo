package com.example;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.server.annotation.Default;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.Param;

public class HelloService {
    @Get("/hello/{status}")
    public HttpResponse hello(@Param("status") @Default("200") int statusCode) {
        return HttpResponse.of(HttpStatus.valueOf(statusCode));
    }
}
