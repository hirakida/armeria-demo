package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.CalculatorServiceGrpc.CalculatorServiceBlockingStub;
import com.example.HelloServiceGrpc.HelloServiceStub;

import com.linecorp.armeria.client.Clients;
import com.linecorp.armeria.client.logging.LoggingClient;

@Configuration
public class GrpcClientConfig {

    @Bean
    public CalculatorServiceBlockingStub calculatorServiceBlockingStub() {
        return Clients.builder("gproto+http://127.0.0.1:8080/")
                      .responseTimeoutMillis(10000)
                      .decorator(LoggingClient.newDecorator())
                      .build(CalculatorServiceBlockingStub.class);
    }

    @Bean
    public HelloServiceStub helloServiceStub() {
        return Clients.builder("gproto+http://127.0.0.1:8080/")
                      .responseTimeoutMillis(10000)
                      .decorator(LoggingClient.newDecorator())
                      .build(HelloServiceStub.class);
    }
}
