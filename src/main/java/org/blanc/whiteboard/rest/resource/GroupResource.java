package org.blanc.whiteboard.rest.resource;

import com.tracebucket.tron.assembler.BaseResource;

import java.util.HashSet;
import java.util.Set;

public class GroupResource extends BaseResource {
    private String name;
    private String description;
    private String image;
    private Set<AuthorityResource> authorities = new HashSet<>(0);
    private Set<UserResource> members = new HashSet<>(0);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<AuthorityResource> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<AuthorityResource> authorities) {
        this.authorities = authorities;
    }

    public Set<UserResource> getMembers() {
        return members;
    }

    public void setMembers(Set<UserResource> members) {
        this.members = members;
    }
}
