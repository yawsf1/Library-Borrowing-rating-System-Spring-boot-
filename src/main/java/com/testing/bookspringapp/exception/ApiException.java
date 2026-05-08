package com.testing.bookspringapp.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiException {
    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timeStamp;

    public ApiException(String message,
                        HttpStatus httpStatus,
                        ZonedDateTime timeStamp) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timeStamp = timeStamp;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public ZonedDateTime getTimeStamp() {
        return timeStamp;
    }
}
