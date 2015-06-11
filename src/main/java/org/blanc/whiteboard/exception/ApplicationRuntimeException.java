package org.blanc.whiteboard.exception;


public class ApplicationRuntimeException extends BaseWebApplicationException {

	private static final long serialVersionUID = 1L;
    public ApplicationRuntimeException(String applicationMessage) {
        super(500, "Internal System error", applicationMessage);
    }
}
