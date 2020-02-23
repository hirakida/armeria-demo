package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.Calculator.CalculatorRequest;
import com.example.Calculator.CalculatorRequest.OperationType;
import com.example.Hello.HelloRequest;
import com.example.service.CalculatorService;
import com.example.service.HelloService;

import com.linecorp.armeria.common.grpc.GrpcSerializationFormats;
import com.linecorp.armeria.server.grpc.GrpcService;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;
import com.linecorp.armeria.spring.GrpcServiceRegistrationBean;

import io.grpc.ServerInterceptors;

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
                .setServiceName(CalculatorServiceGrpc.SERVICE_NAME)
                .setService(GrpcService.builder()
                                       .addService(calculatorService)
                                       .supportedSerializationFormats(GrpcSerializationFormats.values())
                                       .enableUnframedRequests(true)
                                       .build())
                .addExampleRequests(CalculatorServiceGrpc.SERVICE_NAME,
                                    "Calculate",
                                    CalculatorRequest.newBuilder()
                                                     .setNumber1(2)
                                                     .setNumber2(3)
                                                     .setOperation(OperationType.MULTIPLY)
                                                     .build());
    }

    @Bean
    public GrpcServiceRegistrationBean helloServiceRegistration(HelloService helloService) {
        return new GrpcServiceRegistrationBean()
                .setServiceName(HelloServiceGrpc.SERVICE_NAME)
                .setService(GrpcService.builder()
                                       .addService(ServerInterceptors.intercept(helloService,
                                                                                new ServerInterceptorImpl()))
                                       .supportedSerializationFormats(GrpcSerializationFormats.values())
                                       .enableUnframedRequests(true)
                                       .build())
                .addExampleRequests(HelloServiceGrpc.SERVICE_NAME,
                                    "SayHello1",
                                    HelloRequest.newBuilder()
                                                .setName("hirakida")
                                                .build())
                .addExampleRequests(HelloServiceGrpc.SERVICE_NAME,
                                    "SayHello2",
                                    HelloRequest.newBuilder()
                                                .setName("hirakida")
                                                .build());
    }
}
