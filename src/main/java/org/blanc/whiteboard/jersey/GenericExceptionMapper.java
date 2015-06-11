package org.blanc.whiteboard.jersey;

import org.blanc.whiteboard.exception.ApplicationRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Exception> {

    private static Logger LOG = LoggerFactory.getLogger(GenericExceptionMapper.class);

    public Response toResponse(Exception exception) {
        if (exception instanceof WebApplicationException) {
            LOG.info("Web Application Exception: " + exception);
            return ((WebApplicationException) exception).getResponse();
        }
        LOG.error("Internal Server Error: " + exception);
        LOG.error("Internal Server Error: " + exception.getCause());
        return new ApplicationRuntimeException("Server error").getResponse();
    }
}
