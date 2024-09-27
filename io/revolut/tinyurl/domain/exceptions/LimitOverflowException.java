package io.revolut.tinyurl.domain.exceptions;

public class LimitOverflowException extends RuntimeException {
    public LimitOverflowException(String message) {
        super(message);
    }
}
