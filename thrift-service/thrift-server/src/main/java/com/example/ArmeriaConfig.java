package com.example;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
                                            new Calculator.subtract_args(5, 2)));
    }
}
