package com.deep.clean.app.exception;

import jakarta.ws.rs.core.Response;

public class ConflictException extends AppException {
    public ConflictException(String message) {
        super(message, Response.Status.CONFLICT);
    }
}
