package com.example;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.thrift.Calculator;

import com.linecorp.armeria.client.Clients;
import com.linecorp.armeria.client.logging.LoggingClient;

@Configuration
public class ThriftClientConfig {

    @Bean
    public Calculator.Iface calculator() {
        return Clients.builder("tbinary+http://127.0.0.1:8080/calculator")
                      .responseTimeout(Duration.ofSeconds(10))
                      .decorator(LoggingClient.newDecorator())
                      .build(Calculator.Iface.class);
    }
}
