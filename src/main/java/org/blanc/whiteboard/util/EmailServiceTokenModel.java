package org.blanc.whiteboard.util;

import org.apache.commons.codec.binary.Base64;
import org.blanc.whiteboard.domain.User;
import org.blanc.whiteboard.domain.VerificationToken;
import org.blanc.whiteboard.domain.VerificationTokenType;

import java.io.Serializable;

public class EmailServiceTokenModel implements Serializable {

	private static final long serialVersionUID = 1L;
    private final String emailAddress;
    private final String token;
    private final VerificationTokenType tokenType;
    private final String hostNameUrl;


    public EmailServiceTokenModel(User user, VerificationToken token, String hostNameUrl)  {
        this.emailAddress = user.getEmailAddress();
        this.token = token.getToken();
        this.tokenType = token.getTokenType();
        this.hostNameUrl = hostNameUrl;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getEncodedToken() {
        return new String(Base64.encodeBase64(token.getBytes()));
    }

    public String getToken() {
        return token;
    }

    public VerificationTokenType getTokenType() {
        return tokenType;
    }

    public String getHostNameUrl() {
        return hostNameUrl;
    }
}

