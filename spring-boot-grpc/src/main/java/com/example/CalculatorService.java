package com.example;

import org.springframework.stereotype.Service;

import com.example.CalculatorOuterClass.CalculatorRequest;
import com.example.CalculatorOuterClass.CalculatorResponse;

import io.grpc.stub.StreamObserver;

@Service
public class CalculatorService extends CalculatorGrpc.CalculatorImplBase {

    @Override
    public void calculate(CalculatorRequest request, StreamObserver<CalculatorResponse> responseObserver) {
        CalculatorResponse.Builder builder = CalculatorResponse.newBuilder();
        switch (request.getOperation()) {
            case ADD:
                builder.setResult(request.getNumber1() + request.getNumber2());
                break;
            case SUBTRACT:
                builder.setResult(request.getNumber1() - request.getNumber2());
                break;
            case MULTIPLY:
                builder.setResult(request.getNumber1() * request.getNumber2());
                break;
            case DIVIDE:
                builder.setResult(request.getNumber1() / request.getNumber2());
                break;
            case UNRECOGNIZED:
                break;
        }
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
