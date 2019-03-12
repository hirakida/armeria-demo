package com.example;

import org.apache.thrift.async.AsyncMethodCallback;
import org.springframework.stereotype.Component;

@Component
public class CalculatorService implements Calculator.AsyncIface {

    @Override
    public void add(int n1, int n2, AsyncMethodCallback<Integer> resultHandler) {
        resultHandler.onComplete(n1 + n2);
    }

    @Override
    public void subtract(int n1, int n2, AsyncMethodCallback<Integer> resultHandler) {
        resultHandler.onComplete(n1 - n2);
    }
}
