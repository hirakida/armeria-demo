package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.Hello.HelloResponse;

import io.grpc.stub.StreamObserver;

public class HelloStreamObserver implements StreamObserver<HelloResponse> {
    private static final Logger logger = LoggerFactory.getLogger(HelloStreamObserver.class);

    @Override
    public void onNext(HelloResponse response) {
        logger.info("onNext: {}", response.getMessage());
    }

    @Override
    public void onError(Throwable t) {
        logger.error("onError: {}", t.getMessage(), t);
    }

    @Override
    public void onCompleted() {
        logger.info("onCompleted");
    }
}
