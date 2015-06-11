package org.blanc.whiteboard.domain;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.Date;
import java.util.Map;
import java.util.Set;

public class OAuth2AuthenticationAccessToken extends BaseEntity implements OAuth2AccessToken{

    public Map<String, Object> getAdditionalInformation() {
        return null;
    }

    public Set<String> getScope() {
        return null;
    }

    public OAuth2RefreshToken getRefreshToken() {
        return null;
    }

    public String getTokenType() {
        return null;
    }

    public boolean isExpired() {
        return false;
    }

    public Date getExpiration() {
        return null;
    }

    public int getExpiresIn() {
        return 0;
    }

    public String getValue() {
        return null;
    }
}
