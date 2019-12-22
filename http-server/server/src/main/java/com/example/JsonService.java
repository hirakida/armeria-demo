package com.example;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.common.MediaType;
import com.linecorp.armeria.server.HttpService;
import com.linecorp.armeria.server.ServiceRequestContext;

public class JsonService implements HttpService {
    @Override
    public HttpResponse serve(ServiceRequestContext ctx, HttpRequest req) throws Exception {
        return HttpResponse.of(HttpStatus.OK,
                               MediaType.JSON_UTF_8,
                               "{\"date\":\"%s\",\"datetime\":\"%s\"}",
                               LocalDate.now(), LocalDateTime.now());
    }
}
