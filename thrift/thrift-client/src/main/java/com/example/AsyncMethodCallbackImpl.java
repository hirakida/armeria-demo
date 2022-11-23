package com.example;

import org.apache.thrift.async.AsyncMethodCallback;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AsyncMethodCallbackImpl<T> implements AsyncMethodCallback<T> {
    @Override
    public void onComplete(T response) {
        log.info("response: {}", response);
    }

    @Override
    public void onError(Exception e) {
        log.error("{}", e.getMessage(), e);
    }
}
