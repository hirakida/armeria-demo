package com.example;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.Calculator.CalculatorRequest;
import com.example.Calculator.CalculatorRequest.OperationType;
import com.example.Calculator.CalculatorResponse;
import com.example.CalculatorServiceGrpc.CalculatorServiceBlockingStub;
import com.example.Hello.HelloRequest;
import com.example.HelloServiceGrpc.HelloServiceStub;

import com.linecorp.armeria.client.grpc.GrpcClients;
import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.client.logging.LoggingClientBuilder;
import com.linecorp.armeria.common.grpc.GrpcSerializationFormats;
import com.linecorp.armeria.common.logging.LogLevel;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class GrpcClientRunner implements CommandLineRunner {
    private static final String BASE_URI = "http://127.0.0.1:8080/";
    private final CalculatorServiceBlockingStub calculatorService;

    @Override
    public void run(String... args) throws Exception {
        CalculatorRequest request1 = CalculatorRequest.newBuilder()
                                                      .setNumber1(1)
                                                      .setNumber2(2)
                                                      .setOperation(OperationType.ADD)
                                                      .build();
        CalculatorResponse response = calculatorService.calculate(request1);
        log.info("result={}", response.getResult());

        CalculatorRequest request2 = CalculatorRequest.newBuilder()
                                                      .setNumber1(2)
                                                      .setNumber2(3)
                                                      .setOperation(OperationType.MULTIPLY)
                                                      .build();
        response = calculatorService.calculate(request2);
        log.info("result={}", response.getResult());

        hello();
    }

    private static void hello() throws InterruptedException {
        LoggingClientBuilder loggingClientBuilder = LoggingClient.builder()
                                                                 .requestLogLevel(LogLevel.INFO)
                                                                 .successfulResponseLogLevel(LogLevel.INFO);
        HelloServiceStub client1 = GrpcClients.builder(BASE_URI)
                                              .decorator(loggingClientBuilder.newDecorator())
                                              .build(HelloServiceStub.class);
        HelloServiceStub client2 = GrpcClients.builder(BASE_URI)
                                              .serializationFormat(GrpcSerializationFormats.JSON)
                                              .decorator(loggingClientBuilder.newDecorator())
                                              .build(HelloServiceStub.class);

        // Unary
        HelloRequest request1 = HelloRequest.newBuilder().setName("hirakida1").build();
        client1.helloUnary(request1, new HelloStreamObserver());
        client2.helloUnary(request1, new HelloStreamObserver());
        TimeUnit.SECONDS.sleep(1);

        // Server Streaming
        HelloRequest request2 = HelloRequest.newBuilder().setName("hirakida2").build();
        client1.helloServerStreaming(request2, new HelloStreamObserver());
        TimeUnit.SECONDS.sleep(1);

        // Client Streaming
        StreamObserver<HelloRequest> requestStream1 =
                client1.helloClientStreaming(new HelloStreamObserver());
        HelloRequest request3_1 = HelloRequest.newBuilder().setName("hirakida3-1").build();
        HelloRequest request3_2 = HelloRequest.newBuilder().setName("hirakida3-2").build();
        HelloRequest request3_3 = HelloRequest.newBuilder().setName("hirakida3-3").build();
        requestStream1.onNext(request3_1);
        requestStream1.onNext(request3_2);
        requestStream1.onNext(request3_3);
        requestStream1.onCompleted();
        TimeUnit.SECONDS.sleep(1);

        // Bidirectional Streaming
        StreamObserver<HelloRequest> requestStream2 =
                client1.helloBidirectionalStreaming(new HelloStreamObserver());
        HelloRequest request4_1 = HelloRequest.newBuilder().setName("hirakida4-1").build();
        HelloRequest request4_2 = HelloRequest.newBuilder().setName("hirakida4-2").build();
        HelloRequest request4_3 = HelloRequest.newBuilder().setName("hirakida4-3").build();
        requestStream2.onNext(request4_1);
        requestStream2.onNext(request4_2);
        requestStream2.onNext(request4_3);
        requestStream2.onCompleted();
        TimeUnit.SECONDS.sleep(1);
    }
}
