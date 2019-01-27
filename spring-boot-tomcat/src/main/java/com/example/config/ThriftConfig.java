package com.example.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.Calculator;
import com.example.Calculator.ping_args;

import com.linecorp.armeria.server.thrift.THttpService;
import com.linecorp.armeria.spring.ThriftServiceRegistrationBean;

@Configuration
public class ThriftConfig {

    @Bean
    public ThriftServiceRegistrationBean helloService(Calculator.Iface calculatorService) {
        return new ThriftServiceRegistrationBean()
                .setServiceName("calculator")
                .setPath("/calculator")
                .setService(THttpService.of(calculatorService))
                .setExampleRequests(List.of(new ping_args(), new Calculator.add_args(1, 2)));
    }
}
