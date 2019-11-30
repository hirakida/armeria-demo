package com.example;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.thrift.Calculator;
import com.example.thrift.Operation;
import com.example.thrift.Work;

import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.server.thrift.THttpService;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;
import com.linecorp.armeria.spring.ThriftServiceRegistrationBean;

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
    public ThriftServiceRegistrationBean calculatorServiceRegistration(
            Calculator.AsyncIface calculatorService) {
        return new ThriftServiceRegistrationBean()
                .setServiceName("Calculator")
                .setPath("/calculator")
                .setService(THttpService.of(calculatorService))
                .setExampleRequests(List.of(new Calculator.add_args(1, 2),
                                            new Calculator.calculate_args(1,
                                                                          new Work(5, 3, Operation.SUBTRACT))));
    }
}
