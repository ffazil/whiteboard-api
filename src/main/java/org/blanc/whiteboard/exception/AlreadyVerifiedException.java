package org.blanc.whiteboard.exception;

public class AlreadyVerifiedException extends BaseWebApplicationException {

	private static final long serialVersionUID = 1L;
    public AlreadyVerifiedException() {
        super(409, "Already verified", "The token has already been verified");
    }
}
