package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.CalculatorOuterClass.CalculatorRequest;
import com.example.CalculatorOuterClass.CalculatorRequest.OperationType;

import com.linecorp.armeria.common.grpc.GrpcSerializationFormats;
import com.linecorp.armeria.server.docs.DocServiceBuilder;
import com.linecorp.armeria.server.grpc.GrpcServiceBuilder;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;

@Configuration
public class ArmeriaConfig {

    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator(CalculatorService calculatorService) {
        return builder -> {
            builder.service(new GrpcServiceBuilder().addService(calculatorService)
                                                    .supportedSerializationFormats(
                                                            GrpcSerializationFormats.values())
                                                    .enableUnframedRequests(true)
                                                    .build());
            builder.serviceUnder("/docs",
                                 new DocServiceBuilder()
                                         .exampleRequestForMethod(CalculatorGrpc.SERVICE_NAME,
                                                                  "Calculate",
                                                                  CalculatorRequest
                                                                          .newBuilder()
                                                                          .setNumber1(2)
                                                                          .setNumber2(3)
                                                                          .setOperation(OperationType.MULTIPLY)
                                                                          .build())
                                         .build());
            builder.decorator(LoggingService.newDecorator());
            builder.accessLogWriter(AccessLogWriter.combined(), false);
        };
    }
}
