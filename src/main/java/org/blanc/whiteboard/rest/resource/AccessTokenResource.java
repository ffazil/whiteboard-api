package org.blanc.whiteboard.rest.resource;

import com.tracebucket.tron.assembler.BaseResource;

import java.util.*;

public class AccessTokenResource extends BaseResource{
    private String authenticationId;
    private String tokenId;
    private byte[] token;
    private byte[] authentication;
    private ClientResource client;
    private UserResource user;
    private String value;
    private Date expiration;
    private String tokenType;
    private RefreshTokenResource refreshToken;
    private Set<String> scope = new HashSet<String>(0);
    private Map<String, String> additionalInformation = new HashMap<String, String>(0);

    public String getAuthenticationId() {
        return authenticationId;
    }

    public void setAuthenticationId(String authenticationId) {
        this.authenticationId = authenticationId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public byte[] getToken() {
        return token;
    }

    public void setToken(byte[] token) {
        this.token = token;
    }

    public byte[] getAuthentication() {
        return authentication;
    }

    public void setAuthentication(byte[] authentication) {
        this.authentication = authentication;
    }

    public ClientResource getClient() {
        return client;
    }

    public void setClient(ClientResource client) {
        this.client = client;
    }

    public UserResource getUser() {
        return user;
    }

    public void setUser(UserResource user) {
        this.user = user;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public RefreshTokenResource getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(RefreshTokenResource refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Set<String> getScope() {
        return scope;
    }

    public void setScope(Set<String> scope) {
        this.scope = scope;
    }

    public Map<String, String> getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(Map<String, String> additionalInformation) {
        this.additionalInformation = additionalInformation;
    }
}
