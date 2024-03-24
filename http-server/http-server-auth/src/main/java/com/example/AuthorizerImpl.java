package com.example;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.common.auth.OAuth2Token;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.auth.AuthTokenExtractors;
import com.linecorp.armeria.server.auth.Authorizer;

public class AuthorizerImpl implements Authorizer<HttpRequest> {
    private static final Logger logger = LoggerFactory.getLogger(AuthorizerImpl.class);

    @Override
    public CompletionStage<Boolean> authorize(ServiceRequestContext ctx, HttpRequest req) {
        final OAuth2Token token = AuthTokenExtractors.oAuth2().apply(req.headers());
        Objects.requireNonNull(token);
        logger.info("token: {}", token);
        return CompletableFuture.completedFuture(true);
    }
}
