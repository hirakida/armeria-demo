package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.Calculator.CalculatorRequest;
import com.example.Calculator.CalculatorRequest.OperationType;
import com.example.Hello.HelloRequest;
import com.example.interceptor.ServerInterceptorImpl;
import com.example.service.CalculatorService;
import com.example.service.HelloService;

import com.linecorp.armeria.server.docs.DocServiceFilter;
import com.linecorp.armeria.server.grpc.GrpcService;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;
import com.linecorp.armeria.spring.DocServiceConfigurator;

import io.grpc.ServerInterceptors;
import io.grpc.protobuf.services.ProtoReflectionService;
import io.grpc.reflection.v1alpha.ServerReflectionGrpc;

@Configuration
public class ArmeriaServerConfig {
    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator(CalculatorService calculatorService,
                                                               HelloService helloService) {
        GrpcService grpcService = GrpcService.builder()
                                             .addService(calculatorService)
                                             .addService(ServerInterceptors.intercept(helloService,
                                                                                      new ServerInterceptorImpl()))
                                             .addService(ProtoReflectionService.newInstance())
                                             .enableUnframedRequests(true)
                                             .build();
        return builder -> builder.service(grpcService)
                                 .decorator(LoggingService.newDecorator())
                                 .accessLogWriter(AccessLogWriter.combined(), false);
    }

    @Bean
    public DocServiceConfigurator docServiceConfigurator() {
        CalculatorRequest calculatorRequest = CalculatorRequest.newBuilder()
                                                               .setNumber1(2)
                                                               .setNumber2(3)
                                                               .setOperation(OperationType.MULTIPLY)
                                                               .build();
        HelloRequest helloRequest = HelloRequest.newBuilder()
                                                .setName("hirakida")
                                                .build();
        return builder -> builder.exampleRequests(CalculatorServiceGrpc.SERVICE_NAME,
                                                  "Calculate",
                                                  calculatorRequest)
                                 .exampleRequests(HelloServiceGrpc.SERVICE_NAME,
                                                  "HelloUnary",
                                                  helloRequest)
                                 .exclude(DocServiceFilter.ofServiceName(ServerReflectionGrpc.SERVICE_NAME));
    }
}
