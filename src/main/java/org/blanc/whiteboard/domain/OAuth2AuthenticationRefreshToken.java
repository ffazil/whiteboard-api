package org.blanc.whiteboard.domain;

import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

public class OAuth2AuthenticationRefreshToken extends BaseEntity implements OAuth2RefreshToken{

    public String getValue() {
        return null;
    }
}
