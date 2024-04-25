package com.umitsen.onlinebookstore.Exception;




public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
