package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.CalculatorServiceGrpc.CalculatorServiceBlockingStub;

import com.linecorp.armeria.client.grpc.GrpcClients;
import com.linecorp.armeria.client.logging.LoggingClient;
import com.linecorp.armeria.common.logging.LogLevel;

@Configuration
public class ArmeriaClientConfig {
    @Bean
    public CalculatorServiceBlockingStub calculatorServiceBlockingStub() {
        return GrpcClients.builder("http://127.0.0.1:8080/")
                          .decorator(LoggingClient.builder()
                                                  .requestLogLevel(LogLevel.INFO)
                                                  .successfulResponseLogLevel(LogLevel.INFO)
                                                  .newDecorator())
                          .build(CalculatorServiceBlockingStub.class);
    }
}
