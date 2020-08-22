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
import com.linecorp.armeria.spring.DocServiceConfigurator;

import io.grpc.ServerInterceptors;

@Configuration
public class ArmeriaServerConfig {

    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator(CalculatorService calculatorService,
                                                               HelloService helloService) {
        return server ->
                server.service(GrpcService.builder()
                                          .addService(calculatorService)
                                          .supportedSerializationFormats(GrpcSerializationFormats.values())
                                          .enableUnframedRequests(true)
                                          .build())
                      .service(GrpcService.builder()
                                          .addService(ServerInterceptors.intercept(helloService,
                                                                                   new ServerInterceptorImpl()))
                                          .supportedSerializationFormats(GrpcSerializationFormats.values())
                                          .enableUnframedRequests(true)
                                          .build())
                      .decorator(LoggingService.newDecorator())
                      .accessLogWriter(AccessLogWriter.combined(), false);
    }

    @Bean
    public DocServiceConfigurator docServiceConfigurator() {
        return builder ->
                builder.exampleRequests(CalculatorServiceGrpc.SERVICE_NAME,
                                        "Calculate",
                                        CalculatorRequest.newBuilder()
                                                         .setNumber1(2)
                                                         .setNumber2(3)
                                                         .setOperation(OperationType.MULTIPLY)
                                                         .build())
                       .exampleRequests(HelloServiceGrpc.SERVICE_NAME,
                                        "SayHello1",
                                        HelloRequest.newBuilder()
                                                    .setName("hirakida")
                                                    .build())
                       .exampleRequests(HelloServiceGrpc.SERVICE_NAME,
                                        "SayHello2",
                                        HelloRequest.newBuilder()
                                                    .setName("hirakida")
                                                    .build());
    }
}
