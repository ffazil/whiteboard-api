package org.blanc.whiteboard.service.impl;

import org.blanc.whiteboard.repository.mongo.OAuth2AccessTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;

/**
 * Created by ffl on 11-06-2015.
 */
@Service("mongoTokenStore")
public class MongoTokenStore implements TokenStore{

	private static final Logger log = LoggerFactory.getLogger(MongoTokenStore.class);

	@Autowired
	private OAuth2AccessTokenRepository oAuth2AccessTokenRepository;

	private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();


	public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
		return null;
	}

	protected OAuth2Authentication deserializeAuthentication(byte[] authentication) {
		return SerializationUtils.deserialize(authentication);
	}

	public OAuth2Authentication readAuthentication(String token) {
		OAuth2Authentication authentication = null;

		try {
			authentication = deserializeAuthentication(oAuth2AccessTokenRepository.findByTokenId(extractTokenKey(token))
					.getAuthentication());
		}
		catch (EmptyResultDataAccessException e) {
			if (log.isInfoEnabled()) {
				log.info("Failed to find access token for token " + token);
			}
		}
		catch (IllegalArgumentException e) {
			log.warn("Failed to deserialize authentication for " + token, e);
			removeAccessToken(token);
		}

		return authentication;
	}

	protected String extractTokenKey(String value) {
		if (value == null) {
			return null;
		}
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
		}

		try {
			byte[] bytes = digest.digest(value.getBytes("UTF-8"));
			return String.format("%032x", new BigInteger(1, bytes));
		}
		catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");
		}
	}

	public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {

	}

	public OAuth2AccessToken readAccessToken(String tokenValue) {
		return null;
	}



	public void removeAccessToken(String token) {
		oAuth2AccessTokenRepository.deleteByTokenId(extractTokenKey(token));
	}

	@Override
	public void removeAccessToken(OAuth2AccessToken token) {
		removeAccessToken(token.getValue());

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
