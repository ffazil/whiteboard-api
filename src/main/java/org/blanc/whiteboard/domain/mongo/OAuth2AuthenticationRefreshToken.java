package org.blanc.whiteboard.domain.mongo;

import org.springframework.security.oauth2.common.OAuth2RefreshToken;

public class OAuth2AuthenticationRefreshToken extends BaseEntity implements OAuth2RefreshToken{

    private String tokenId;

	private byte[] token;

	private byte[] authentication;

	private String value;

	public OAuth2AuthenticationRefreshToken(String value){
		this.value = value;
	}

	private OAuth2AuthenticationRefreshToken() {
		this(null);
	}

    public String getValue() {
        return value;
    }
}
