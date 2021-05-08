package com.example;

import java.time.Duration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.thrift.Calculator;
import com.example.thrift.Operation;
import com.example.thrift.Work;

import com.linecorp.armeria.client.Clients;
import com.linecorp.armeria.client.logging.LoggingRpcClient;
import com.linecorp.armeria.common.logging.LogLevel;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CommandLineRunnerImpl implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        Calculator.Iface calculator =
                Clients.builder("tbinary+http://127.0.0.1:8080/calculator")
                       .responseTimeout(Duration.ofSeconds(10))
                       .rpcDecorator(LoggingRpcClient.builder()
                                                     .requestLogLevel(LogLevel.INFO)
                                                     .successfulResponseLogLevel(LogLevel.INFO)
                                                     .newDecorator())
                       .build(Calculator.Iface.class);

        int result = calculator.add(1, 2);
        log.info("result={}", result);

        result = calculator.calculate(1, new Work(5, 3, Operation.SUBTRACT));
        log.info("result={}", result);
    }
}
