package com.example.webcrawler.exception;

public class MaximumDepthExceededException extends RuntimeException {

    private String errorMessage;
    public MaximumDepthExceededException(String errorMessage){super(errorMessage);}
}
