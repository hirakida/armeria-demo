package com.example.service;

import org.springframework.stereotype.Component;

import com.example.Hello.HelloRequest;
import com.example.Hello.HelloResponse;
import com.example.HelloServiceGrpc.HelloServiceImplBase;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class HelloService extends HelloServiceImplBase {
    /**
     * Unary RPC
     */
    @Override
    public void helloUnary(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        HelloResponse response = HelloResponse.newBuilder()
                                              .setMessage("Hello, %s!".formatted(request.getName()))
                                              .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * Server streaming RPC
     */
    @Override
    public void helloServerStreaming(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        HelloResponse response = HelloResponse.newBuilder()
                                              .setMessage("Hello, %s!".formatted(request.getName()))
                                              .build();
        responseObserver.onNext(response);
        responseObserver.onNext(response);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * Client streaming RPC
     */
    @Override
    public StreamObserver<HelloRequest> helloClientStreaming(StreamObserver<HelloResponse> responseObserver) {
        return new ClientStreamObserver(responseObserver);
    }

    /**
     * Bidirectional streaming RPC
     */
    @Override
    public StreamObserver<HelloRequest> helloBidirectionalStreaming(
            StreamObserver<HelloResponse> responseObserver) {
        return new BidirectionalStreamObserver(responseObserver);
    }
}
