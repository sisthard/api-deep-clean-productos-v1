package com.deep.clean.app.exception;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Throwable> {

    private static final Logger LOG = Logger.getLogger(GlobalExceptionHandler.class);

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(Throwable exception) {
        // Extraemos la causa real para evitar el error "ArcUndeclaredThrowableException"
        Throwable cause = extractRealCause(exception);

        LOG.errorf("Procesando excepción: %s - Mensaje: %s", cause.getClass().getSimpleName(), cause.getMessage());

        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        String message = "Ha ocurrido un error inesperado en el servidor.";

        if (cause instanceof ConflictException conflictEx) {
            status = Response.Status.CONFLICT;
            message = conflictEx.getMessage();
        } else if (cause instanceof AppException appEx) {
            status = appEx.getStatus();
            message = appEx.getMessage();
        } else if (cause instanceof ConstraintViolationException cvEx) {
            status = Response.Status.BAD_REQUEST;
            message = cvEx.getConstraintViolations().stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .collect(Collectors.joining(", "));
        } else if (cause instanceof WebApplicationException webEx) {
            status = Response.Status.fromStatusCode(webEx.getResponse().getStatus());
            message = webEx.getMessage();
        }

        ErrorDetails error = new ErrorDetails(
                LocalDateTime.now(),
                message,
                uriInfo.getPath(),
                status.getStatusCode());

        return Response.status(status).entity(error).build();
    }

    private Throwable extractRealCause(Throwable e) {
        if (e.getCause() != null && (
                e instanceof jakarta.transaction.RollbackException ||
                e instanceof jakarta.persistence.PersistenceException ||
                e.getClass().getName().contains("ArcUndeclaredThrowableException"))) {
            return extractRealCause(e.getCause());
        }
        return e;
    }
}
