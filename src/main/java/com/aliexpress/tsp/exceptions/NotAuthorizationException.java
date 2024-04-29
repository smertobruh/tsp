package com.aliexpress.tsp.exceptions;

public class NotAuthorizationException extends RuntimeException {
    public NotAuthorizationException() {
    }

    public NotAuthorizationException(String message) {
        super(message);
    }

    public NotAuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotAuthorizationException(Throwable cause) {
        super(cause);
    }
}
