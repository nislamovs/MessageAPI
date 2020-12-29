package com.consumer.smsConsumer.domain.exceptions;

public class VonageTokenAuthException extends RuntimeException {

    public VonageTokenAuthException(String msg) {
        super(msg);
    }

    public VonageTokenAuthException(String msg, Throwable t) {
        super(msg, t);
    }
}