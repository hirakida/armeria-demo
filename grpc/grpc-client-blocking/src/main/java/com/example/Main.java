package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.Calculator.CalculatorRequest;
import com.example.Calculator.CalculatorRequest.OperationType;
import com.example.Calculator.CalculatorResponse;
import com.example.CalculatorServiceGrpc.CalculatorServiceBlockingStub;

import com.linecorp.armeria.client.grpc.GrpcClients;
import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.common.logging.LogLevel;

public final class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        final CalculatorServiceBlockingStub calculatorService =
                GrpcClients.builder("http://127.0.0.1:8080/")
                           .decorator(LoggingClient.builder()
                                                   .requestLogLevel(LogLevel.INFO)
                                                   .successfulResponseLogLevel(LogLevel.INFO)
                                                   .newDecorator())
                           .build(CalculatorServiceBlockingStub.class);

        final CalculatorRequest request1 = CalculatorRequest.newBuilder()
                                                            .setNumber1(1)
                                                            .setNumber2(2)
                                                            .setOperation(OperationType.ADD)
                                                            .build();
        CalculatorResponse response = calculatorService.calculate(request1);
        logger.info("result={}", response.getResult());

        final CalculatorRequest request2 = CalculatorRequest.newBuilder()
                                                            .setNumber1(2)
                                                            .setNumber2(3)
                                                            .setOperation(OperationType.MULTIPLY)
                                                            .build();
        response = calculatorService.calculate(request2);
        logger.info("result={}", response.getResult());
    }
}
