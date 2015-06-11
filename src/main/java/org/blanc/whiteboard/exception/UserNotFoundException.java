package org.blanc.whiteboard.exception;

public class UserNotFoundException extends BaseWebApplicationException {

	private static final long serialVersionUID = 1L;
    public UserNotFoundException() {
        super(404, "User Not Found", "No User could be found for that Id");
    }
}
