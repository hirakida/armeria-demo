package com.example;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.hello.Hello;
import com.example.thrift.Calculator;

import com.linecorp.armeria.client.Clients;
import com.linecorp.armeria.client.logging.LoggingRpcClient;
import com.linecorp.armeria.common.logging.LogLevel;

@Configuration
public class ArmeriaClientConfig {
    @Bean
    public Calculator.Iface calculatorClient() {
        return Clients.builder("tbinary+http://127.0.0.1:8080/calculator")
                      .responseTimeout(Duration.ofSeconds(10))
                      .rpcDecorator(LoggingRpcClient.builder()
                                                    .requestLogLevel(LogLevel.INFO)
                                                    .successfulResponseLogLevel(LogLevel.INFO)
                                                    .newDecorator())
                      .build(Calculator.Iface.class);
    }

    public static Hello.Iface buildHelloClient(String format) {
        return Clients.builder(format + "+http://127.0.0.1:8080/hello")
                      .responseTimeout(Duration.ofSeconds(10))
                      .rpcDecorator(LoggingRpcClient.builder()
                                                    .requestLogLevel(LogLevel.INFO)
                                                    .successfulResponseLogLevel(LogLevel.INFO)
                                                    .newDecorator())
                      .build(Hello.Iface.class);
    }
}
