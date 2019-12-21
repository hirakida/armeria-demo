package com.example;

import org.springframework.stereotype.Service;

import com.example.Hello.HelloRequest;
import com.example.Hello.HelloResponse;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HelloService extends HelloServiceGrpc.HelloServiceImplBase {

    @Override
    public void sayHello1(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        HelloResponse response = HelloResponse.newBuilder()
                                              .setMessage("Hello " + request.getName())
                                              .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void sayHello2(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        HelloResponse response = HelloResponse.newBuilder()
                                              .setMessage("Hello " + request.getName())
                                              .build();
        responseObserver.onNext(response);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<HelloRequest> sayHello3(StreamObserver<HelloResponse> responseObserver) {
        return new StreamObserver1(responseObserver);
    }

    @Override
    public StreamObserver<HelloRequest> sayHello4(StreamObserver<HelloResponse> responseObserver) {
        return new StreamObserver2(responseObserver);
    }

    @RequiredArgsConstructor
    private static class StreamObserver1 implements StreamObserver<HelloRequest> {
        private final StreamObserver<HelloResponse> responseObserver;
        private String name;

        @Override
        public void onNext(HelloRequest request) {
            log.info("onNext: {}", request.getName());
            name = request.getName();
        }

        @Override
        public void onError(Throwable t) {
            log.error("onError: {}", t.getMessage(), t);
        }

        @Override
        public void onCompleted() {
            log.info("onCompleted");
            HelloResponse response = HelloResponse.newBuilder()
                                                  .setMessage("Hello " + name)
                                                  .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }

    @RequiredArgsConstructor
    private static class StreamObserver2 implements StreamObserver<HelloRequest> {
        private final StreamObserver<HelloResponse> responseObserver;

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
            log.info("onCompleted");
            responseObserver.onCompleted();
        }
    }
}
