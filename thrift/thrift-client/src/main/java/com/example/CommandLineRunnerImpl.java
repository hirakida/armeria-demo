package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.thrift.Calculator;
import com.example.thrift.Operation;
import com.example.thrift.Work;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final Calculator.Iface calculator;

    @Override
    public void run(String... args) throws Exception {
        int result = calculator.add(1, 2);
        log.info("result={}", result);

        result = calculator.calculate(1, new Work(5, 3, Operation.SUBTRACT));
        log.info("result={}", result);
    }
}
