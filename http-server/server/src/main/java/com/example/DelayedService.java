package com.example;

import java.time.Duration;

import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.HttpService;
import com.linecorp.armeria.server.ServiceRequestContext;

public class DelayedService implements HttpService {
    @Override
    public HttpResponse serve(ServiceRequestContext ctx, HttpRequest req) throws Exception {
        return HttpResponse.delayed(HttpResponse.of("delayed 3 seconds"),
                                    Duration.ofSeconds(3));
    }
}
