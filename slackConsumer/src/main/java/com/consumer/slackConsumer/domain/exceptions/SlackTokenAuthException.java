package com.consumer.slackConsumer.domain.exceptions;

public class SlackTokenAuthException extends RuntimeException {

    public SlackTokenAuthException(String msg) {
        super(msg);
    }

    public SlackTokenAuthException(String msg, Throwable t) {
        super(msg, t);
    }
}