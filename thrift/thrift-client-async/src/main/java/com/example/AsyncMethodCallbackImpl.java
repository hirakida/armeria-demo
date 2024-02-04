package com.example;

import org.apache.thrift.async.AsyncMethodCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AsyncMethodCallbackImpl<T> implements AsyncMethodCallback<T> {
    private static final Logger logger = LoggerFactory.getLogger(AsyncMethodCallbackImpl.class);

    @Override
    public void onComplete(T response) {
        logger.info("response: {}", response);
    }

    @Override
    public void onError(Exception e) {
        logger.error("{}", e.getMessage(), e);
    }
}
