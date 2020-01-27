package com.example.webcrawler.controller;

import com.example.webcrawler.exception.ErrorResponse;
import com.example.webcrawler.exception.InvalidURLException;
import com.example.webcrawler.exception.MaximumDepthExceededException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.net.UnknownHostException;

@Slf4j
@RestControllerAdvice(assignableTypes = CrawlerController.class)
public class CrawlerControllerAdvice {

    public static final String INVALID_URL_ERROR_CODE="100100";
    public static final String MAXIMUM_DEPTH_EXCEEDED="100200";
    public static final String GENERIC_ERROR_PROCESSING_REQUEST="900100";

    @ExceptionHandler(InvalidURLException.class)
    public ResponseEntity<ErrorResponse> handle(InvalidURLException e) {
        log.error("Error processing request to crawl, invalid url found", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(INVALID_URL_ERROR_CODE,e.getMessage()));
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponse> handle(IOException e) {
        log.error("Unknown host ", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(GENERIC_ERROR_PROCESSING_REQUEST,"Could not reach Host " + e.getMessage()));
    }

    @ExceptionHandler(MaximumDepthExceededException.class)
    public ResponseEntity<ErrorResponse> handle(MaximumDepthExceededException e) {
        log.error("Unknown host ", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(MAXIMUM_DEPTH_EXCEEDED, e.getMessage()));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handle(Exception e) {
        log.error("There is a generic error ", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(GENERIC_ERROR_PROCESSING_REQUEST,e.getMessage()));
    }



}
