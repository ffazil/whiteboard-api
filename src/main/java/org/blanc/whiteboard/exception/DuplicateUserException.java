package org.blanc.whiteboard.exception;

public class DuplicateUserException extends BaseWebApplicationException {

	private static final long serialVersionUID = 1L;
    public DuplicateUserException() {
        super(409, "User already exists", "An attempt was made to create a user that already exists");
    }
}

