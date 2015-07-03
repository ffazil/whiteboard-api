package org.blanc.whiteboard.rest.resource;

import com.tracebucket.tron.assembler.BaseResource;

public class AuthorityResource extends BaseResource {
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}