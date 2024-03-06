package com.example;

import java.util.concurrent.TimeUnit;

import com.example.Hello.HelloRequest;
import com.example.HelloServiceGrpc.HelloServiceStub;

import com.linecorp.armeria.client.grpc.GrpcClients;
import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.common.logging.LogLevel;

import io.grpc.stub.StreamObserver;

public final class Main {
    public static void main(String[] args) throws Exception {
        final HelloServiceStub client =
                GrpcClients.builder("http://127.0.0.1:8080")
                           .decorator(LoggingClient.builder()
                                                   .requestLogLevel(LogLevel.INFO)
                                                   .successfulResponseLogLevel(LogLevel.INFO)
                                                   .newDecorator())
                           .build(HelloServiceStub.class);

        // Unary
        final HelloRequest request1 = HelloRequest.newBuilder().setName("hirakida1").build();
        client.helloUnary(request1, new HelloStreamObserver());
        TimeUnit.SECONDS.sleep(1);

        // Server Streaming
        final HelloRequest request2 = HelloRequest.newBuilder().setName("hirakida2").build();
        client.helloServerStreaming(request2, new HelloStreamObserver());
        TimeUnit.SECONDS.sleep(1);

        // Client Streaming
        final StreamObserver<HelloRequest> requestStream1 =
                client.helloClientStreaming(new HelloStreamObserver());
        final HelloRequest request3_1 = HelloRequest.newBuilder().setName("hirakida3-1").build();
        final HelloRequest request3_2 = HelloRequest.newBuilder().setName("hirakida3-2").build();
        final HelloRequest request3_3 = HelloRequest.newBuilder().setName("hirakida3-3").build();
        requestStream1.onNext(request3_1);
        requestStream1.onNext(request3_2);
        requestStream1.onNext(request3_3);
        requestStream1.onCompleted();
        TimeUnit.SECONDS.sleep(1);

        // Bidirectional Streaming
        final StreamObserver<HelloRequest> requestStream2 =
                client.helloBidirectionalStreaming(new HelloStreamObserver());
        final HelloRequest request4_1 = HelloRequest.newBuilder().setName("hirakida4-1").build();
        final HelloRequest request4_2 = HelloRequest.newBuilder().setName("hirakida4-2").build();
        final HelloRequest request4_3 = HelloRequest.newBuilder().setName("hirakida4-3").build();
        requestStream2.onNext(request4_1);
        requestStream2.onNext(request4_2);
        requestStream2.onNext(request4_3);
        requestStream2.onCompleted();
        TimeUnit.SECONDS.sleep(1);
    }
}
