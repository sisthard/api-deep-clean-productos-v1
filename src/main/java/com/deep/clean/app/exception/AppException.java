package com.deep.clean.app.exception;

import jakarta.ws.rs.core.Response;
import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
    private final Response.Status status;

    public AppException(String message) {
        this(message, Response.Status.INTERNAL_SERVER_ERROR);
    }

    public AppException(String message, Response.Status status) {
        super(message);
        this.status = status;
    }
}