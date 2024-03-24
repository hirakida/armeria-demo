package com.example;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import com.linecorp.armeria.common.HttpRequest;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.auth.AbstractAuthorizerWithHandlers;
import com.linecorp.armeria.server.auth.AuthorizationStatus;

public class AuthorizerWithHandlers extends AbstractAuthorizerWithHandlers<HttpRequest> {
    public static final String TEST_HEADER = "Test";
    private static final AuthorizationStatus SUCCESS_STATUS =
            AuthorizationStatus.ofSuccess();
    private static final AuthorizationStatus FAILURE_STATUS =
            AuthorizationStatus.ofFailure(new AuthFailureHandlerImpl());

    @Override
    public CompletionStage<AuthorizationStatus> authorizeAndSupplyHandlers(ServiceRequestContext ctx,
                                                                           HttpRequest req) {
        if (req == null || !req.headers().contains(TEST_HEADER)) {
            return CompletableFuture.completedFuture(FAILURE_STATUS);
        }

        return CompletableFuture.completedFuture(SUCCESS_STATUS);
    }
}
