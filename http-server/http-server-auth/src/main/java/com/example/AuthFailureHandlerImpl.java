package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.common.annotation.Nullable;
import com.linecorp.armeria.server.HttpService;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.auth.AuthFailureHandler;

public class AuthFailureHandlerImpl implements AuthFailureHandler {
    private static final Logger logger = LoggerFactory.getLogger(AuthFailureHandlerImpl.class);

    @Override
    public HttpResponse authFailed(HttpService delegate, ServiceRequestContext ctx, HttpRequest req,
                                   @Nullable Throwable cause) throws Exception {
        logger.warn("authFailed", cause);
        return HttpResponse.of(HttpStatus.UNAUTHORIZED);
    }
}
