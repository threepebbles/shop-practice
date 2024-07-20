package com.example.practiceShop.exception;

public class LoggedOutAccessToken extends RuntimeException {
    public LoggedOutAccessToken() {
        super();
    }

    public LoggedOutAccessToken(String message) {
        super(message);
    }

    public LoggedOutAccessToken(String message, Throwable cause) {
        super(message, cause);
    }

    public LoggedOutAccessToken(Throwable cause) {
        super(cause);
    }

    protected LoggedOutAccessToken(String message, Throwable cause, boolean enableSuppression,
                                   boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
