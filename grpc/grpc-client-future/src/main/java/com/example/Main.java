package com.example;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.Hello.HelloRequest;
import com.example.Hello.HelloResponse;
import com.example.HelloServiceGrpc.HelloServiceFutureStub;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;

import com.linecorp.armeria.client.grpc.GrpcClients;
import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.common.logging.LogLevel;

public final class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        final HelloServiceFutureStub client =
                GrpcClients.builder("http://127.0.0.1:8080")
                           .decorator(LoggingClient.builder()
                                                   .requestLogLevel(LogLevel.INFO)
                                                   .successfulResponseLogLevel(LogLevel.INFO)
                                                   .newDecorator())
                           .build(HelloServiceFutureStub.class);

        final HelloRequest request = HelloRequest.newBuilder().setName("hirakida").build();
        final ListenableFuture<HelloResponse> response = client.helloUnary(request);
        Futures.addCallback(response, new FutureCallback<>() {
            @Override
            public void onSuccess(HelloResponse result) {
                logger.info("{}", result);
            }

            @Override
            public void onFailure(Throwable t) {
                logger.error("{}", t.getMessage(), t);
            }
        }, MoreExecutors.directExecutor());

        TimeUnit.SECONDS.sleep(1);
    }
}
