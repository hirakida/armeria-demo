package com.example;

import java.time.Duration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.thrift.Calculator;
import com.example.thrift.Operation;
import com.example.thrift.Work;

import com.linecorp.armeria.client.Clients;
import com.linecorp.armeria.client.logging.LoggingClient;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class ThriftClientApplication implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        Calculator.Iface calculator = Clients.builder("tbinary+http://127.0.0.1:8080/calculator")
                                             .responseTimeout(Duration.ofSeconds(10))
                                             .decorator(LoggingClient.newDecorator())
                                             .build(Calculator.Iface.class);

        int result = calculator.add(1, 2);
        log.info("result={}", result);

        result = calculator.calculate(1, new Work(5, 3, Operation.SUBTRACT));
        log.info("result={}", result);
    }

    public static void main(String[] args) {
        SpringApplication.run(ThriftClientApplication.class, args);
    }
}
