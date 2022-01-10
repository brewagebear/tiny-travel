package io.dailyworker.flight.exceptions;

public class NoSuchCodeException extends RuntimeException {
    public NoSuchCodeException() {
        super();
    }

    public NoSuchCodeException(String message) {
        super(message);
    }
}
