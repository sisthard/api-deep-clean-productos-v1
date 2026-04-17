package com.deep.clean.app.exception;

import jakarta.ws.rs.core.Response;

public class KafkaMessagingException extends AppException {
    public KafkaMessagingException(String message) {
        super(message, Response.Status.SERVICE_UNAVAILABLE);
    }
}
