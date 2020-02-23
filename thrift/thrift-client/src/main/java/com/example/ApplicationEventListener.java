package com.example;

import org.apache.thrift.TException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.thrift.Calculator;
import com.example.thrift.Operation;
import com.example.thrift.Work;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApplicationEventListener {
    private final Calculator.Iface calculator;

    @EventListener(ApplicationReadyEvent.class)
    public void readyEvent() throws TException {
        int result = calculator.add(1, 2);
        log.info("result={}", result);

        result = calculator.calculate(1, new Work(5, 3, Operation.SUBTRACT));
        log.info("result={}", result);
    }
}
