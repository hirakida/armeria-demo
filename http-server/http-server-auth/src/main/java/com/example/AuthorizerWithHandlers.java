package com.example;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.common.auth.OAuth2Token;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.auth.AbstractAuthorizerWithHandlers;
import com.linecorp.armeria.server.auth.AuthTokenExtractors;
import com.linecorp.armeria.server.auth.AuthorizationStatus;

public class AuthorizerWithHandlers extends AbstractAuthorizerWithHandlers<HttpRequest> {
    private static final Logger logger = LoggerFactory.getLogger(AuthorizerWithHandlers.class);
    private static final AuthorizationStatus SUCCESS_STATUS = AuthorizationStatus.ofSuccess();
    private static final AuthorizationStatus FAILURE_STATUS =
            AuthorizationStatus.ofFailure(new AuthFailureHandlerImpl());

    @Override
    public CompletionStage<AuthorizationStatus> authorizeAndSupplyHandlers(ServiceRequestContext ctx,
                                                                           HttpRequest req) {
        if (req != null) {
            final OAuth2Token token = AuthTokenExtractors.oAuth2().apply(req.headers());
            if (token != null) {
                logger.info("token: {}", token);
                return CompletableFuture.completedFuture(SUCCESS_STATUS);
            }
        }
        return CompletableFuture.completedFuture(FAILURE_STATUS);
    }
}
