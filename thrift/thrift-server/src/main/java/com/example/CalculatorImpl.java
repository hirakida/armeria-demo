package com.example;

import org.apache.thrift.async.AsyncMethodCallback;
import org.springframework.stereotype.Component;

import com.example.thrift.Calculator;
import com.example.thrift.InvalidOperation;
import com.example.thrift.Work;

@Component
public class CalculatorImpl implements Calculator.AsyncIface {
    @Override
    public void add(int n1, int n2, AsyncMethodCallback<Integer> resultHandler) {
        resultHandler.onComplete(n1 + n2);
    }

    @Override
    public void calculate(int logid, Work work, AsyncMethodCallback<Integer> resultHandler)
            throws InvalidOperation {
        int val;
        switch (work.op) {
            case ADD:
                val = work.num1 + work.num2;
                break;
            case SUBTRACT:
                val = work.num1 - work.num2;
                break;
            case MULTIPLY:
                val = work.num1 * work.num2;
                break;
            case DIVIDE:
                if (work.num2 == 0) {
                    InvalidOperation io = new InvalidOperation();
                    io.whatOp = work.op.getValue();
                    io.why = "Cannot divide by 0";
                    throw io;
                }
                val = work.num1 / work.num2;
                break;
            default:
                InvalidOperation io = new InvalidOperation();
                io.whatOp = work.op.getValue();
                io.why = "Unknown operation";
                throw io;
        }
        resultHandler.onComplete(val);
    }
}
