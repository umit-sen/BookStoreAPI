package com.umitsen.onlinebookstore.Exception;


public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String message) {
        super(message);
    }
}
