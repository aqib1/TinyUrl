package io.revolut.tinyurl.domain.exceptions;

public class TinyUrlCollisionException extends RuntimeException {
    public TinyUrlCollisionException(String message) {
        super(message);
    }
}
