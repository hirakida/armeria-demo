package com.example.service;

import java.util.ArrayList;
import java.util.List;

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
                                              .setMessage("Hello " + request.getName())
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
                                              .setMessage("Hello " + request.getName())
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

    private static class ClientStreamObserver implements StreamObserver<HelloRequest> {
        private final List<String> names = new ArrayList<>();
        private final StreamObserver<HelloResponse> responseObserver;

        ClientStreamObserver(StreamObserver<HelloResponse> responseObserver) {
            this.responseObserver = responseObserver;
        }

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
                                                  .setMessage("Hello " + names)
                                                  .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }

    private static class BidirectionalStreamObserver implements StreamObserver<HelloRequest> {
        private final StreamObserver<HelloResponse> responseObserver;

        BidirectionalStreamObserver(StreamObserver<HelloResponse> responseObserver) {
            this.responseObserver = responseObserver;
        }

        @Override
        public void onNext(HelloRequest request) {
            log.info("onNext: {}", request.getName());
            HelloResponse response = HelloResponse.newBuilder()
                                                  .setMessage("Hello " + request.getName())
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
}
