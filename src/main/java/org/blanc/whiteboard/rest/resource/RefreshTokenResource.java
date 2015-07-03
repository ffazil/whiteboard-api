package org.blanc.whiteboard.rest.resource;

import com.tracebucket.tron.assembler.BaseResource;

public class RefreshTokenResource extends BaseResource{
    private String tokenId;
    private byte[] token;
    private byte[] authentication;
    private String value;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
