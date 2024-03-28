package com.example.service;

import java.util.ArrayList;
import java.util.List;

import com.example.Hello.HelloRequest;
import com.example.Hello.HelloResponse;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ClientStreamObserver implements StreamObserver<HelloRequest> {
    private final List<String> names = new ArrayList<>();
    private final StreamObserver<HelloResponse> responseObserver;

    @Override
    public void onNext(HelloRequest request) {
        log.info("onNext: {}", request.getName());
        names.add(request.getName());
    }

    @Override
    public void onError(Throwable t) {
        log.error("onError: {}", t.getMessage(), t);
    }

    @Override
    public void onCompleted() {
        HelloResponse response = HelloResponse.newBuilder()
                                              .setMessage("Hello, %s!".formatted(names))
                                              .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
