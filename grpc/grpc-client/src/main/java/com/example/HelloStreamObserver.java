package com.example;

import com.example.Hello.HelloResponse;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloStreamObserver implements StreamObserver<HelloResponse> {
    @Override
    public void onNext(HelloResponse response) {
        log.info("onNext: {}", response.getMessage());
    }

    @Override
    public void onError(Throwable t) {
        log.error("onError: {}", t.getMessage(), t);
    }

    @Override
    public void onCompleted() {
        log.info("onCompleted");
    }
}
