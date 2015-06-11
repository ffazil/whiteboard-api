package org.blanc.whiteboard.exception;

import org.blanc.whiteboard.rest.resource.ErrorResponse;

public class BadClientCredentialsException extends ErrorResponse {

    public BadClientCredentialsException(String errorMessage) {
        super("401", "Client Credentials were incorrect", "Client Credentials were incorrect. Useage: Base64Encode(username:password) ");
    }
}
