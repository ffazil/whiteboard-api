package org.blanc.whiteboard.service;

import org.blanc.whiteboard.domain.VerificationToken;
import org.blanc.whiteboard.rest.resource.LostPasswordRequest;
import org.blanc.whiteboard.rest.resource.PasswordRequest;

public interface VerificationTokenService {

    public VerificationToken sendEmailVerificationToken(String userId);

    public VerificationToken sendEmailRegistrationToken(String userId);

    public VerificationToken sendLostPasswordToken(LostPasswordRequest lostPasswordRequest);

    public VerificationToken verify(String base64EncodedToken);

    public VerificationToken generateEmailVerificationToken(String emailAddress);

    public VerificationToken resetPassword(String base64EncodedToken, PasswordRequest passwordRequest);
}
