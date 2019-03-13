package com.rw.http;

public abstract class StatusCarryingException extends Exception {

    public StatusCarryingException() {
    }

    public StatusCarryingException(String message) {
        super(message);
    }

    public StatusCarryingException(String message, Throwable e) {
        super(message, e);
    }

    public abstract int getStatusCode();
}
