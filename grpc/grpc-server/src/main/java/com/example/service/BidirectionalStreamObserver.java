package com.example.service;

import com.example.Hello.HelloRequest;
import com.example.Hello.HelloResponse;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class BidirectionalStreamObserver implements StreamObserver<HelloRequest> {
    private final StreamObserver<HelloResponse> responseObserver;

    @Override
    public void onNext(HelloRequest request) {
        log.info("onNext: {}", request.getName());
        HelloResponse response = HelloResponse.newBuilder()
                                              .setMessage("Hello, %s!".formatted(request.getName()))
                                              .build();
        responseObserver.onNext(response);
    }

    @Override
    public void onError(Throwable t) {
        log.error("onError: {}", t.getMessage(), t);
    }

    @Override
    public void onCompleted() {
        responseObserver.onCompleted();
    }
}
