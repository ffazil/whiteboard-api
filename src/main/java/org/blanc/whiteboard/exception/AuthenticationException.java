package org.blanc.whiteboard.exception;

public class AuthenticationException extends BaseWebApplicationException {

	private static final long serialVersionUID = 1L;
    public AuthenticationException() {
        super(401, "Authentication Error", "Authentication Error. The username or password were incorrect");
    }
}