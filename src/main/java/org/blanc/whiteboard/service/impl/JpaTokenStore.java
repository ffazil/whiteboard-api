package org.blanc.whiteboard.service.impl;

import org.blanc.whiteboard.domain.AccessToken;
import org.blanc.whiteboard.domain.RefreshToken;
import org.blanc.whiteboard.domain.User;
import org.blanc.whiteboard.repository.jpa.AccessTokenRepository;
import org.blanc.whiteboard.repository.jpa.ClientRepository;
import org.blanc.whiteboard.repository.jpa.RefreshTokenRepository;
import org.blanc.whiteboard.repository.jpa.UserRepository;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author FFL
 * @since 16-03-2015
 *
 * JPA based implementation of Token store.
 */
@Service("jpaTokenStore")
public class JpaTokenStore implements TokenStore{
    private static final Logger log = LoggerFactory.getLogger(JpaTokenStore.class);

    private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();


    @Autowired
    private AccessTokenRepository accessTokenRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        return readAuthentication(token.getValue());
    }

    @Override
    public OAuth2Authentication readAuthentication(String token) {
        OAuth2Authentication authentication = null;

        try {
            authentication = deserializeAuthentication(accessTokenRepository.findByTokenId(extractTokenKey(token))
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

    public void removeAccessToken(String token) {
        accessTokenRepository.deleteByTokenId(extractTokenKey(token));
    }

    protected OAuth2AccessToken deserializeAccessToken(byte[] token) {
        return SerializationUtils.deserialize(token);
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

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        String userName = authentication.isClientOnly() ? null : authentication.getName();

        RefreshToken refreshToken = null;
        if (token.getRefreshToken() != null) {
            refreshToken = refreshTokenRepository.findByTokenId(token.getRefreshToken().getValue());
        }



        if (readAccessToken(token.getValue())!=null) {
            removeAccessToken(token.getValue());
        }

        User user = userRepository.findByUsername(userName);


        AccessToken accessToken = new AccessToken(extractTokenKey(token.getValue()));

        accessToken.setToken(serializeAccessToken(token));
        accessToken.setTokenId(extractTokenKey(token.getValue()));
        accessToken.setAuthenticationId(authenticationKeyGenerator.extractKey(authentication));
        accessToken.setClient(clientRepository.findByClientId(authentication.getOAuth2Request().getClientId()));
        accessToken.setUser(user);
        accessToken.setAuthentication(serializeAuthentication(authentication));
        accessToken.setRefreshToken(refreshToken);
        accessToken.setExpiration(token.getExpiration());
        accessToken.setScope(token.getScope());
        accessToken.setTokenType(token.getTokenType());


        accessToken = accessTokenRepository.save(accessToken);





    }

    protected byte[] serializeAuthentication(OAuth2Authentication authentication) {
        return SerializationUtils.serialize(authentication);
    }

    protected byte[] serializeAccessToken(OAuth2AccessToken token) {
        return SerializationUtils.serialize(token);
    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        OAuth2AccessToken accessToken = null;

        try {
                AccessToken token = accessTokenRepository.findByTokenId(extractTokenKey(tokenValue));
                if(token != null){
                    accessToken = deserializeAccessToken(token.getToken());
                }

        }
        catch (EmptyResultDataAccessException e) {
            if (log.isInfoEnabled()) {
                log.info("Failed to find access token for token " + tokenValue);
            }
        }
        catch (IllegalArgumentException e) {
            log.warn("Failed to deserialize access token for " + tokenValue, e);
            removeAccessToken(tokenValue);
        }

        return accessToken;
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken token) {
        removeAccessToken(token.getValue());

    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        RefreshToken entity = new RefreshToken(refreshToken.getValue());
        entity.setTokenId(extractTokenKey(refreshToken.getValue()));
        entity.setAuthentication(serializeAuthentication(authentication));
        entity.setToken(serializeRefreshToken(refreshToken));

        entity = refreshTokenRepository.save(entity);

    }


    protected byte[] serializeRefreshToken(OAuth2RefreshToken token) {
        return SerializationUtils.serialize(token);
    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        OAuth2RefreshToken refreshToken = null;
        try{
            refreshToken = refreshTokenRepository.findByTokenId(extractTokenKey(tokenValue));
        }
        catch (EmptyResultDataAccessException e){
            log.info("Failed to find refresh token for value: " + tokenValue);
        }
        catch (IllegalArgumentException e){
            log.warn("Failed to deserialize refresh token for value: " + tokenValue, e);
        }
        return refreshToken;
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        return readAuthenticationForRefreshToken(token.getValue());
    }

    private OAuth2Authentication readAuthenticationForRefreshToken(String value) {
        OAuth2Authentication authentication = null;
        try{
            authentication = deserializeAuthentication(refreshTokenRepository.findByTokenId(extractTokenKey(value)).getAuthentication());
        }
        catch (EmptyResultDataAccessException e){
            log.info("Failed to find access token for value: " + value);
        }
        catch (IllegalArgumentException e){
            log.warn("Failed to deserialize access token for " + value, e);
        }
        return authentication;
    }

    protected OAuth2Authentication deserializeAuthentication(byte[] authentication) {
        return SerializationUtils.deserialize(authentication);
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken token) {
        removeRefreshToken(token.getValue());
    }

    private void removeRefreshToken(String value) {
        refreshTokenRepository.deleteByTokenId(extractTokenKey(value));
    }

    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
        removeAccessTokenUsingRefreshToken(refreshToken.getValue());
    }

    private void removeAccessTokenUsingRefreshToken(String value) {
        accessTokenRepository.deleteByRefreshTokenTokenId(extractTokenKey(value));
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        OAuth2AccessToken accessToken = null;

        String key = authenticationKeyGenerator.extractKey(authentication);
        try {

            AccessToken token = accessTokenRepository.findByAuthenticationId(key);
            if(token != null){
                byte[] at = token.getToken();
                accessToken = deserializeAccessToken(at);
            }

        }
        catch (EmptyResultDataAccessException e) {
                log.debug("Failed to find access token for authentication " + authentication);
        }
        catch (IllegalArgumentException e) {
            log.error("Could not extract access token for authentication " + authentication, e);
        }


        if (accessToken != null
                && !key.equals(authenticationKeyGenerator.extractKey(readAuthentication(accessToken.getValue())))) {
            removeAccessToken(accessToken.getValue());
            // Keep the store consistent (maybe the same user is represented by this authentication but the details have
            // changed)
            storeAccessToken(accessToken, authentication);
        }
        return accessToken;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        List<AccessToken> accessTokens = new ArrayList<AccessToken>();

        try {
            accessTokens = accessTokenRepository.findByUserAndClient(userName, clientId);
        }
        catch (EmptyResultDataAccessException e) {
            log.info("Failed to find access token for clientId " + clientId + " and userName " + userName);

        }

        List<OAuth2AccessToken> oAuth2AccessTokens = new ArrayList<OAuth2AccessToken>(0);
        accessTokens.stream()
                .filter(accessToken -> accessToken != null)
                .forEach(accessToken -> oAuth2AccessTokens.add((OAuth2AccessToken)accessToken));
        return oAuth2AccessTokens;
    }


    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        List<AccessToken> accessTokens = new ArrayList<AccessToken>();

        try {
            accessTokens = accessTokenRepository.findByClient(clientId);
        }
        catch (EmptyResultDataAccessException e) {
            log.info("Failed to find access token for clientId " + clientId);

        }

        List<OAuth2AccessToken> oAuth2AccessTokens = new ArrayList<>(0);
        accessTokens.stream()
                .filter(accessToken -> accessToken != null)
                .forEach(accessToken -> oAuth2AccessTokens.add((OAuth2AccessToken)accessToken));
        return oAuth2AccessTokens;
    }
}
