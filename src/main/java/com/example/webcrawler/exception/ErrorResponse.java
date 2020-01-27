package com.example.webcrawler.exception;

public class ErrorResponse {

    private String code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;

    public ErrorResponse() {}

    public ErrorResponse(String code,String message) {
        this.code = code;
        this.message=message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
