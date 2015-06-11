package org.blanc.whiteboard.service.impl;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by ffl on 11-06-2015.
 */
@Service("mongoTokenStore")
public class MongoTokenStore implements TokenStore{
	public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
		return null;
	}

	public OAuth2Authentication readAuthentication(String token) {
		return null;
	}

	public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {

	}

	public OAuth2AccessToken readAccessToken(String tokenValue) {
		return null;
	}

	public void removeAccessToken(OAuth2AccessToken token) {

	}

	public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {

	}

	public OAuth2RefreshToken readRefreshToken(String tokenValue) {
		return null;
	}

	public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
		return null;
	}

	public void removeRefreshToken(OAuth2RefreshToken token) {

	}

	public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {

	}

	public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
		return null;
	}

	public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
		return null;
	}

	public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
		return null;
	}
}
