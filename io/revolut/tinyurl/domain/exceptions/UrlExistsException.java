package io.revolut.tinyurl.domain.exceptions;

public class UrlExistsException extends RuntimeException {
    public UrlExistsException(String message) {
        super(message);
    }
}
