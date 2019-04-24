package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.CalculatorGrpc.CalculatorBlockingStub;
import com.example.CalculatorOuterClass.CalculatorRequest;
import com.example.CalculatorOuterClass.CalculatorRequest.OperationType;

import com.linecorp.armeria.client.ClientBuilder;
import com.linecorp.armeria.client.logging.LoggingClient;

@SpringBootApplication
public class GrpcClientApplication implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(GrpcClientApplication.class);

    @Override
    public void run(String... args) {

        CalculatorBlockingStub calculator = new ClientBuilder("gproto+http://127.0.0.1:8080/")
                .defaultResponseTimeoutMillis(10000)
                .decorator(LoggingClient.newDecorator())
                .build(CalculatorBlockingStub.class);

        double result = calculator.calculate(CalculatorRequest.newBuilder()
                                                              .setNumber1(1)
                                                              .setNumber2(2)
                                                              .setOperation(OperationType.ADD)
                                                              .build())
                                  .getResult();
        log.info("result={}", result);

        result = calculator.calculate(CalculatorRequest.newBuilder()
                                                       .setNumber1(2)
                                                       .setNumber2(3)
                                                       .setOperation(OperationType.MULTIPLY)
                                                       .build())
                           .getResult();
        log.info("result={}", result);
    }

    public static void main(String[] args) {
        SpringApplication.run(GrpcClientApplication.class, args);
    }
}
