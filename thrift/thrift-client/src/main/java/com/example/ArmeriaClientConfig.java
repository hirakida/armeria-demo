package com.example;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.thrift.CalculatorService;

import com.linecorp.armeria.client.logging.LoggingRpcClient;
import com.linecorp.armeria.client.thrift.ThriftClients;
import com.linecorp.armeria.common.logging.LogLevel;

@Configuration
public class ArmeriaClientConfig {
    @Bean
    public CalculatorService.AsyncIface calculatorClient() {
        return ThriftClients.builder("http://127.0.0.1:8080")
                            .path("/calculator")
                            .responseTimeout(Duration.ofSeconds(10))
                            .rpcDecorator(LoggingRpcClient.builder()
                                                          .requestLogLevel(LogLevel.INFO)
                                                          .successfulResponseLogLevel(LogLevel.INFO)
                                                          .newDecorator())
                            .build(CalculatorService.AsyncIface.class);
    }
}
