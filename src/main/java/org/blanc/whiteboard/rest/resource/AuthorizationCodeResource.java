package org.blanc.whiteboard.rest.resource;

import com.tracebucket.tron.assembler.BaseResource;

public class AuthorizationCodeResource extends BaseResource {
    private String code;
    private byte[] authentication;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public byte[] getAuthentication() {
        return authentication;
    }

    public void setAuthentication(byte[] authentication) {
        this.authentication = authentication;
    }
}