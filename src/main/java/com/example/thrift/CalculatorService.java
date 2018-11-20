package com.example.thrift;

import org.springframework.stereotype.Component;

import com.example.Calculator;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CalculatorService implements Calculator.Iface {

    @Override
    public void ping() {
        log.info("ping()");
    }

    @Override
    public int add(int n1, int n2) {
        return n1 + n2;
    }
}
