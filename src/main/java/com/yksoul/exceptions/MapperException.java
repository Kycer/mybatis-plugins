package com.yksoul.exceptions;

/**
 * @author yk
 * @version 1.0 Date: 2018-01-26
 */
public class MapperException extends RuntimeException {
    public MapperException() {
    }

    public MapperException(String message) {
        super(message);
    }

    public MapperException(String message, Throwable cause) {
        super(message, cause);
    }

    public MapperException(Throwable cause) {
        super(cause);
    }
}