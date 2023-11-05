package com.example;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.hello.HelloRequest;
import com.example.hello.HelloService;
import com.example.thrift.CalculatorService;
import com.example.thrift.Operation;
import com.example.thrift.Work;

import com.linecorp.armeria.common.metric.PrometheusMeterRegistries;
import com.linecorp.armeria.common.thrift.ThriftSerializationFormats;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.server.thrift.THttpService;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;
import com.linecorp.armeria.spring.DocServiceConfigurator;

import io.micrometer.prometheus.PrometheusMeterRegistry;

@Configuration
public class ArmeriaServerConfig {
    @Bean
    public ArmeriaServerConfigurator calculatorConfigurator(CalculatorService.AsyncIface calculatorService) {
        return server -> server.route()
                               .path("/calculator")
                               .defaultServiceName("CalculatorService")
                               .decorator(LoggingService.newDecorator())
                               .accessLogWriter(AccessLogWriter.combined(), false)
                               .build(THttpService.of(calculatorService))
                               .meterRegistry(prometheusMeterRegistry());
    }

    @Bean
    public ArmeriaServerConfigurator helloConfigurator(HelloService.AsyncIface helloService) {
        return server -> server.route()
                               .path("/hello")
                               .defaultServiceName("HelloService")
                               .decorator(LoggingService.newDecorator())
                               .accessLogWriter(AccessLogWriter.combined(), false)
                               .build(THttpService.ofFormats(helloService,
                                                             ThriftSerializationFormats.BINARY,
                                                             ThriftSerializationFormats.COMPACT,
                                                             ThriftSerializationFormats.JSON,
                                                             ThriftSerializationFormats.TEXT))
                               .meterRegistry(prometheusMeterRegistry());
    }

    @Bean
    public PrometheusMeterRegistry prometheusMeterRegistry() {
        return PrometheusMeterRegistries.defaultRegistry();
    }

    @Bean
    public DocServiceConfigurator docServiceConfigurator() {
        final List<?> requests = List.of(
                new CalculatorService.calculate_args(1, new Work(5, 3, Operation.SUBTRACT)),
                new HelloService.hello1_args("hirakida"),
                new HelloService.hello2_args(new HelloRequest("hirakida")));
        return builder -> builder.exampleRequests(requests);
    }
}
