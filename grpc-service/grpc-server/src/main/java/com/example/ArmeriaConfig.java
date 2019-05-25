package com.example;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.CalculatorOuterClass.CalculatorRequest;
import com.example.CalculatorOuterClass.CalculatorRequest.OperationType;

import com.linecorp.armeria.common.grpc.GrpcSerializationFormats;
import com.linecorp.armeria.server.grpc.GrpcServiceBuilder;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;
import com.linecorp.armeria.spring.GrpcServiceRegistrationBean;
import com.linecorp.armeria.spring.GrpcServiceRegistrationBean.ExampleRequest;

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
                .setServiceName("calculatorService")
                .setService(new GrpcServiceBuilder()
                                    .addService(calculatorService)
                                    .supportedSerializationFormats(GrpcSerializationFormats.values())
                                    .enableUnframedRequests(true)
                                    .build())
                .setExampleRequests(List.of(ExampleRequest.of(
                        CalculatorGrpc.SERVICE_NAME,
                        "Calculate",
                        CalculatorRequest.newBuilder()
                                         .setNumber1(2)
                                         .setNumber2(3)
                                         .setOperation(OperationType.MULTIPLY)
                                         .build()
                )));
    }
}
