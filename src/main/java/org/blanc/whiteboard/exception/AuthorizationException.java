package org.blanc.whiteboard.exception;

public class AuthorizationException extends BaseWebApplicationException {


	private static final long serialVersionUID = 1L;

	public AuthorizationException(String applicationMessage) {
        super(403, "Not authorized", applicationMessage);
    }

}
