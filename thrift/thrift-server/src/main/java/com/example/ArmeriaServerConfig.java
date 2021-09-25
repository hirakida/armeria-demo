package com.example;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.hello.Hello;
import com.example.thrift.Calculator;
import com.example.thrift.Operation;
import com.example.thrift.Work;

import com.linecorp.armeria.common.thrift.ThriftSerializationFormats;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.server.thrift.THttpService;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;
import com.linecorp.armeria.spring.DocServiceConfigurator;

@Configuration
public class ArmeriaServerConfig {
    @Bean
    public ArmeriaServerConfigurator calculatorConfigurator(Calculator.AsyncIface calculatorService) {
        return server -> server.route()
                               .path("/calculator")
                               .defaultServiceName("Calculator")
                               .decorator(LoggingService.newDecorator())
                               .accessLogWriter(AccessLogWriter.combined(), false)
                               .build(THttpService.of(calculatorService));
    }

    @Bean
    public ArmeriaServerConfigurator helloConfigurator(Hello.AsyncIface helloService) {
        return server -> server.route()
                               .path("/hello")
                               .defaultServiceName("Hello")
                               .decorator(LoggingService.newDecorator())
                               .accessLogWriter(AccessLogWriter.combined(), false)
                               .build(THttpService.ofFormats(helloService,
                                                             ThriftSerializationFormats.BINARY,
                                                             ThriftSerializationFormats.COMPACT,
                                                             ThriftSerializationFormats.JSON,
                                                             ThriftSerializationFormats.TEXT));
    }

    @Bean
    public DocServiceConfigurator docServiceConfigurator() {
        List<?> requests = List.of(new Calculator.add_args(1, 2),
                                   new Calculator.calculate_args(1, new Work(5, 3, Operation.SUBTRACT)));
        return builder -> builder.exampleRequests(requests);
    }
}
