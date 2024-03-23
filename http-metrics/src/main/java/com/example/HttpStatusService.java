package com.example;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.server.annotation.Get;
import com.linecorp.armeria.server.annotation.Param;

public class HttpStatusService {
    @Get("/status/{statusCode}")
    public HttpResponse getStatusCode(@Param int statusCode) {
        return HttpResponse.of(HttpStatus.valueOf(statusCode));
    }
}
