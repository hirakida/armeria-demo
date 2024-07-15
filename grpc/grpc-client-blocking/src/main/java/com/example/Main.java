package com.example;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.Hello.HelloRequest;
import com.example.Hello.HelloResponse;
import com.example.HelloServiceGrpc.HelloServiceBlockingStub;

import com.linecorp.armeria.client.grpc.GrpcClients;
import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.common.grpc.GrpcSerializationFormats;
import com.linecorp.armeria.common.logging.LogLevel;
import com.linecorp.armeria.common.logging.LogWriter;

public final class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        final HelloServiceBlockingStub client1 =
                GrpcClients.builder("http://127.0.0.1:8080")
                           .decorator(LoggingClient
                                              .builder()
                                              .logWriter(LogWriter.builder()
                                                                  .requestLogLevel(LogLevel.INFO)
                                                                  .successfulResponseLogLevel(LogLevel.INFO)
                                                                  .build())
                                              .newDecorator())
                           .build(HelloServiceBlockingStub.class);
        final HelloServiceBlockingStub client2 =
                GrpcClients.builder("http://127.0.0.1:8080")
                           .serializationFormat(GrpcSerializationFormats.JSON)
                           .decorator(LoggingClient
                                              .builder()
                                              .logWriter(LogWriter.builder()
                                                                  .requestLogLevel(LogLevel.INFO)
                                                                  .successfulResponseLogLevel(LogLevel.INFO)
                                                                  .build())
                                              .newDecorator())
                           .build(HelloServiceBlockingStub.class);

        final HelloRequest request = HelloRequest.newBuilder().setName("hirakida").build();
        logger.info("Unary proto: {}", client1.helloUnary(request));
        logger.info("Unary json: {}", client2.helloUnary(request));

        final Iterator<HelloResponse> response2 = client1.helloServerStreaming(request);
        response2.forEachRemaining(response -> logger.info("Server-streaming: {}", response));
    }
}
