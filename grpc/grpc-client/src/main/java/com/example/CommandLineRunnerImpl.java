package com.example;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.Calculator.CalculatorRequest;
import com.example.Calculator.CalculatorRequest.OperationType;
import com.example.CalculatorServiceGrpc.CalculatorServiceBlockingStub;
import com.example.Hello.HelloRequest;
import com.example.HelloServiceGrpc.HelloServiceStub;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final CalculatorServiceBlockingStub calculatorService;
    private final HelloServiceStub helloService;

    @Override
    public void run(String... args) {
        calculator();
        hello();
    }

    private void calculator() {
        CalculatorRequest request1 = CalculatorRequest.newBuilder()
                                                      .setNumber1(1)
                                                      .setNumber2(2)
                                                      .setOperation(OperationType.ADD)
                                                      .build();
        double result = calculatorService.calculate(request1).getResult();
        log.info("result={}", result);

        CalculatorRequest request2 = CalculatorRequest.newBuilder()
                                                      .setNumber1(2)
                                                      .setNumber2(3)
                                                      .setOperation(OperationType.MULTIPLY)
                                                      .build();
        result = calculatorService.calculate(request2).getResult();
        log.info("result={}", result);
    }

    private void hello() {
        // Unary
        HelloRequest request1 = HelloRequest.newBuilder().setName("hirakida1").build();
        helloService.helloUnary(request1, new HelloStreamObserver());
        sleep(1);

        // Server Streaming
        HelloRequest request2 = HelloRequest.newBuilder().setName("hirakida2").build();
        helloService.helloServerStreaming(request2, new HelloStreamObserver());
        sleep(1);

        // Client Streaming
        StreamObserver<HelloRequest> requestStream1 =
                helloService.helloClientStreaming(new HelloStreamObserver());
        HelloRequest request3_1 = HelloRequest.newBuilder().setName("hirakida3-1").build();
        HelloRequest request3_2 = HelloRequest.newBuilder().setName("hirakida3-2").build();
        HelloRequest request3_3 = HelloRequest.newBuilder().setName("hirakida3-3").build();
        requestStream1.onNext(request3_1);
        requestStream1.onNext(request3_2);
        requestStream1.onNext(request3_3);
        requestStream1.onCompleted();
        sleep(1);

        // Bidirectional Streaming
        StreamObserver<HelloRequest> requestStream2 =
                helloService.helloBidirectionalStreaming(new HelloStreamObserver());
        HelloRequest request4_1 = HelloRequest.newBuilder().setName("hirakida4-1").build();
        HelloRequest request4_2 = HelloRequest.newBuilder().setName("hirakida4-2").build();
        HelloRequest request4_3 = HelloRequest.newBuilder().setName("hirakida4-3").build();
        requestStream2.onNext(request4_1);
        requestStream2.onNext(request4_2);
        requestStream2.onNext(request4_3);
        requestStream2.onCompleted();
        sleep(1);
    }

    private static void sleep(long timeout) {
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            log.error("timeout", e);
        }
    }
}
