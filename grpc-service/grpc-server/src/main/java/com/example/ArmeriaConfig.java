package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.CalculatorOuterClass.CalculatorRequest;
import com.example.CalculatorOuterClass.CalculatorRequest.OperationType;

import com.linecorp.armeria.common.grpc.GrpcSerializationFormats;
import com.linecorp.armeria.server.grpc.GrpcService;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;
import com.linecorp.armeria.spring.GrpcServiceRegistrationBean;

@Configuration
public class ArmeriaConfig {

    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator() {
        return builder -> {
            builder.decorator(LoggingService.newDecorator());
            builder.accessLogWriter(AccessLogWriter.combined(), false);
        };
    }

    @Bean
    public GrpcServiceRegistrationBean calculatorServiceRegistration(CalculatorService calculatorService) {
        return new GrpcServiceRegistrationBean()
                .setServiceName(CalculatorGrpc.SERVICE_NAME)
                .setService(GrpcService.builder()
                                       .addService(calculatorService)
                                       .supportedSerializationFormats(GrpcSerializationFormats.values())
                                       .enableUnframedRequests(true)
                                       .build())
                .addExampleRequests(CalculatorGrpc.SERVICE_NAME,
                                    "Calculate",
                                    CalculatorRequest.newBuilder()
                                                     .setNumber1(2)
                                                     .setNumber2(3)
                                                     .setOperation(OperationType.MULTIPLY)
                                                     .build());
    }
}
