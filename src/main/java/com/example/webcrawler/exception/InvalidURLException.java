package com.example.webcrawler.exception;


public class InvalidURLException extends RuntimeException {

    private String errorMessage;
    public InvalidURLException(String errorMessage){super(errorMessage); }
}
