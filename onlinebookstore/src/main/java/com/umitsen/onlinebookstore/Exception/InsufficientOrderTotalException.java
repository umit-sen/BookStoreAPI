package com.umitsen.onlinebookstore.Exception;

public class InsufficientOrderTotalException extends RuntimeException {
    public InsufficientOrderTotalException(String message) {
        super(message);
    }
}
