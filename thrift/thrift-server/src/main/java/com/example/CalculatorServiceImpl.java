package com.example;

import org.apache.thrift.async.AsyncMethodCallback;
import org.springframework.stereotype.Component;

import com.example.thrift.CalculatorService;
import com.example.thrift.InvalidOperation;
import com.example.thrift.Work;

@Component
public class CalculatorServiceImpl implements CalculatorService.AsyncIface {
    @Override
    public void calculate(int logid, Work work, AsyncMethodCallback<Integer> resultHandler)
            throws InvalidOperation {
        int val;
        switch (work.op) {
            case ADD -> val = work.num1 + work.num2;
            case SUBTRACT -> val = work.num1 - work.num2;
            case MULTIPLY -> val = work.num1 * work.num2;
            case DIVIDE -> {
                if (work.num2 == 0) {
                    throw new InvalidOperation()
                            .setWhatOp(work.op.getValue())
                            .setWhy("Cannot divide by 0");
                }
                val = work.num1 / work.num2;
            }
            default -> throw new InvalidOperation()
                    .setWhatOp(work.op.getValue())
                    .setWhy("Unknown operation");
        }
        resultHandler.onComplete(val);
    }
}
