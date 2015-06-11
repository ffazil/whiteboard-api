package org.blanc.whiteboard.exception;

import org.blanc.whiteboard.rest.resource.ErrorResponse;
import org.springframework.security.access.AccessDeniedException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class AccessDeniedExceptionMapper implements ExceptionMapper<AccessDeniedException> {


    public Response toResponse(AccessDeniedException exception) {
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(new ErrorResponse("401", "You do not have the appropriate privileges to access this resource",
                        exception.getMessage()))
                .type(MediaType.APPLICATION_JSON).
                        build();
    }
}
