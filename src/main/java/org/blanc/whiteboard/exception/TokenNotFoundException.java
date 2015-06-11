package org.blanc.whiteboard.exception;

public class TokenNotFoundException extends BaseWebApplicationException {

	private static final long serialVersionUID = 1L;
    public TokenNotFoundException() {
        super(404, "Token Not Found", "No token could be found for that Id");
    }
}
